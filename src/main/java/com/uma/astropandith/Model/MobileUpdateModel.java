package com.uma.astropandith.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileUpdateModel {

    @SerializedName("update_status")
    @Expose
    private Integer updateStatus;
    @SerializedName("msg")
    @Expose
    private String msg;

    public Integer getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Integer updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
