package com.inferit.projectmanagementtool.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
@Data
@Entity
public class ProjectTasks {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Summary should not be blank")
    private String summary;
    private String status;
    private int priority;
    @JsonFormat(pattern="yyyy-mm-dd")
    private Date dueDate;
    private String acceptanceCriteria;
    @JsonFormat(pattern="yyyy-mm-dd")
    private Date create_date;
    private String projectIdentifier;
    private String projectSequence;
    @JsonFormat(pattern="yyyy-mm-dd")
    private Date update_date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="backlog_id",updatable = false, nullable=false)
    @JsonIgnore
    private BackLog backlog;
    @PrePersist
    public void onCreate(){
        this.create_date=new Date();
    }
    @PreUpdate
    public void onUpdate(){
        this.update_date=new Date();
    }

}
