package pl.online_courses_mail.online_courses_mail.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.online_courses_mail.online_courses_mail.service.EmailSenderService;

@Component
@RequiredArgsConstructor
public class EmailListener {

    private final EmailSenderService emailSenderService;

    @org.springframework.kafka.annotation.KafkaListener(
            topics = "email",
            groupId = "courses"
    )
    void sendMail(String data) {
        emailSenderService.sendEmail(data, "Test", "Test");
    }
}
