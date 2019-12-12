package pl.bookworm.bookworm.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pl.bookworm.bookworm.model.User;
import pl.bookworm.bookworm.service.UserService;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
public class UserController {

    UserService userService;

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

    @CrossOrigin(origins = "${config.port.access.cors}")
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        User loggedInUser = userService.loginUser(user);
        if (!loggedInUser.getUsername().equals("")) {
            return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(loggedInUser, HttpStatus.UNAUTHORIZED);
    }
}
