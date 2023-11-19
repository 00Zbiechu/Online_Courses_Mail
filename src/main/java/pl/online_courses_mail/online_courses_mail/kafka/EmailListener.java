package pl.online_courses_mail.online_courses_mail.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.online_courses_mail.online_courses_mail.kafka.dto.UsernameAndMailDTO;
import pl.online_courses_mail.online_courses_mail.service.EmailSenderService;

@Component
@RequiredArgsConstructor
public class EmailListener {

    private final EmailSenderService emailSenderService;

    @org.springframework.kafka.annotation.KafkaListener(
            topics = "registration",
            groupId = "courses",
            containerFactory = "registrationFactory"
    )
    void sendMail(UsernameAndMailDTO usernameAndMailDTO) {
        emailSenderService.sendRegistrationEmail(usernameAndMailDTO.getMail(), usernameAndMailDTO.getUsername());
    }
}
