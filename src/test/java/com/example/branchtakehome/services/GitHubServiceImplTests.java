package com.example.branchtakehome.services;

import com.example.branchtakehome.models.GitHubRepo;
import com.example.branchtakehome.models.GitHubUser;
import com.example.branchtakehome.services.impl.GitHubServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GitHubServiceImplTests {
    @Mock RestTemplate restTemplate;
    GitHubServiceImpl gitHubService = new GitHubServiceImpl();

    @Test
    void testGetUserSuccess() {
        ResponseEntity<GitHubUser> re = gitHubService.getUser("octocat");
        GitHubUser body = re.getBody();

        Assertions.assertEquals(HttpStatus.OK, re.getStatusCode());
        Assertions.assertEquals("https://avatars.githubusercontent.com/u/583231?v=4", body.getAvatar_url());
        Assertions.assertEquals("https://github.com/octocat", body.getHtml_url());
        Assertions.assertEquals("octocat", body.getLogin());
        Assertions.assertNull(body.getEmail());
        Assertions.assertEquals("San Francisco", body.getLocation());
        Assertions.assertEquals("The Octocat", body.getName());
        Assertions.assertEquals(LocalDateTime.parse("2011-01-25T18:44:36"), body.getCreated_at());
    }

    @Test
    void testGetUserNotFound() {
        ResponseEntity<GitHubUser> re = gitHubService.getUser("octocat-a");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, re.getStatusCode());
    }

    @Test
    void testGetReposSuccess() {
        List<GitHubRepo> repos = gitHubService.getRepos("octocat");
        Assertions.assertEquals(8, repos.size());
    }
}
