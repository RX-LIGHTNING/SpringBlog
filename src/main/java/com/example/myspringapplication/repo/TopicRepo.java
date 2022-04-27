package com.example.myspringapplication.repo;

import com.example.myspringapplication.models.Comment;
import com.example.myspringapplication.models.Topic;
import com.example.myspringapplication.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TopicRepo extends CrudRepository<Topic, Long>
{
    @Query("SELECT s FROM Topic s JOIN s.tags t WHERE t = LOWER(:tag)")
    List<Topic> retrieveByTag(@Param("tag") String tag);
    List<Topic> findTopicsByUser(User user);
    List<Topic> findAllByPublishDateBetween(Date to, Date from);
    List<Topic> findAllByArticleContainsIgnoreCase(String article);
}
