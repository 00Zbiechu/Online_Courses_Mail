package pl.online_courses_mail.online_courses_mail.service;

public interface EmailSenderService {

    void sendEmail(String toEmail, String subject, String body);
}
