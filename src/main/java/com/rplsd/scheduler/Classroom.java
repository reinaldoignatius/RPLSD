package com.rplsd.scheduler;

import java.util.List;

public class Classroom {
  private String id;
  private int capacity;
  private List<String> facilities;

  public Classroom(String id, int capacity, List<String> facilities) {
    this.id = id;
    this.capacity = capacity;
    this.facilities = facilities;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public List<String> getFacilities() {
    return facilities;
  }

  public void setFacilities(List<String> facilities) {
    this.facilities = facilities;
  }

  public boolean isSatisfying(Course course) {
    if (capacity < course.getMinimumCapacity() || capacity > course.getMaximumCapacity()) return false;
    for (String facility: course.getFacilities()) {
      if (!facilities.contains(facility)) return false;
    }
    return true;
  }
}
