package com.rplsd.scheduler;

import java.util.List;

public class ClassRequirement {
  private int minimumCapacity;
  private int maximumCapacity;
  private List<String> requiredFacilities;
  private int hours;

  public ClassRequirement(int minimumCapacity, int maximumCapacity, List<String> requiredFacilities, int hours) {
    this.minimumCapacity = minimumCapacity;
    this.maximumCapacity = maximumCapacity;
    this.requiredFacilities = requiredFacilities;
    this.hours = hours;
  }

  public int getMinimumCapacity() {
    return minimumCapacity;
  }

  public void setMinimumCapacity(int minimumCapacity) {
    this.minimumCapacity = minimumCapacity;
  }

  public int getMaximumCapacity() {
    return maximumCapacity;
  }

  public void setMaximumCapacity(int maximumCapacity) {
    this.maximumCapacity = maximumCapacity;
  }

  public List<String> getRequiredFacilities() {
    return requiredFacilities;
  }

  public void setRequiredFacilities(List<String> requiredFacilities) {
    this.requiredFacilities = requiredFacilities;
  }

  public int getHours() {
    return hours;
  }

  public void setHours(int hours) {
    this.hours = hours;
  }
}
