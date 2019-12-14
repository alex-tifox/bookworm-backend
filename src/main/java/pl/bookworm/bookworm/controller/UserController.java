package pl.bookworm.bookworm.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pl.bookworm.bookworm.model.Book;
import pl.bookworm.bookworm.model.User;
import pl.bookworm.bookworm.service.ConfirmationCodeService;
import pl.bookworm.bookworm.service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
public class UserController {

    UserService userService;
    ConfirmationCodeService confirmationCodeService;

    @CrossOrigin(origins = "${config.port.access.cors}")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User newUser) {
        if (userService.registerUser(newUser)) {
            return new ResponseEntity<>("User registered", HttpStatus.OK);
        }
        return new ResponseEntity<>(
                "User with such username is already existing. Try again.",
                HttpStatus.CONFLICT
        );
    }
    
    @GetMapping("/confirm")
    public ResponseEntity<String> confirmUser(@RequestParam("code") String code) {
    	if (confirmationCodeService.UseConfirmationCode(code)) {
    		return new ResponseEntity<>("User confirmed", HttpStatus.OK);
    	}
    	
    	return new ResponseEntity<>("Confirmation code doesn't exist or was already used.", HttpStatus.CONFLICT);
    }

    @CrossOrigin(origins = "${config.port.access.cors}")
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        User loggedInUser = userService.loginUser(user);
        if (!loggedInUser.getUsername().equals("")) {
            return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(loggedInUser, HttpStatus.UNAUTHORIZED);
    }
    
    @GetMapping("/getUserShowcase/{username}")
    public ResponseEntity<User> getUserShowcase(@PathVariable("username") String username) {
        // find user's showcase (find User's record in the database)
        return new ResponseEntity<>(temporaryMockingUserData(), HttpStatus.OK);
    }

    @GetMapping("/getUserTopFive/{username}")
    public List<Book> getUserTopFive(@PathVariable("username") String username) {
        //Find user's top rated books
        return new ArrayList<>(Arrays.asList(
                BookController.temporaryMockingBookData(5.0),
                BookController.temporaryMockingBookData(4.5),
                BookController.temporaryMockingBookData(4.0),
                BookController.temporaryMockingBookData(4.0),
                BookController.temporaryMockingBookData(3.5)
        ));
    }

    @GetMapping("/getUserRecentRatings/{username}")
    public List<Book> getUserRecentRatings(@PathVariable("username") String username) {
        //Find user's top rated books
        return new ArrayList<>(Arrays.asList(
                BookController.temporaryMockingBookData(3.5),
                BookController.temporaryMockingBookData(4.5),
                BookController.temporaryMockingBookData(2.5),
                BookController.temporaryMockingBookData(5.0),
                BookController.temporaryMockingBookData(4.0)
        ));
    }

    /*
     * Temp method for mocking user's data
     */
    private User temporaryMockingUserData() {
        User user = new User();
        user.setUsername("userforshowcase");
        user.setEmail("useremail@gmail.com");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            user.setLastLoginDate(format.parse("2019-07-21"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        user.setUserDescription("This is me - user, and Im awesome");
        user.setFavouriteBook(BookController.temporaryMockingBookData());

        return user;
    }
}
