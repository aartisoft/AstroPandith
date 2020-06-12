package com.uma.astropandith.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletHistory {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("panditid")
    @Expose
    private String panditid;
    @SerializedName("panditName")
    @Expose
    private String panditName;
    @SerializedName("walletAmount")
    @Expose
    private String walletAmount;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("astrotype")
    @Expose
    private String astrotype;
    @SerializedName("astrotypeId")
    @Expose
    private String astrotypeId;
    @SerializedName("astrotypeStatusValue")
    @Expose
    private String astrotypeStatusValue;
    @SerializedName("timeDuraton")
    @Expose
    private String timeDuraton;
    @SerializedName("userAmount")
    @Expose
    private String userAmount;
    @SerializedName("taxAmount")
    @Expose
    private String taxAmount;
    @SerializedName("totalTransactionAmount")
    @Expose
    private String totalTransactionAmount;
    @SerializedName("transactionSymbal")
    @Expose
    private String transactionSymbal;
    @SerializedName("paymentThrough")
    @Expose
    private String paymentThrough;
    @SerializedName("orderStatus")
    @Expose
    private String orderStatus;
    @SerializedName("orderStatusValue")
    @Expose
    private String orderStatusValue;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPanditid() {
        return panditid;
    }

    public void setPanditid(String panditid) {
        this.panditid = panditid;
    }

    public String getPanditName() {
        return panditName;
    }

    public void setPanditName(String panditName) {
        this.panditName = panditName;
    }

    public String getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(String walletAmount) {
        this.walletAmount = walletAmount;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAstrotype() {
        return astrotype;
    }

    public void setAstrotype(String astrotype) {
        this.astrotype = astrotype;
    }

    public String getAstrotypeId() {
        return astrotypeId;
    }

    public void setAstrotypeId(String astrotypeId) {
        this.astrotypeId = astrotypeId;
    }

    public String getAstrotypeStatusValue() {
        return astrotypeStatusValue;
    }

    public void setAstrotypeStatusValue(String astrotypeStatusValue) {
        this.astrotypeStatusValue = astrotypeStatusValue;
    }

    public String getTimeDuraton() {
        return timeDuraton;
    }

    public void setTimeDuraton(String timeDuraton) {
        this.timeDuraton = timeDuraton;
    }

    public String getUserAmount() {
        return userAmount;
    }

    public void setUserAmount(String userAmount) {
        this.userAmount = userAmount;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getTotalTransactionAmount() {
        return totalTransactionAmount;
    }

    public void setTotalTransactionAmount(String totalTransactionAmount) {
        this.totalTransactionAmount = totalTransactionAmount;
    }

    public String getTransactionSymbal() {
        return transactionSymbal;
    }

    public void setTransactionSymbal(String transactionSymbal) {
        this.transactionSymbal = transactionSymbal;
    }

    public String getPaymentThrough() {
        return paymentThrough;
    }

    public void setPaymentThrough(String paymentThrough) {
        this.paymentThrough = paymentThrough;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusValue() {
        return orderStatusValue;
    }

    public void setOrderStatusValue(String orderStatusValue) {
        this.orderStatusValue = orderStatusValue;
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
