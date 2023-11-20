package pl.online_courses_mail.online_courses_mail.service;

import io.vavr.control.Try;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.online_courses_mail.online_courses_mail.exception.EmailErrorException;

@Component
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void sendRegistrationEmail(String email, String username) {

        Try.run(() -> {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            Context context = new Context();
            context.setVariable("username", username);

            helper.setFrom("***REMOVED***");
            helper.setTo(email);
            helper.setSubject("Welcome " + username);
            String htmlContent = templateEngine.process("registration-template", context);
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);

        }).onFailure(mail -> {
            throw new EmailErrorException("Email error");
        });
    }
}
