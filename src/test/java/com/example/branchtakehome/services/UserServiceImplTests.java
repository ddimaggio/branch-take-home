package com.example.branchtakehome.services;

import com.example.branchtakehome.models.GitHubRepo;
import com.example.branchtakehome.models.GitHubUser;
import com.example.branchtakehome.models.UserResponseDTO;
import com.example.branchtakehome.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @Mock GitHubService gitHubService;
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp () {
        userService = new UserServiceImpl(gitHubService);
    }

    @Test
    void testSuccess() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String date = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        GitHubUser gitHubUser = new GitHubUser();
        gitHubUser.setHtml_url("url");
        gitHubUser.setLogin("user");
        gitHubUser.setEmail("d@d.com");
        gitHubUser.setAvatar_url("url");
        gitHubUser.setLocation("Seattle");
        gitHubUser.setName("name");
        gitHubUser.setCreated_at(localDateTime);
        GitHubRepo gitHubRepo = new GitHubRepo();
        gitHubRepo.setName("repo");
        gitHubRepo.setHtml_url("url");

        Mockito.when(gitHubService.getUser(any())).thenReturn(ResponseEntity.ok(gitHubUser));
        Mockito.when(gitHubService.getRepos(any())).thenReturn(List.of(gitHubRepo));
        ResponseEntity<UserResponseDTO> re = userService.getUser("octocat");
        UserResponseDTO body = re.getBody();

        Assertions.assertEquals(gitHubUser.getAvatar_url(), body.getAvatar());
        Assertions.assertEquals(gitHubUser.getHtml_url(), body.getUrl());
        Assertions.assertEquals(gitHubUser.getLogin(), body.getUser_name());
        Assertions.assertEquals(gitHubUser.getEmail(), body.getEmail());
        Assertions.assertEquals(gitHubUser.getLocation(), body.getGeo_location());
        Assertions.assertEquals(gitHubUser.getName(), body.getDisplay_name());
        Assertions.assertEquals(date, body.getCreated_at());
        Assertions.assertEquals(gitHubRepo.getName(), body.getRepos().get(0).getName());
        Assertions.assertEquals(gitHubRepo.getHtml_url(), body.getRepos().get(0).getUrl());
    }

    @Test
    void testUserDoesNotExist() {
        Mockito.when(gitHubService.getUser(any())).thenReturn(new ResponseEntity(HttpStatus.NOT_FOUND));
        ResponseEntity<UserResponseDTO> re = userService.getUser("octocat-a");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, re.getStatusCode());
    }
}
