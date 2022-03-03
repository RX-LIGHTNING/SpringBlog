package com.example.myspringapplication.models;
import org.springframework.context.annotation.Bean;

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
    private Users User;

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public Users getUser() {
        return User;
    }

    public void setUser(Users user) {
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
}
