package com.uma.astropandith.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletModel {

    @SerializedName("p_status")
    @Expose
    private String pStatus;
    @SerializedName("walletAmount")
    @Expose
    private String walletAmount;

    public String getPStatus() {
        return pStatus;
    }

    public void setPStatus(String pStatus) {
        this.pStatus = pStatus;
    }

    public String getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(String walletAmount) {
        this.walletAmount = walletAmount;
    }

}
