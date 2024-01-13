package pl.online_courses_mail.online_courses_mail.service;

import io.vavr.control.Try;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.online_courses_mail.online_courses_mail.exception.EmailErrorException;
import pl.online_courses_mail.online_courses_mail.model.dto.Message;
import pl.online_courses_mail.online_courses_mail.model.entity.Mail;
import pl.online_courses_mail.online_courses_mail.repository.MailRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    private final MailRepository mailRepository;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Override
    public void sendRegistrationEmail(String email, String username, String confirmationLink) {

        Try.run(() -> {

            var mail = saveMail(email, username, confirmationLink);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            Context context = new Context();
            context.setVariable("username", mail.getMessage().getSubject());
            context.setVariable("confirmationLink", mail.getMessage().getConfirmationLink());

            helper.setFrom(fromMail);
            helper.setTo(mail.getMail());
            helper.setSubject("Welcome " + mail.getMessage().getSubject());
            String htmlContent = templateEngine.process("registration-template", context);
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);

        }).onFailure(mail -> {
            throw new EmailErrorException("Mail email error");
        });
    }

    @Override
    public void sendParticipationConfirmationEmail(String email, String title, String confirmationLink) {

        Try.run(() -> {

            var mail = saveMail(email, title, confirmationLink);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            Context context = new Context();
            context.setVariable("title", mail.getMessage().getSubject());
            context.setVariable("confirmationLink", mail.getMessage().getConfirmationLink());

            helper.setFrom(fromMail);
            helper.setTo(mail.getMail());
            helper.setSubject("Invitation to Join Course");
            String htmlContent = templateEngine.process("participationCourse-template", context);
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);

        }).onFailure(mail -> {
            throw new EmailErrorException("Mail email error");
        });
    }

    private Mail saveMail(String email, String subject, String confirmationLink) {
        return mailRepository.save(
                Mail.builder()
                        .uuid(UUID.randomUUID())
                        .mail(email)
                        .message(Message.builder()
                                .subject(subject)
                                .confirmationLink(confirmationLink)
                                .build()
                        )
                        .build()
        );
    }
}
