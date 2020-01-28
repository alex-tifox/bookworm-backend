package pl.bookworm.bookworm.model;

import java.util.List;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Builder(toBuilder = true)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchResult {
	String category;
	Set<Book> foundBooks;
	Set<Author> foundAuthors;
	Set<User> foundUsers;
	List<Object> results;
}