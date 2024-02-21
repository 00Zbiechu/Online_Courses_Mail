package pl.online_courses_mail.online_courses_mail.kafka.listener;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.online_courses_mail.online_courses_mail.model.dto.CourseConfirmationDTO;
import pl.online_courses_mail.online_courses_mail.service.EmailSenderService;

@Component
@RequiredArgsConstructor
public class KafkaParticipationConfirmationListener {

    private final EmailSenderService emailSenderService;

    @KafkaListener(topics = "${online-courses.kafka.topics.email.participation.name}", groupId = "${online-courses.kafka.topics.email.participation.group}", containerFactory = "participationCourseFactory")
    void sendMail(ConsumerRecord<String, CourseConfirmationDTO> consumerRecord) {
        emailSenderService.sendParticipationConfirmationEmail(
                consumerRecord.value().getMail(),
                consumerRecord.value().getTitle(),
                consumerRecord.value().getConfirmationLink()
        );
    }
}
