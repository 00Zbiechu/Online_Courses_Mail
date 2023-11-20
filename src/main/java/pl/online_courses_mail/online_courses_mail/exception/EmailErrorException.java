package pl.online_courses_mail.online_courses_mail.exception;

public class EmailErrorException extends RuntimeException {
    public EmailErrorException(String error) {
        super(error);
    }
}
