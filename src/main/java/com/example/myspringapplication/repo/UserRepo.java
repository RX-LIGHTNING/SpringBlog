package com.example.myspringapplication.repo;

import com.example.myspringapplication.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    User findUserByUsername(String username);
    List<User> findAllByRegDateBetween(Date from, Date to);
}
