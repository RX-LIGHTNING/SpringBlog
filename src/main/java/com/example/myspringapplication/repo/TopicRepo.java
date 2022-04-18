package com.example.myspringapplication.repo;

import com.example.myspringapplication.models.Topic;
import com.example.myspringapplication.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepo extends CrudRepository<Topic, Long>
{
    List<Topic> findTopicsByUser(User user);
}
