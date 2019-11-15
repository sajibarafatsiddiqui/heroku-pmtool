package com.inferit.pmtool.exceptions;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class ProjectIdExceptionResponse {
    @Autowired
    private String ProjectIdentifier;

    public ProjectIdExceptionResponse(String ProjectIdentifier) {
        this.ProjectIdentifier=ProjectIdentifier;
    }
}
