package pl.bookworm.bookworm.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@Builder(toBuilder = true)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {

    //TODO: on production - change generation type to sequence
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String username;
    String password;
    String userFullname;
    String email;
    Date registrationDate;
    Date lastLoginDate;
}
