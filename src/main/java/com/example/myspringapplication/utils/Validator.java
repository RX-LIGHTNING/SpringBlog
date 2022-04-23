package com.example.myspringapplication.utils;

public class Validator {
    public static boolean isMail(String mail){
        return mail.matches("[a-zA-Z0-9_-]+\\@[a-zA-Z0-9_\\\\.-]+");
    }
    public static boolean isPassword(String password){
        return password.matches("\\S{4,}");
    }
    public static boolean isUsername(String Username){
        return Username.matches("\\S{4,}");
    }
    public static boolean isCorrectTopicLength(String Text){return Text.matches("\\.{1,1024}");}
    public static boolean isCorrectTopicArticle(String Text){return Text.matches("\\.{1,255}");}
}
