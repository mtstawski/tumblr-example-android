package com.braintri.tumblr.model;

import java.util.ArrayList;

/**
 * Created by mike on 25.01.16.
 */
public class Post {

    private String id;
    private String type;
    private String photoCaption;
    private String photoUrl;
    private ArrayList<String> tags;
    private byte[] photo;

    public Post(String id, String type, String photoCaption, String photoUrl, ArrayList<String> tags, byte[] photo) {
        this.id = id;
        this.type = type;
        this.photoCaption = photoCaption;
        this.photoUrl = photoUrl;
        this.tags = tags;
        this.photo = photo;
    }

    public Post(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhotoCaption() {
        return photoCaption;
    }

    public void setPhotoCaption(String photoCaption) {
        this.photoCaption = photoCaption;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public String getTagsByIndex(int index) {
        return tags.get(index);
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}

