package com.sv.secureentry.adapters;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VisitorList {
    private final String name;
    private final String mobile;
    private final String work;
    private final String place;
    private final String visitorName;
    private final String imageUrl;
    private final Date visitingDate;

    // Constructor
    public VisitorList(String name, String mobile, String work, String place, String visitorName, String imageUrl, Date visitingDate) {
        this.name = name;
        this.mobile = mobile;
        this.work = work;
        this.place = place;
        this.visitorName = visitorName;
        this.imageUrl = imageUrl;
        this.visitingDate = visitingDate;
    }

    public String getVisitingDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

        // Create a SimpleDateFormat object for the desired date format
        @SuppressLint("SimpleDateFormat") SimpleDateFormat desiredFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedDateStr;
        try {
            Date date = originalFormat.parse(visitingDate.toString());
            assert date != null;
            formattedDateStr = desiredFormat.format(date);
            System.out.println("Formatted Date: " + formattedDateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return formattedDateStr;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getWork() {
        return work;
    }

    public String getPlace() {
        return place;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
