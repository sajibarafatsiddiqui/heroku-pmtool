package com.inferit.projectmanagementtool.services;

import com.inferit.projectmanagementtool.domain.User;
import com.inferit.projectmanagementtool.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private UserRepositories userRepositories;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println("I am loadUserByUsername");
        User user = userRepositories.findByUserName(userName);
        if (user == null) {System.out.println("I am loadUserByUsername");
        new UsernameNotFoundException("User doesn't exist");}
        return user;

    }

    @Transactional
    public User loadUserByUserId(long id){
        User user=userRepositories.getById(id);
        if (user==null) new UsernameNotFoundException("User id is invalid");
        return user;
    }
}
