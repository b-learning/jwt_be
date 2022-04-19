package com.example.jwt.dto.request;

import lombok.Data;

@Data
public class LoginForm {
    String username;
    String password;
}
