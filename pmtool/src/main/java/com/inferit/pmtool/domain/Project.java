package com.inferit.pmtool.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
public class Project {
    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long Id;
    @NotBlank(message="Project Name Should not be blank")
    private String projectName;
    @NotBlank(message="Project Identifier Should not be blank")
    @Size(min=4,max=10,message="Project Identifier should be between 4 to20 charecters")
    @Column(updatable=false,unique=true)
    private String projectIdentifier;
    @NotBlank(message="Project Description Should not be blank")
    private String projectDescription;
    @JsonFormat(pattern="yyyy-mm-dd")
    private Date start_date;
    @JsonFormat(pattern="yyyy-mm-dd")
    private Date end_date;
    @JsonFormat(pattern="yyyy-mm-dd")
    @Column(updatable = false)
    private Date created_at;
    @JsonFormat(pattern="yyyy-mm-dd")
    private Date updated_at;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy="project")
    @JsonIgnore
    @ToString.Exclude
    private BackLog backlog;
    @ManyToOne(fetch=FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private User user;
    private String projectLeader;
    @PrePersist
    protected void onCreate(){
        this.created_at=new Date();
    }
    @PreUpdate
    protected void onUpdate() {
        this.updated_at = new Date();
    }



}

