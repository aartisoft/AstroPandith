package com.uma.astropandith.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StausMoldel {
    @SerializedName("p_status")
    @Expose
    private Integer pStatus;
    @SerializedName("rstatus")
    @Expose
    private String rstatus;
    @SerializedName("rsedate")
    @Expose
    private String rsedate;
    @SerializedName("rsetime")
    @Expose
    private String rsetime;
    @SerializedName("chatstatus")
    @Expose
    private String chatstatus;
    @SerializedName("chsedate")
    @Expose
    private String chsedate;
    @SerializedName("chsetime")
    @Expose
    private String chsetime;
    @SerializedName("callstatus")
    @Expose
    private String callstatus;
    @SerializedName("csedate")
    @Expose
    private String csedate;
    @SerializedName("csetime")
    @Expose
    private String csetime;
    @SerializedName("admin_rlock")
    @Expose
    private String adminRlock;
    @SerializedName("admin_chlock")
    @Expose
    private String adminChlock;
    @SerializedName("admin_calock")
    @Expose
    private String adminCalock;

    public Integer getPStatus() {
        return pStatus;
    }

    public void setPStatus(Integer pStatus) {
        this.pStatus = pStatus;
    }

    public String getRstatus() {
        return rstatus;
    }

    public void setRstatus(String rstatus) {
        this.rstatus = rstatus;
    }

    public String getRsedate() {
        return rsedate;
    }

    public void setRsedate(String rsedate) {
        this.rsedate = rsedate;
    }

    public String getRsetime() {
        return rsetime;
    }

    public void setRsetime(String rsetime) {
        this.rsetime = rsetime;
    }

    public String getChatstatus() {
        return chatstatus;
    }

    public void setChatstatus(String chatstatus) {
        this.chatstatus = chatstatus;
    }

    public String getChsedate() {
        return chsedate;
    }

    public void setChsedate(String chsedate) {
        this.chsedate = chsedate;
    }

    public String getChsetime() {
        return chsetime;
    }

    public void setChsetime(String chsetime) {
        this.chsetime = chsetime;
    }

    public String getCallstatus() {
        return callstatus;
    }

    public void setCallstatus(String callstatus) {
        this.callstatus = callstatus;
    }

    public String getCsedate() {
        return csedate;
    }

    public void setCsedate(String csedate) {
        this.csedate = csedate;
    }

    public String getCsetime() {
        return csetime;
    }

    public void setCsetime(String csetime) {
        this.csetime = csetime;
    }

    public String getAdminRlock() {
        return adminRlock;
    }

    public void setAdminRlock(String adminRlock) {
        this.adminRlock = adminRlock;
    }

    public String getAdminChlock() {
        return adminChlock;
    }

    public void setAdminChlock(String adminChlock) {
        this.adminChlock = adminChlock;
    }

    public String getAdminCalock() {
        return adminCalock;
    }

    public void setAdminCalock(String adminCalock) {
        this.adminCalock = adminCalock;
    }

}
