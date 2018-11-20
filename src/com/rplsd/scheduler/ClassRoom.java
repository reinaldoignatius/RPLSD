package com.rplsd.scheduler;

import java.util.List;

public class ClassRoom {
  private int capacity;
  private List<String> facilities;

  public ClassRoom(int capacity, List<String> facilities) {
    this.capacity = capacity;
    this.facilities = facilities;
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
}
