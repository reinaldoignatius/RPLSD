package com.rplsd.zadwal.scheduler;

import java.util.List;

public class Course {
  private String name;
  private int minimumCapacity;
  private int maximumCapacity;
  private List<String> facilities;
  private int hours;
  private List<String> lecturers;

  public Course(String name, int minimumCapacity, int maximumCapacity, List<String> facilities, int hours, List<String> lecturers) {
    this.name = name;
    this.minimumCapacity = minimumCapacity;
    this.maximumCapacity = maximumCapacity;
    this.facilities = facilities;
    this.hours = hours;
    this.lecturers = lecturers;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
