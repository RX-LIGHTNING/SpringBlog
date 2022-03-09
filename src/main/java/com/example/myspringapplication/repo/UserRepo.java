package com.example.myspringapplication.repo;

import com.example.myspringapplication.models.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<Users, Long> {
}
