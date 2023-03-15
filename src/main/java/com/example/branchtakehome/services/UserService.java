package com.example.branchtakehome.services;

import com.example.branchtakehome.models.UserResponseDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<UserResponseDTO> getUser(String username);
}
