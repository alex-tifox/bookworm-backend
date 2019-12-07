package pl.bookworm.bookworm.controller;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/register")
    String registerUser(@RequestBody User newUser, HttpServletResponse response) {
        response.setStatus(HttpStatus.SC_OK);
        userService.registerUser(newUser);
        return "User registered";
    }

    @PostMapping("/login")
    User loginUser(@RequestBody User user, HttpServletResponse response) {
        if (user.getUsername().equals("janjan") && user.getPassword().equals("123123123")) {
            response.setStatus(HttpStatus.SC_OK);
            return user;
        }
        response.setStatus(HttpStatus.SC_UNAUTHORIZED);
        return new User();
    }
}
