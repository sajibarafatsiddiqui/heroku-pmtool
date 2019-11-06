package com.inferit.projectmanagementtool.services;

import com.inferit.projectmanagementtool.domain.BackLog;
import com.inferit.projectmanagementtool.domain.ProjectTasks;
import com.inferit.projectmanagementtool.exceptions.ProjectNotFoundExcepion;
import com.inferit.projectmanagementtool.exceptions.SequenceNotFoundException;
import com.inferit.projectmanagementtool.repositories.BackLogRepositories;
import com.inferit.projectmanagementtool.repositories.ProjectRepositories;
import com.inferit.projectmanagementtool.repositories.ProjectTasksRepositories;
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

    public ProjectTasks addProjectTask(String projectIdentifier, ProjectTasks projectTasks) {
        try {
            BackLog backLog = backLogRepositories.findByProjectIdentifier(projectIdentifier);

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

    public Iterable<ProjectTasks> findByProjectIdentifier(String projectIdentifier) {

        if (projectRepositories.findByProjectIdentifier(projectIdentifier) != null) {
            return projectTasksRepositories.findByProjectIdentifier(projectIdentifier);
        } else {
            throw new ProjectNotFoundExcepion("Project with ID " + projectIdentifier + "doesn't exist");
        }

    }

    public ProjectTasks findByProjectSequence(String projectIdentifier, String projectSequence) {
        BackLog backLog = backLogRepositories.findByProjectIdentifier(projectIdentifier);
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
                                                           String projectSequence) {

        ProjectTasks projectTasks = findByProjectSequence(projectIdentifier, projectSequence);
        projectTasks = updatedTask;

        return projectTasksRepositories.save(projectTasks);

    }


    public void deleteTask(String projectIdentifier, String projectSequence) {
        ProjectTasks projectTask=findByProjectSequence(projectIdentifier,projectSequence);
        projectTasksRepositories.delete(projectTask);

    }
}




