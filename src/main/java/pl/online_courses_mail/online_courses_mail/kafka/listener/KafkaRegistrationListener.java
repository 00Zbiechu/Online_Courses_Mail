package pl.online_courses_mail.online_courses_mail.kafka.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.online_courses_mail.online_courses_mail.dto.UsernameAndMailDTO;
import pl.online_courses_mail.online_courses_mail.service.EmailSenderService;

@Component
@RequiredArgsConstructor
public class KafkaRegistrationListener {

    private final EmailSenderService emailSenderService;

    @KafkaListener(topics = "${online-courses.kafka.topics.email.name}", groupId = "${online-courses.kafka.topics.email.group}", containerFactory = "registrationFactory")
    void sendMail(UsernameAndMailDTO usernameAndMailDTO) {
        emailSenderService.sendRegistrationEmail(usernameAndMailDTO.getMail(), usernameAndMailDTO.getUsername());
    }
}
