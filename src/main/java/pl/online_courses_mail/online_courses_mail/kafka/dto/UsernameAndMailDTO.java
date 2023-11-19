package pl.online_courses_mail.online_courses_mail.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsernameAndMailDTO {

    private String username;

    private String mail;
}
