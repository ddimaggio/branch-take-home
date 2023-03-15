package com.example.branchtakehome.models.mappers;

import com.example.branchtakehome.models.GitHubRepo;
import com.example.branchtakehome.models.GitHubUser;
import com.example.branchtakehome.models.RepoDTO;
import com.example.branchtakehome.models.UserResponseDTO;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static UserResponseDTO map(GitHubUser user, List<GitHubRepo> repos) {
        UserResponseDTO responseDTO = new UserResponseDTO();

        responseDTO.setUser_name(user.getLogin());
        responseDTO.setDisplay_name(user.getName());
        responseDTO.setAvatar(user.getAvatar_url());
        responseDTO.setGeo_location(user.getLocation());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setUrl(user.getHtml_url());
        responseDTO.setCreated_at(user.getCreated_at().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        List<RepoDTO> repoDTOS = new ArrayList<>();
        for (GitHubRepo g : repos) {
            RepoDTO repo = new RepoDTO();
            repo.setUrl(g.getHtml_url());
            repo.setName(g.getName());
            repoDTOS.add(repo);
        }
        responseDTO.setRepos(repoDTOS);

        return responseDTO;
    }
}
