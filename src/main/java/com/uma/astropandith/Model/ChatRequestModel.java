package com.uma.astropandith.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatRequestModel {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("pname")
    @Expose
    private String pname;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("uname")
    @Expose
    private String uname;
    @SerializedName("chtime")
    @Expose
    private String chtime;
    @SerializedName("chdate")
    @Expose
    private String chdate;
    @SerializedName("chduration")
    @Expose
    private String chduration;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("pandit_geting_amount")
    @Expose
    private String panditGetingAmount;
    @SerializedName("admin_geting_amount")
    @Expose
    private String adminGetingAmount;
    @SerializedName("feedback")
    @Expose
    private String feedback;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("chatStatus")
    @Expose
    private String chatStatus;
    @SerializedName("chatStatusValue")
    @Expose
    private String chatStatusValue;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("mins")
    @Expose
    private String mins;

    public String getRequest_from() {
        return request_from;
    }

    public void setRequest_from(String request_from) {
        this.request_from = request_from;
    }

    @SerializedName("request_from")
    @Expose
    private String request_from;

    public String getMins() {
        return mins;
    }

    public void setMins(String mins) {
        this.mins = mins;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getChtime() {
        return chtime;
    }

    public void setChtime(String chtime) {
        this.chtime = chtime;
    }

    public String getChdate() {
        return chdate;
    }

    public void setChdate(String chdate) {
        this.chdate = chdate;
    }

    public String getChduration() {
        return chduration;
    }

    public void setChduration(String chduration) {
        this.chduration = chduration;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPanditGetingAmount() {
        return panditGetingAmount;
    }

    public void setPanditGetingAmount(String panditGetingAmount) {
        this.panditGetingAmount = panditGetingAmount;
    }

    public String getAdminGetingAmount() {
        return adminGetingAmount;
    }

    public void setAdminGetingAmount(String adminGetingAmount) {
        this.adminGetingAmount = adminGetingAmount;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
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
}
