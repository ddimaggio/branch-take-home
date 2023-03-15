package com.example.branchtakehome.services.impl;

import com.example.branchtakehome.models.GitHubRepo;
import com.example.branchtakehome.models.GitHubUser;
import com.example.branchtakehome.services.GitHubService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class GitHubServiceImpl implements GitHubService {

    public GitHubServiceImpl () {}
    @Override
    public ResponseEntity getUser(String username) {
        String url = "https://api.github.com/users/" + username;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity re;
        try {
            re = restTemplate.getForEntity(url, GitHubUser.class);
        } catch (Exception e) {
            re = new ResponseEntity<>("No user was found with that username.", HttpStatus.NOT_FOUND);
        }
        return re;
    }

    @Override
    public List<GitHubRepo> getRepos(String username) {
        String url = "https://api.github.com/users/" + username + "/repos";
        RestTemplate restTemplate = new RestTemplate();
        return Arrays.stream(restTemplate.getForObject(url, GitHubRepo[].class)).toList();
    }
}
