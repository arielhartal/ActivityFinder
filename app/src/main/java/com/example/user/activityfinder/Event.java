package com.example.user.activityfinder;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private String name;
    private String host;
    private String category;
    private String date;
    private String time;
    private String place; //address
    private int max;
    private ArrayList<String> visitorList;
    private int amount;
    private boolean ifDistance;

    public Event() {
    }

    public Event(String name, String host, String category, String date, String place, String time, int max, ArrayList<String> visitorList, int amount/*, boolean ifDistance*/) {

        this.name = name;
        this.host = host;
        this.category = category;
        this.date = date;
        this.place = place;
        this.time = time;
        this.max = max;
        this.visitorList = new ArrayList<>();
        this.visitorList = visitorList;
        this.amount = amount;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public ArrayList<String> getVisitorList() {
        return visitorList;
    }

    public void setVisitorList(ArrayList<String> visitorList) {
        this.visitorList = visitorList;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}

