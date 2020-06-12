package com.uma.astropandith.Model;

public class ChatPop {

    private String sender;
    private String receiver;
    private String message;
    private String image;
    private String date;
    private String time;
    private String chatid;


    public ChatPop(String sender, String receiver, String message, String image, String date, String time, String chatid) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.image = image;
        this.date = date;
        this.time = time;
        this.chatid = chatid;
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

    public ChatPop() {
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


    public String getChatid() {
        return chatid;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }

}
