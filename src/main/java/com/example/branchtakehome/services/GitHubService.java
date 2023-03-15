package com.example.branchtakehome.services;

import com.example.branchtakehome.models.GitHubRepo;
import com.example.branchtakehome.models.GitHubUser;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GitHubService {
    ResponseEntity getUser(String username);
    List<GitHubRepo> getRepos(String username);
}
