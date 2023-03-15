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

/**
 * Controller class for any calls related to users.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Completes checks for valid GitHub usernames and calls the User Service for the data contained
     * in UserResponseDTO if username is valid
     * @param username - username of the GitHub user to query
     * @return a ResponseEntity with a UserResponseDTO body that contains merged user data and repo data
     */
    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable String username) {
        // Username cannot be null
        if (username == null) {
            return new ResponseEntity("The given username is not valid.", HttpStatus.BAD_REQUEST);
        }
        Pattern pattern = Pattern.compile("^(?!-)(?!.*--)[A-Za-z0-9-]{0,38}(?<!-)$");
        Matcher matcher = pattern.matcher(username);
        // Username must be a valid GitHub username (see README for exact restrictions)
        if (!matcher.matches()) {
            return new ResponseEntity("The given username is not valid.", HttpStatus.BAD_REQUEST);
        }
        return userService.getUser(username);
    }
}
