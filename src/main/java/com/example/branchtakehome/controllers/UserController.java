package com.example.branchtakehome.controllers;

import com.example.branchtakehome.models.UserResponseDTO;
import com.example.branchtakehome.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable String username) {
        if (username == null) {
            return new ResponseEntity("The given username is not valid.", HttpStatus.BAD_REQUEST);
        }
        Pattern pattern = Pattern.compile("^(?!-)(?!.*--)[A-Za-z0-9-]{0,38}(?<!-)$");
        Matcher matcher = pattern.matcher(username);
        if (!matcher.matches()) {
            return new ResponseEntity("The given username is not valid.", HttpStatus.BAD_REQUEST);
        }
        return userService.getUser(username);
    }
}
