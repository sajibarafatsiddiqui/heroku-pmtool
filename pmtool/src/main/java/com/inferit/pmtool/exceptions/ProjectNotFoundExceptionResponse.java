package com.inferit.pmtool.exceptions;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
@Data
public class ProjectNotFoundExceptionResponse {

    @Autowired
    private String projectNotFound;

    public ProjectNotFoundExceptionResponse(String projectNotFound) {
        this.projectNotFound= projectNotFound;
    }
}
