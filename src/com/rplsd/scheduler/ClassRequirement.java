package com.rplsd.scheduler;

import java.util.List;

public class ClassRequirement {
  private int minimumCapacity;
  private int maximumCapacity;
  private List<String> facilities;
  private int hours;
  private List<String> lecturers;

  public ClassRequirement(int minimumCapacity, int maximumCapacity, List<String> facilities, int hours, List<String> lecturers) {
    this.minimumCapacity = minimumCapacity;
    this.maximumCapacity = maximumCapacity;
    this.facilities = facilities;
    this.hours = hours;
    this.lecturers = lecturers;
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

  public List<String> getFacilities() {
    return facilities;
  }

  public void setFacilities(List<String> facilities) {
    this.facilities = facilities;
  }

  public int getHours() {
    return hours;
  }

  public void setHours(int hours) {
    this.hours = hours;
  }

  public List<String> getLecturers() {
    return lecturers;
  }

  public void setLecturers(List<String> lecturers) {
    this.lecturers = lecturers;
  }
}
