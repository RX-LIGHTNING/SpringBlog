package com.example.myspringapplication.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "Topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String article;
    private int views;
    private Date publishDate;
    @OneToOne
    private User User;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Comment> Comments;

    public void setId(Long id) {
        this.id = id;
    }

    public List<Comment> getComments() {
        return Comments;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public void setComments(List<Comment> comments) {
        Comments = comments;
    }

    public Topic() {

    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public com.example.myspringapplication.models.User getUser() {
        return User;
    }

    public void setUser(com.example.myspringapplication.models.User user) {
        User = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Topic(String description, String article, com.example.myspringapplication.models.User user) {
        this.id = 0L;
        this.description = description;
        this.article = article;
        this.views = 0;
        User = user;
        this.publishDate = new Date();
    }
}
