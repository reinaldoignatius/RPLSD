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
}
