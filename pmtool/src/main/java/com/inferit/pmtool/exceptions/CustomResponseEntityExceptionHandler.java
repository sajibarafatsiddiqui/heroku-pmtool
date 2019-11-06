package com.inferit.projectmanagementtool.exceptions;
import com.inferit.projectmanagementtool.exceptions.ProjectIdException;
import com.inferit.projectmanagementtool.exceptions.ProjectIdExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class  CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> exceptionHandler(ProjectIdException ex, WebRequest request){
        ProjectIdExceptionResponse projectIdExceptionResponse=new ProjectIdExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(projectIdExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Object> exceptionHandler(ProjectNotFoundExcepion ex, WebRequest request){
        ProjectNotFoundExceptionResponse projectNotFoundExceptionResponse=new ProjectNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(projectNotFoundExceptionResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<Object> exceptionHandler(SequenceNotFoundException ex, WebRequest request){
        SequenceNotFoundExceptionResponse sequenceNotFoundExceptionResponse=new SequenceNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(sequenceNotFoundExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Object> exceptionHandler(UsernameNotUniqueException ex, WebRequest request){
        UsernameNotUniqueResponse usernameNotUniqueResponse=new UsernameNotUniqueResponse(ex.getMessage());
        return new ResponseEntity<>(usernameNotUniqueResponse,HttpStatus.BAD_REQUEST);
    }
}
