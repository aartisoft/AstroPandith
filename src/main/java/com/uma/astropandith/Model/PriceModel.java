package com.uma.astropandith.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PriceModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("reportPFprice")
    @Expose
    private String reportPFprice;
    @SerializedName("chatPFprice")
    @Expose
    private String chatPFprice;
    @SerializedName("callPFprice")
    @Expose
    private String callPFprice;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportPFprice() {
        return reportPFprice;
    }

    public void setReportPFprice(String reportPFprice) {
        this.reportPFprice = reportPFprice;
    }

    public String getChatPFprice() {
        return chatPFprice;
    }

    public void setChatPFprice(String chatPFprice) {
        this.chatPFprice = chatPFprice;
    }

    public String getCallPFprice() {
        return callPFprice;
    }

    public void setCallPFprice(String callPFprice) {
        this.callPFprice = callPFprice;
    }
}
