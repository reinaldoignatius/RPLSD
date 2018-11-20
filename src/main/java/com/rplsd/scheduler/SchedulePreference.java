package com.rplsd.scheduler;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.rplsd.scheduler.Constants.HOURS_IN_A_DAY;

public class SchedulePreference {
  private ArrayList<ArrayList<Integer>> hourPriorities;

  public SchedulePreference() {
    hourPriorities = new ArrayList<>(5);
    for (ArrayList<Integer> day: hourPriorities) {
      day = new ArrayList<>(HOURS_IN_A_DAY);
      for (Integer time: day) {
        time = 0;
      }
    }
  }

  public ArrayList<ArrayList<Integer>> getHourPriorities() {
    return hourPriorities;
  }
}
