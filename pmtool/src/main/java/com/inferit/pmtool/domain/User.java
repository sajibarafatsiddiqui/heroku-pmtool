package com.inferit.pmtool.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class User implements UserDetails {

    @OneToMany(cascade= CascadeType.REFRESH,fetch=FetchType.EAGER,mappedBy = "user",orphanRemoval = true)
    private List<Project> ProjectList;

    @javax.persistence.Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @Email(message="Username should be an email")
    @NotBlank(message="Username can't be blank")
    @Column(unique=true)
    private String userName;


    @NotBlank(message="password can't be blank")
    private String password;
    @Transient
    private String confirmPassword;

    public long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @NotBlank(message="Fullname is required!")
    private String fullName;

    private Date created_at;
    private Date updated_at;

    @PrePersist
    private void onCreate(){
        this.created_at=new Date();
    }
    private void onUpdate(){
        this.updated_at=new Date();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
