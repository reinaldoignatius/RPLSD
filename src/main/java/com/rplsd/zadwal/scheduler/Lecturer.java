package com.rplsd.zadwal.scheduler;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Lecturer {
    private String name;
    private List<Pair<Integer, Integer>> availability;

    public Lecturer(String name, List<Pair<Integer, Integer>> availability) {
      this.name = name;
      this.availability = availability;
    }

    public List<Pair<Integer, Integer>>  getAvailability() {
        return availability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
