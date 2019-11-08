package com.inferit.pmtool.controllers;

import com.inferit.pmtool.Validator.UserValidator;
import com.inferit.pmtool.domain.User;
import com.inferit.pmtool.payload.LoginRequest;
import com.inferit.pmtool.payload.LoginSuccess;
import com.inferit.pmtool.security.JsonTokenProvider;
import com.inferit.pmtool.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static com.inferit.pmtool.security.SecurityConstants.JWT_PREFIX;

@RestController
@RequestMapping("api/users/")
@CrossOrigin
public class UserController {
@Autowired
private  UserService userService;
@Autowired
private UserValidator userValidator;

@Autowired
private JsonTokenProvider jsonTokenProvider;

@Autowired
private AuthenticationManager authenticationManager;

@PostMapping("signin")
public ResponseEntity<?> signin (@Valid @RequestBody LoginRequest login, BindingResult result){
    Map<String,String> errorMap=userService.errorMap(result);
    if (errorMap!=null) return new ResponseEntity<Map<String,String>>(errorMap, HttpStatus.BAD_REQUEST);
    Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUserName(),login.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt= JWT_PREFIX + jsonTokenProvider.generateToken(authentication);
    System.out.println(jwt);

    return ResponseEntity.ok(new LoginSuccess(true,jwt));
}

@PostMapping("")
 public ResponseEntity<?> newUser(@Valid @RequestBody User user, BindingResult result) {
    userValidator.validate(user,result);
    Map<String,String> errorMap=userService.errorMap(result);
    if(errorMap!=null){
        return new ResponseEntity<Map<String,String>>(errorMap, HttpStatus.BAD_REQUEST);
    }

    User user1=userService.saveorupdate(user);

    return new ResponseEntity<String>(user1.toString(),HttpStatus.CREATED);

}

}
