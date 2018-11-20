package com.rplsd.scheduler;

public class SchedulePreference {
  private int hourPriority[][] = new int[5][11];

  public SchedulePreference(int[][] hourPriority) {
    this.hourPriority = hourPriority;
  }

  public int[][] getHourPriority() {
    return hourPriority;
  }

  public void setHourPriority(int[][] hourPriority) {
    this.hourPriority = hourPriority;
  }

  public void setHourPriority(int day, int time, int priority) {
    try {
      this.hourPriority[day][time] = priority;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
