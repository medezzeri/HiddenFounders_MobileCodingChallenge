package com.hf.hiddenfounders.classes;

/**
 * Created by Admin on 04/01/2018.
 */

public class Repo {

    String name, description, owner_username, owner_avatar;
    long stars;

    //--- Constructors
    public Repo() {

    }

    public Repo(String name, String description, String owner_username, String owner_avatar, long stars) {
        this.name = name;
        this.description = description;
        this.owner_username = owner_username;
        this.owner_avatar = owner_avatar;
        this.stars = stars;
    }

    //--- Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner_username() {
        return owner_username;
    }

    public void setOwner_username(String owner_username) {
        this.owner_username = owner_username;
    }

    public String getOwner_avatar() {
        return owner_avatar;
    }

    public void setOwner_avatar(String owner_avatar) {
        this.owner_avatar = owner_avatar;
    }

    public long getStars() {
        return stars;
    }

    public void setStars(long stars) {
        this.stars = stars;
    }
}
