package com.example.first.model;

import android.util.Log;

import org.json.JSONObject;

public class User {


    private String token;
    private String name;
    private String picture;
    private String phoneNumber;
    private String email;
    private String reportAccess;
    private boolean hasItinerary;
    private int userId;

    public User() {
    }

    public User( String token, String name, String picture, String phoneNumber, String email, String reportAccess, boolean hasItinerary, int userId) {
        this.token = token;
        this.name = name;
        this.picture = picture;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.reportAccess = reportAccess;
        this.hasItinerary = hasItinerary;
        this.userId = userId;
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReportAccess() {
        return reportAccess;
    }

    public void setReportAccess(String reportAccess) {
        this.reportAccess = reportAccess;
    }

    public boolean isHasItinerary() {
        return hasItinerary;
    }

    public void setHasItinerary(boolean hasItinerary) {
        this.hasItinerary = hasItinerary;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", reportAccess='" + reportAccess + '\'' +
                ", hasItinerary=" + hasItinerary +
                ", userId=" + userId +
                '}';
    }

    public static User getUserFromJson(JSONObject jsonObject)
    {
        User user = null;

        try{
            user = new User(jsonObject.getString("token"),jsonObject.getString("name"),jsonObject.getString("picture"),jsonObject.getString("phoneNumber"),jsonObject.getString("email"),
                    jsonObject.getString("reportAccess"),jsonObject.getBoolean("hasItinerary"),jsonObject.getInt("userId"));
        }catch (Exception e)
        {
            Log.e("ERROR",e.toString());
        }

        return user;
    }
}
