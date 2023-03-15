package com.example.branchtakehome.models;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {
    private String user_name;
    private String display_name;
    private String avatar;
    private String geo_location;
    private String email;
    private String url;
    private String created_at;
    private List<RepoDTO> repos;
}
