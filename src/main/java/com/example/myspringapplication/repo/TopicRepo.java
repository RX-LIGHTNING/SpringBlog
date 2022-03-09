package com.example.myspringapplication.repo;

import com.example.myspringapplication.models.Topic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepo extends CrudRepository<Topic, Long>
{

}