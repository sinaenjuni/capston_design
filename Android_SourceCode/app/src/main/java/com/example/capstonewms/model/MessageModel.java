package com.example.capstonewms.model;

public class MessageModel {
    public String phoneNumber;
    public String sms;

    public MessageModel() {
    }

    public MessageModel(String phoneNumber, String sms) {
        this.phoneNumber = phoneNumber;
        this.sms = sms;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSms() { return sms; }

    public void setSms(String sms) { this.sms = sms; }

}
