package com.inferit.projectmanagementtool.payload;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;


@Component
@Data
public class LoginRequest {
    @NotBlank(message="Username cant be blank!")
    private String userName;
    @NotBlank(message="Password cant be blank!")
    public String password;

    @Override
    public String toString() {
        return "LoginRequest{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
