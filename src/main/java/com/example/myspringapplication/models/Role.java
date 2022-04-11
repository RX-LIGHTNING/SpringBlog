package com.example.myspringapplication.models;

public enum Role {
    USER("Casual role"),
    ADMIN("Role, that gives access to users list"),
    CANDELETECOMMENTS("Role, that gives permission to delete comment"),
    CANEDITTOPIC("Role, that gives permission to edit topics"),
    CANDELETETOPIC("Role, that gives permission to delete topics"),
    BANNED("Role, for banned users");
    String description;

    public String getDescription() {
        return description;
    }

    Role(String description) {
        this.description = description;
    }
}
