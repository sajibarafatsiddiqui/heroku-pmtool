package com.inferit.pmtool.exceptions;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class SequenceNotFoundExceptionResponse {

    @Autowired
    private String SequenceNotFound;

    public SequenceNotFoundExceptionResponse(String projectNotFound) {
        this.SequenceNotFound= projectNotFound;
    }
}

