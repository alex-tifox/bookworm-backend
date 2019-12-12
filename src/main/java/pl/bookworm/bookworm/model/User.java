package pl.bookworm.bookworm.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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
    String userDescription;

    @ManyToOne
    Book favouriteBook;

    /* This set will be sorted for client purposes
     * For example,
     * If client need user's top 5 - this will be sorted by ratings
     * If recent rated books - this one will be sorted by rating time
     * TODO: add rating time to make list sorting more specific
     */
    @OneToMany
    Set<Book> usersRatedBooks;
}
