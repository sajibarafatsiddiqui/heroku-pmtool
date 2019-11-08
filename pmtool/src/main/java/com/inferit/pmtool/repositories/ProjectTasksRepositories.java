package com.inferit.pmtool.repositories;


import com.inferit.pmtool.domain.ProjectTasks;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTasksRepositories extends CrudRepository<ProjectTasks,Long> {


    List<ProjectTasks> findByProjectIdentifier(String projectIdentifier);



    @Override
    Iterable<ProjectTasks> findAll();

  ProjectTasks findByProjectSequence(String projectSequence);
}
