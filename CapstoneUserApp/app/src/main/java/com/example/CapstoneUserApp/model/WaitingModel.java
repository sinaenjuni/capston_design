package com.example.CapstoneUserApp.model;

public class WaitingModel {
    public String num;
    public String phoneNumber;
    public String people;
    public String sms;

    public WaitingModel() {
    }

    public WaitingModel(String num, String phoneNumber, String people) {
        this.num = num;
        this.phoneNumber = phoneNumber;
        this.people = people;
    }

    public WaitingModel(String num, String phoneNumber, String people, String sms) {
        this.num = num;
        this.phoneNumber = phoneNumber;
        this.people = people;
        this.sms = sms;
    }

    public String getNum() { return num; }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getSms() { return sms; }

    public void setSms(String sms) { this.sms = sms; }

}
