package com.example.realtimetaskmanagement.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginResponseDTO {

    private String accessToken;
    private String refreshToken;
    private UserResponseDTO user;
}
