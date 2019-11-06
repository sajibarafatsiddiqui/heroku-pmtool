package com.inferit.projectmanagementtool.repositories;

import com.inferit.projectmanagementtool.domain.BackLog;
import com.inferit.projectmanagementtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BackLogRepositories extends CrudRepository<BackLog,Long> {

     BackLog findByProjectIdentifier(String projectIdentifier);
}
