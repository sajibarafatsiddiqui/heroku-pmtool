package com.inferit.pmtool.services;

import com.inferit.pmtool.domain.BackLog;
import com.inferit.pmtool.domain.ProjectTasks;
import com.inferit.pmtool.exceptions.ProjectNotFoundExcepion;
import com.inferit.pmtool.exceptions.SequenceNotFoundException;
import com.inferit.pmtool.repositories.BackLogRepositories;
import com.inferit.pmtool.repositories.ProjectRepositories;
import com.inferit.pmtool.repositories.ProjectTasksRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTasksService {
    @Autowired
    ProjectTasksRepositories projectTasksRepositories;
    @Autowired
    BackLogRepositories backLogRepositories;
    @Autowired
    ProjectRepositories projectRepositories;
    @Autowired
    ProjectService projectService;

    public ProjectTasks addProjectTask(String projectIdentifier, ProjectTasks projectTasks, String userName) {
        try {
            BackLog backLog = projectService.findByProjectIdentifier(projectIdentifier,userName).getBacklog();

            Integer backLogSequence = backLog.getProjectSequence();
            backLogSequence++;
            backLog.setProjectSequence(backLogSequence);
            projectTasks.setBacklog(backLog);
            projectTasks.setProjectIdentifier(projectIdentifier);
            projectTasks.setProjectSequence(projectIdentifier + "-" + backLogSequence);
            return projectTasksRepositories.save(projectTasks);
        } catch (Exception e) {
            throw new ProjectNotFoundExcepion("Project with ID " + projectIdentifier + " doesn't exist");
        }
    }

    public Iterable<ProjectTasks> findByProjectIdentifier(String projectIdentifier, String userName) {

        if (projectService.findByProjectIdentifier(projectIdentifier,userName) != null) {
            return projectTasksRepositories.findByProjectIdentifier(projectIdentifier);
        } else {
            throw new ProjectNotFoundExcepion("Project with ID " + projectIdentifier + "doesn't exist");
        }

    }

    public ProjectTasks findByProjectSequence(String projectIdentifier, String projectSequence, String userName) {
        BackLog backLog = projectService.findByProjectIdentifier(projectIdentifier,userName).getBacklog();
        if (backLog == null) {
            throw new ProjectNotFoundExcepion("Project with ID " + projectIdentifier + " doesn't exist");
        }

        ProjectTasks projectTasks = projectTasksRepositories.findByProjectSequence(projectSequence);
        if (projectTasks == null) {
            throw new SequenceNotFoundException("ProjectTask with ID " + projectSequence + " doesn't exist");
        }
        if (!projectTasks.getBacklog().equals(backLog)) {

            throw new ProjectNotFoundExcepion("Project with ID " + projectSequence + " doesn't belong to " + projectIdentifier);
        }
        return projectTasks;

    }

    public ProjectTasks updateProjectTaskByProjectSequence(ProjectTasks updatedTask, String projectIdentifier,
                                                           String projectSequence,String userName) {

        ProjectTasks projectTasks = findByProjectSequence(projectIdentifier, projectSequence,userName);
        projectTasks = updatedTask;

        return projectTasksRepositories.save(projectTasks);

    }


    public void deleteTask(String projectIdentifier, String projectSequence,String userName) {
        ProjectTasks projectTask=findByProjectSequence(projectIdentifier,projectSequence,userName);
        projectTasksRepositories.delete(projectTask);

    }
}




