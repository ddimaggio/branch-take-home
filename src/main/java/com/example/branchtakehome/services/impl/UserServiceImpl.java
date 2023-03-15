package com.example.branchtakehome.services.impl;

import com.example.branchtakehome.models.GitHubRepo;
import com.example.branchtakehome.models.GitHubUser;
import com.example.branchtakehome.models.UserResponseDTO;
import com.example.branchtakehome.models.mappers.UserMapper;
import com.example.branchtakehome.services.GitHubService;
import com.example.branchtakehome.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final GitHubService gitHubService;

    public UserServiceImpl(GitHubService gitHubService){
        this.gitHubService = gitHubService;
    }

    /**
     * Retrieves user and repo data from the GitHubService then maps them to our UserResponseDTO before responding
     * @param username - username of the GitHub user to query
     * @return a mapped and merged UserResponseDTO with an OK (200) response
     */
    @Override
    public ResponseEntity<UserResponseDTO> getUser(String username) {
        ResponseEntity<GitHubUser> gitHubUserEntity = gitHubService.getUser(username);
        if (gitHubUserEntity.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(gitHubUserEntity.getStatusCode());
        }
        List<GitHubRepo> gitHubRepos = gitHubService.getRepos(username);
        return ResponseEntity.ok(UserMapper.map(gitHubUserEntity.getBody(), gitHubRepos));
    }
}
