package com.uma.astropandith.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallHistory {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("pname")
    @Expose
    private String pname;
    @SerializedName("pphone")
    @Expose
    private String pphone;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("uname")
    @Expose
    private String uname;
    @SerializedName("uphone")
    @Expose
    private String uphone;
    @SerializedName("catime")
    @Expose
    private String catime;
    @SerializedName("cadate")
    @Expose
    private String cadate;
    @SerializedName("caduration")
    @Expose
    private String caduration;
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
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("callStatus")
    @Expose
    private String callStatus;
    @SerializedName("callStatusValue")
    @Expose
    private String callStatusValue;
    @SerializedName("call_transfer_duration")
    @Expose
    private String callTransferDuration;
    @SerializedName("call_transfer_status")
    @Expose
    private String callTransferStatus;
    @SerializedName("call_uuid")
    @Expose
    private String callUuid;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("bonus_minutes_user")
    @Expose
    private String bonusMinutesUser;
    @SerializedName("bonus_minutes_form_pandit")
    @Expose
    private String bonusMinutesFormPandit;
    @SerializedName("bonus_minutes_form_admin")
    @Expose
    private String bonusMinutesFormAdmin;
    @SerializedName("coupon_code")
    @Expose
    private String couponCode;
    @SerializedName("coupon_code_status")
    @Expose
    private String couponCodeStatus;


    @SerializedName("customer_status")
    @Expose
    private String customerStatus;



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

    public String getPphone() {
        return pphone;
    }

    public void setPphone(String pphone) {
        this.pphone = pphone;
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

    public String getUphone() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public String getCatime() {
        return catime;
    }

    public void setCatime(String catime) {
        this.catime = catime;
    }

    public String getCadate() {
        return cadate;
    }

    public void setCadate(String cadate) {
        this.cadate = cadate;
    }

    public String getCaduration() {
        return caduration;
    }

    public void setCaduration(String caduration) {
        this.caduration = caduration;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus;
    }

    public String getCallStatusValue() {
        return callStatusValue;
    }

    public void setCallStatusValue(String callStatusValue) {
        this.callStatusValue = callStatusValue;
    }

    public String getCallTransferDuration() {
        return callTransferDuration;
    }

    public void setCallTransferDuration(String callTransferDuration) {
        this.callTransferDuration = callTransferDuration;
    }

    public String getCallTransferStatus() {
        return callTransferStatus;
    }

    public void setCallTransferStatus(String callTransferStatus) {
        this.callTransferStatus = callTransferStatus;
    }

    public String getCallUuid() {
        return callUuid;
    }

    public void setCallUuid(String callUuid) {
        this.callUuid = callUuid;
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

    public String getBonusMinutesUser() {
        return bonusMinutesUser;
    }

    public void setBonusMinutesUser(String bonusMinutesUser) {
        this.bonusMinutesUser = bonusMinutesUser;
    }

    public String getBonusMinutesFormPandit() {
        return bonusMinutesFormPandit;
    }

    public void setBonusMinutesFormPandit(String bonusMinutesFormPandit) {
        this.bonusMinutesFormPandit = bonusMinutesFormPandit;
    }

    public String getBonusMinutesFormAdmin() {
        return bonusMinutesFormAdmin;
    }

    public void setBonusMinutesFormAdmin(String bonusMinutesFormAdmin) {
        this.bonusMinutesFormAdmin = bonusMinutesFormAdmin;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponCodeStatus() {
        return couponCodeStatus;
    }

    public void setCouponCodeStatus(String couponCodeStatus) {
        this.couponCodeStatus = couponCodeStatus;
    }


    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

}
