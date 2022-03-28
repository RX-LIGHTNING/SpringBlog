package com.example.myspringapplication.models;

import javax.persistence.*;
@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String comment;
    @OneToOne
    private User user;
    @OneToOne
    private Topic topic;

    public Comment() {

    }

    public Long getId() {
        return id;
    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Comment(String comment, User user, Topic topic) {
        this.comment = comment;
        this.user = user;
        this.topic = topic;
    }
}
