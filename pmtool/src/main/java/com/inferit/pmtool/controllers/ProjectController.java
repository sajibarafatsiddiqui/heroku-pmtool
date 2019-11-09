package com.inferit.pmtool.controllers;

import com.inferit.pmtool.domain.Project;
import com.inferit.pmtool.domain.ProjectTasks;
import com.inferit.pmtool.services.ProjectService;
import com.inferit.pmtool.services.ProjectTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("api/project/")
@CrossOrigin
public class ProjectController {

    @Autowired
    ProjectService projectService;
    @Autowired
    ProjectTasksService projectTasksService;
    @PostMapping("")
    public ResponseEntity<?> post(@Valid @RequestBody Project project, BindingResult result, Principal principal){
        Map<String,String> errorMap=projectService.errorMap(result);
         if(errorMap!=null){
             return new ResponseEntity<Map<String,String>>(errorMap,HttpStatus.BAD_REQUEST);
         }

        Project project1= projectService.saveOrUpdate(project,principal.getName());
        System.out.println(project1.toString());
        return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
    }

    @GetMapping("/{projectIdentifier}")
    public ResponseEntity<?> getProjectByIdentifier(@PathVariable String projectIdentifier, Principal principal){
        Project project=projectService.findByProjectIdentifier(projectIdentifier,principal.getName());
        if(project!=null) {
            return new ResponseEntity<Project>(project, HttpStatus.OK);
        }
        else
            return new ResponseEntity<String>("Project Does Not Exist",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")

    public ResponseEntity<?> findAll(Principal principal){

        Iterable<Project> projects= projectService.findAll(principal.getName());
        if (projects !=null){
            return new ResponseEntity<Iterable<Project>>(projects,HttpStatus.OK);
        }
        else{
           return new ResponseEntity<String>("Project List is empty",HttpStatus.OK);
        }
    }

    @DeleteMapping("/{projectIdentifier}")
    public ResponseEntity<String> deleteByProjectIdentifier(@PathVariable String projectIdentifier){
        projectService.deleteByIdentifier(projectIdentifier);
        return new ResponseEntity<String>("Project with Identifier '"+projectIdentifier+"' has been deleted",HttpStatus.OK);
    }

    @PostMapping("/{projectIdentifier}")
    public ResponseEntity<?> addProjectTask(@Valid @RequestBody ProjectTasks projectTasks, BindingResult result,@PathVariable String projectIdentifier
    ,Principal principal){
       projectIdentifier.toUpperCase();
       Map<String,String> errorMap =projectService.errorMap(result);

            if (errorMap != null) return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
            projectTasksService.addProjectTask(projectIdentifier, projectTasks,principal.getName());
            return new ResponseEntity<ProjectTasks>(projectTasks, HttpStatus.CREATED);


    }

    @GetMapping("/backlog/{projectIdentifier}")
    public Iterable<ProjectTasks> getProjectTasks(@PathVariable String projectIdentifier,Principal principal) {


            return projectTasksService.findByProjectIdentifier(projectIdentifier,principal.getName());

    }
    @GetMapping("/backlog/{projectIdentifier}/{projectSequence}")
    public ProjectTasks getProjectTasks(@PathVariable String projectIdentifier, @PathVariable String projectSequence,Principal principal) {


        return projectTasksService.findByProjectSequence(projectIdentifier,projectSequence,principal.getName());

    }
    @PatchMapping("/backlog/{projectIdentifier}/{projectSequence}")
    public ResponseEntity<?>updateProjectTask(@Valid @RequestBody ProjectTasks updatedTasks,
                                              @PathVariable String projectIdentifier,@PathVariable String projectSequence,
                                              BindingResult result,Principal principal ){
        Map<String,String>errorMap=projectService.errorMap(result);
        if(errorMap!=null){
            return new ResponseEntity<Map<String,String>>(errorMap,HttpStatus.BAD_REQUEST);
        }

          ProjectTasks projectTasks=projectTasksService.updateProjectTaskByProjectSequence(updatedTasks,projectIdentifier,projectSequence,principal.getName());
            return new ResponseEntity<ProjectTasks>(projectTasks,HttpStatus.CREATED);


    }
    @DeleteMapping("/backlog/{projectIdentifier}/{projectSequence}")
    public ResponseEntity<?>deleteTask(@PathVariable String projectIdentifier,@PathVariable String projectSequence,Principal principal){
       projectTasksService.deleteTask(projectIdentifier,projectSequence,principal.getName());
       return new ResponseEntity<String>("Project has been deleted",HttpStatus.OK);
    }
}
