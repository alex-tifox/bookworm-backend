package pl.bookworm.bookworm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pl.bookworm.bookworm.model.SearchResult;
import pl.bookworm.bookworm.service.SearchService;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
public class SearchController {
	SearchService searchService;
	
    @GetMapping("/search")
    public SearchResult search(@RequestParam(name = "bookName", required = false) String bookName,
											 @RequestParam(name = "authorName", required = false) String authorName,
											 @RequestParam(name = "userName", required = false) String userName){
    	
    	return searchService.processSearch(bookName, authorName, userName);
    }
}