package com.skyshi.sms.Model;

/**
 * Created by skyshi on 12/04/16.
 */
public class SmsModel {
    private String address;
    private String body;
    private String date;
    private String date_sent;
    private int count;
    public SmsModel(){}
    public SmsModel(String address,String body,String date,String date_sent,int count){
        this.address = address;
        this.body = body;
        this.date = date;
        this.date_sent = date_sent;
        this.setCount(count);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate_sent() {
        return date_sent;
    }

    public void setDate_sent(String date_sent) {
        this.date_sent = date_sent;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
