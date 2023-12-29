package pl.online_courses_mail.online_courses_mail.service;

public interface EmailSenderService {

    void sendRegistrationEmail(String email, String username, String confirmationLink);

    void sendParticipationConfirmationEmail(String email, String title, String confirmationLink);
}
