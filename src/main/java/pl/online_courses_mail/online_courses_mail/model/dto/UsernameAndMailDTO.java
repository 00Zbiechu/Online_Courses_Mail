package pl.online_courses_mail.online_courses_mail.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsernameAndMailDTO {

    private String username;

    private String mail;

    private String confirmationLink;
}
