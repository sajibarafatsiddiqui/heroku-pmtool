package com.inferit.projectmanagementtool.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;


@Data()

public class InvalidLoginResponse {
    private String userName;
    private String password="Invalid Password";

    public InvalidLoginResponse(){
        this.userName="Invalid UserName";
        this.password="Invalid Password";
    }


}
