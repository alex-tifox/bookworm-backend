package pl.bookworm.bookworm.model;

import java.util.List;
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
	List<Object> results;
}