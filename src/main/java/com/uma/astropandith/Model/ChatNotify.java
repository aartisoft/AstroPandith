package com.uma.astropandith.Model;

public class ChatNotify {

    private String sender;
    private String receiver;
    private String message;
    private String image;
    private String date;
    private String time;
    private String name;
    private boolean isseen;


    public ChatNotify(String sender, String receiver, String message, String image, String date, String time, boolean isseen) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.image = image;
        this.date = date;
        this.time = time;
        this.isseen = isseen;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ChatNotify() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }
}
