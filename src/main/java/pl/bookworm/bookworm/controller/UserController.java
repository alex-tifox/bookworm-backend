package pl.bookworm.bookworm.controller;

import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pl.bookworm.bookworm.model.User;
import pl.bookworm.bookworm.service.UserService;

import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin(origins = "${config.port.access.cors}")
    @PostMapping("/register")
    public String registerUser(@RequestBody User newUser, HttpServletResponse response) {
        if (userService.registerUser(newUser)) {
            response.setStatus(HttpStatus.SC_OK);
            return "User registered";
        }
        response.setStatus(HttpStatus.SC_CONFLICT);
        return "User with such username is already existing. Try again.";
    }

    @CrossOrigin(origins = "${config.port.access.cors}")
    @PostMapping("/login")
    public User loginUser(@RequestBody User user, HttpServletResponse response) {
        User loggedInUser = userService.loginUser(user);
        if (loggedInUser.equals(new User())) {
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            return loggedInUser;
        }
        response.setStatus(HttpStatus.SC_OK);
        return loggedInUser;
    }
}
