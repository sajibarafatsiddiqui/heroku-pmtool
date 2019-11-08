package com.inferit.pmtool.exceptions;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
public class ProjectIdExceptionResponse {
    @Autowired
    private String ProjectIdentifier;

    public ProjectIdExceptionResponse(String ProjectIdentifier) {
        this.ProjectIdentifier=ProjectIdentifier;
    }
}
