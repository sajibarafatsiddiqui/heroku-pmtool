package com.inferit.pmtool.repositories;

import com.inferit.pmtool.domain.BackLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackLogRepositories extends CrudRepository<BackLog,Long> {

     BackLog findByProjectIdentifier(String projectIdentifier);
}
