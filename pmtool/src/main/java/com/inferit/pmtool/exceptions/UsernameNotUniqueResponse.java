package com.inferit.pmtool.exceptions;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class UsernameNotUniqueResponse {

    @Autowired
    private String UserNotUnique;

    public UsernameNotUniqueResponse(String UserNotUnique){
        this.UserNotUnique=UserNotUnique;
    }
}
