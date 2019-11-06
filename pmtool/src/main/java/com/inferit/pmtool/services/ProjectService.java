package com.inferit.projectmanagementtool.services;

import com.inferit.projectmanagementtool.domain.BackLog;
import com.inferit.projectmanagementtool.domain.Project;
import com.inferit.projectmanagementtool.domain.User;
import com.inferit.projectmanagementtool.exceptions.ProjectIdException;
import com.inferit.projectmanagementtool.repositories.BackLogRepositories;
import com.inferit.projectmanagementtool.repositories.ProjectRepositories;
import com.inferit.projectmanagementtool.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProjectService {

    @Autowired
    ProjectRepositories projectRepositories;
    @Autowired
    UserRepositories userRepositories;
    @Autowired
    BackLogRepositories backLogRepositories;

    public Project saveOrUpdate(Project project, String userName){
        try {
            User user=userRepositories.findByUserName(userName);
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            if(project.getId()==null){
                BackLog backLog=new BackLog();
                backLog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
                backLog.setProject(project);
                project.setBacklog(backLog);

            }

            if(project.getId()!=null){

              project.setBacklog(backLogRepositories.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));

            }
            project.setUser(user);
            project.setProjectLeader(userName);
            return projectRepositories.save(project);
        } catch(Exception e){
            throw new ProjectIdException("ProjectIdentifier '"+project.getProjectIdentifier().toUpperCase()+"' is aleady exist");
        }
    }

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
    public Project findByProjectIdentifier(String projectIdentifier, String userName){

        Project project=projectRepositories.findByProjectIdentifier(projectIdentifier);
        if (project.getUser().getUserName().equals(userName)){
            return project;
        }
        return null;
    }

    public Iterable<Project> findAll(String projectLeader){
        return projectRepositories.findAllByProjectLeader(projectLeader);
    }

    public void deleteByIdentifier(String projectIdentifier){
        projectIdentifier.toUpperCase();
        Project project=projectRepositories.findByProjectIdentifier(projectIdentifier);
        if(project==null){
            throw new ProjectIdException("Project with id '"+ projectIdentifier+"' doesn't exist");
        }
        projectRepositories.delete(project);


    }

}
