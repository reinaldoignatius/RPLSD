package com.rplsd.scheduler;

public class ScheduleConstraint {
  private int maxClassPerHour;

  public ScheduleConstraint(int maxClassPerHour) {
    this.maxClassPerHour = maxClassPerHour;
  }

  public int getMaxClassPerHour() {
    return maxClassPerHour;
  }

  public void setMaxClassPerHour(int maxClassPerHour) {
    this.maxClassPerHour = maxClassPerHour;
  }
}
