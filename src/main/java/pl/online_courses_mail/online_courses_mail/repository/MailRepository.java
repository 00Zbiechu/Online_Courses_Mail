package pl.online_courses_mail.online_courses_mail.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.online_courses_mail.online_courses_mail.model.entity.Mail;

@Repository
public interface MailRepository extends CrudRepository<Mail, String> {
}
