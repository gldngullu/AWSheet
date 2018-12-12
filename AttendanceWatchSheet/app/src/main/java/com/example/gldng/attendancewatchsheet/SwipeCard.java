package com.example.gldng.attendancewatchsheet;

import java.util.Calendar;
import java.util.Date;

public class SwipeCard {
    private String name;
    private String courseName;
    private Date today;

    public Date getToday() {
        return today;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getName() {
        return name;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public SwipeCard(String name,String courseName){
        this.name = name;
        this.courseName = courseName;
        today = new Date();
        Calendar calendar = new Calendar() {
            @Override
            protected void computeTime() {

            }

            @Override
            protected void computeFields() {

            }

            @Override
            public void add(int field, int amount) {

            }

            @Override
            public void roll(int field, boolean up) {

            }

            @Override
            public int getMinimum(int field) {
                return 0;
            }

            @Override
            public int getMaximum(int field) {
                return 0;
            }

            @Override
            public int getGreatestMinimum(int field) {
                return 0;
            }

            @Override
            public int getLeastMaximum(int field) {
                return 0;
            }
        };
        today = calendar.getTime();

    }
}