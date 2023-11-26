package pl.online_courses_mail.online_courses_mail.kafka.consumer;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import pl.online_courses_mail.online_courses_mail.event.UserAndMailDTO;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${online-courses.kafka.topics.email.trusted-packages}")
    private String trustedPackages;

    @Value("${spring.kafka.producer.schema.registry.url}")
    String schemaRegistryUrl;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    private Map<String, Object> consumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ConsumerConfig.RECONNECT_BACKOFF_MS_CONFIG, "5000");
        props.put(ConsumerConfig.RETRY_BACKOFF_MS_CONFIG, "5000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put("schema.registry.url", schemaRegistryUrl);
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");
        return props;
    }

    @Bean
    public ConsumerFactory<String, UserAndMailDTO> registrationConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserAndMailDTO> registrationFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserAndMailDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(registrationConsumerFactory());
        return factory;
    }
}
