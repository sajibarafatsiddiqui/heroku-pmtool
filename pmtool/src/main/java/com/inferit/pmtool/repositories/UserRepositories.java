package com.inferit.pmtool.repositories;

import com.inferit.pmtool.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositories extends CrudRepository<User,Long> {
User findByUserName(String userName);
User getById(Long Id);
}
