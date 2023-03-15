package com.example.branchtakehome.controllers;

import com.example.branchtakehome.models.UserResponseDTO;
import com.example.branchtakehome.services.GitHubService;
import com.example.branchtakehome.services.UserService;
import com.example.branchtakehome.services.impl.GitHubServiceImpl;
import com.example.branchtakehome.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class UserControllerTests {

    UserService userService;
    UserController userController ;
    GitHubService gitHubService;

    @BeforeEach
    public void setUp() {
        gitHubService = new GitHubServiceImpl();
        userService = new UserServiceImpl(gitHubService);
        userController = new UserController(userService);
    }

    /**
     * End-to-end test with data retrieved from GitHub
     */
    @Test
    void testSuccess() {
        LocalDateTime localDateTime = LocalDateTime.parse("2011-01-25T18:44:36");
        String date = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ResponseEntity<UserResponseDTO> response = userController.getUser("octocat");
        UserResponseDTO body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("octocat", body.getUser_name());
        Assertions.assertEquals("The Octocat", body.getDisplay_name());
        Assertions.assertEquals("https://avatars.githubusercontent.com/u/583231?v=4", body.getAvatar());
        Assertions.assertEquals("San Francisco", body.getGeo_location());
        Assertions.assertNull(body.getEmail());
        Assertions.assertEquals("https://github.com/octocat", body.getUrl());
        Assertions.assertEquals(date, body.getCreated_at());
        Assertions.assertEquals(8, body.getRepos().size());
    }

    /**
     * Runs the controller against 5 different bad usernames
     */
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"-octocat", "octocat-", "octo--cat", "octocatoctocatoctocatoctocatoctocatoctocat"})
    void testBadUsernames(String username) {
        ResponseEntity re = userController.getUser(username);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, re.getStatusCode());
    }
}
