package com.example.myspringapplication.models;

import javax.persistence.*;


@Entity
@Table(name = "Topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private String article;
    private int views;
    @OneToOne
    private com.example.myspringapplication.models.User User;

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
    }
}
