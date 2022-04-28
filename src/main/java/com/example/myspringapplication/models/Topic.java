package com.example.myspringapplication.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
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
    private User user;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "topic_id")
    private List<Comment> comments;
    private String filename;
    @ElementCollection
    private List<String> tags = new ArrayList<>();


    public List<String> getTags() {
        return tags;
    }

    public String getTagsAsString() {
        return String.join(" ", tags);
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public com.example.myspringapplication.models.User getUser() {
        return user;
    }

    public void setUser(com.example.myspringapplication.models.User user) {
        user = user;
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

    public Topic() {

    }

    public Topic(String description, String article, com.example.myspringapplication.models.User user, List<String> tags, String filename) {
        this.id = 0L;
        this.description = description;
        this.article = article;
        this.views = 0;
        this.user = user;
        this.publishDate = new Date();
        this.tags = tags;
        this.filename = filename;
    }
}
