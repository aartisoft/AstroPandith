package com.uma.astropandith.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatStatusValueModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("chatStatus")
    @Expose
    private String chatStatus;
    @SerializedName("chatStatusValue")
    @Expose
    private String chatStatusValue;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getChatStatus() {
        return chatStatus;
    }

    public void setChatStatus(String chatStatus) {
        this.chatStatus = chatStatus;
    }

    public String getChatStatusValue() {
        return chatStatusValue;
    }

    public void setChatStatusValue(String chatStatusValue) {
        this.chatStatusValue = chatStatusValue;
    }
}
