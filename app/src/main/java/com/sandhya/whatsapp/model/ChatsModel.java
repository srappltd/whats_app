package com.sandhya.whatsapp.model;

public class ChatsModel {
    String name, des, image, date, time, id, uid;

    public ChatsModel(String name, String des, String image, String date, String time, String id, String uid) {
        this.name = name;
        this.des = des;
        this.image = image;
        this.date = date;
        this.time = time;
        this.id = id;
        this.uid = uid;
    }

    public ChatsModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid(String key) {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}