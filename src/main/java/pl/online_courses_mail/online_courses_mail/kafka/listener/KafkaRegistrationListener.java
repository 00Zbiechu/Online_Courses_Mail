package pl.online_courses_mail.online_courses_mail.kafka.listener;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.online_courses_mail.online_courses_mail.model.dto.UsernameAndMailDTO;
import pl.online_courses_mail.online_courses_mail.service.EmailSenderService;

@Component
@RequiredArgsConstructor
public class KafkaRegistrationListener {

    private final EmailSenderService emailSenderService;

    @KafkaListener(topics = "${online-courses.kafka.topics.email.registration.name}", groupId = "${online-courses.kafka.topics.email.registration.group}", containerFactory = "registrationFactory")
    void sendMail(ConsumerRecord<String, UsernameAndMailDTO> consumerRecord) {
        emailSenderService.sendRegistrationEmail(
                consumerRecord.value().getMail(),
                consumerRecord.value().getUsername(),
                consumerRecord.value().getConfirmationLink()
        );
    }
}
