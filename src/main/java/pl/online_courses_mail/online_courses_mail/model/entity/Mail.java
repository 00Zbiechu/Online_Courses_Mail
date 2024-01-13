package pl.online_courses_mail.online_courses_mail.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import pl.online_courses_mail.online_courses_mail.model.dto.Message;

import java.io.Serializable;
import java.util.UUID;

@RedisHash("Mail")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mail implements Serializable {

    @Id
    private UUID uuid;

    private String mail;

    private Message message;
}
