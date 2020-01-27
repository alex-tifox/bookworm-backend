package pl.bookworm.bookworm.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Set;

@Builder(toBuilder = true)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;
    String birthplace;
    String description;

    @OneToMany(mappedBy = "author") @JsonIgnoreProperties("author")
    Set<Book> books;
}
