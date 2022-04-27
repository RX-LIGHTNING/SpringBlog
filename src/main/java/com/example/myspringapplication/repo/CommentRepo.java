package com.example.myspringapplication.repo;

import com.example.myspringapplication.models.Comment;
import com.example.myspringapplication.models.Topic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommentRepo extends CrudRepository<Comment, Long> {
    @Transactional
    List<Comment> findAllByTopic(Topic topic);
}
