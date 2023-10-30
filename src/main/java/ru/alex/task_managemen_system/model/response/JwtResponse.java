package ru.alex.task_managemen_system.model.response;

import lombok.Data;

import java.util.UUID;

@Data
public class JwtResponse {

    private String uuid;
    private String username;
    private String accessToken;
    private String refreshToken;
}
