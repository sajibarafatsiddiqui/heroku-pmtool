package com.inferit.projectmanagementtool.exceptions;


import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectNotFoundExcepion extends RuntimeException{

    public ProjectNotFoundExcepion(String message) {
        super(message);
    }

}
