package com.inferit.pmtool.services;


import com.inferit.pmtool.domain.User;
import com.inferit.pmtool.exceptions.UsernameNotUniqueException;
import com.inferit.pmtool.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepositories userRepositories;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public Map<String,String> errorMap(BindingResult result){

        if (result.hasErrors()){
            Map<String,String> errorMap=new HashMap<>();
            for(FieldError error:result.getFieldErrors()){
                errorMap.put(error.getField(),error.getDefaultMessage());
            }
            return errorMap;
        }
        return null;
    }

    public User saveorupdate(User user){
        try {

            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setConfirmPassword("");
            user.setUserName(user.getUsername());

            return userRepositories.save(user);
        }catch(Exception e){
            throw new UsernameNotUniqueException("User '"+user.getUsername()+"' is already exist!");
        }
    }
}
