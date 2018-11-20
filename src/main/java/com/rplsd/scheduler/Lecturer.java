package com.rplsd.scheduler;

import java.util.ArrayList;

public class Lecturer {
    private String name;
    private Boolean availability[][] = new Boolean[5][11];

    public Lecturer(String name, Boolean[][] availability) {
      this.name = name;
      this.availability = availability;
    }

    public Boolean[][] getAvailability() {
        return availability;
    }

    public String getName() {
        return name;
    }

    public void setAvailability(Boolean[][] availability) {
        this.availability = availability;
    }

    public void setAvailability(int day, int time, boolean availability) {
        try {
            this.availability[day][time] = availability;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setName(String name) {
        this.name = name;
    }
}
