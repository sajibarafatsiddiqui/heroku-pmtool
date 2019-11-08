package com.inferit.pmtool.repositories;

import com.inferit.pmtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ProjectRepositories extends CrudRepository<Project,Long> {
    @Override
    default void deleteById(Long aLong) {}


        Iterable<Project> findAllByProjectLeader (String projectLeader);


        Project findByProjectIdentifier (String id);



}