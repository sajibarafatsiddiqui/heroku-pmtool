package com.inferit.pmtool.exceptions;

import lombok.Data;


@Data
public class InvalidLoginResponse {
    private String userName;
    private String password="Invalid Password";

    public InvalidLoginResponse(){
        this.userName="Invalid UserName";
        this.password="Invalid Password";
    }


}
