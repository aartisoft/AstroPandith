package com.uma.astropandith.Model;

public class ChatRNotify {

    private String sender;
    private String receiver;
    private String chatstatus;
    private boolean chatr;


    public ChatRNotify(String sender, String receiver,String chatstatus, boolean chatr) {
        this.sender = sender;
        this.receiver = receiver;
        this.chatstatus = chatstatus;
        this.chatr = chatr;
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

    public String getChatstatus() {
        return chatstatus;
    }

    public void setChatstatus(String chatstatus) {
        this.chatstatus = chatstatus;
    }

    public boolean isChatr() {
        return chatr;
    }

    public void setChatr(boolean chatr) {
        this.chatr = chatr;
    }
}
