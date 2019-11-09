package com.inferit.pmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class BackLog {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;


    private String projectIdentifier;

    private int projectSequence;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="project_id",updatable = false)
    @JsonIgnore
    private Project project;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH, mappedBy="backlog", orphanRemoval=true)
    @ToString.Exclude
    private List<ProjectTasks> projectTasks= new ArrayList();

    public void Backlog(){}
    public void Backlog(String projectIdentifier){this.projectIdentifier=projectIdentifier;}



}
