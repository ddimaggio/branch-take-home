package com.example.branchtakehome.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GitHubUser {
    private String login;
    private String avatar_url;
    private String html_url;
    private String name;
    private String location;
    private String email;
    private LocalDateTime created_at;
}
