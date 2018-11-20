package com.rplsd.scheduler;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
  private List<ClassRoom> classrooms;
  private List<ClassRequirement> classRequirements;
  private List<Lecturer> lecturers;
  private ScheduleConstraint scheduleConstraint;
  private SchedulePreference schedulePreference;
  private ArrayList<ArrayList<List<Pair<ClassRoom, Lecturer>>>> schedules;

  public Scheduler() {
    schedules = new ArrayList<>(5);
    for (ArrayList<List<Pair<ClassRoom, Lecturer>>> day: schedules) {
      day = new ArrayList<>(11);
      for (List<Pair<ClassRoom, Lecturer>> time: day) {
        time = new ArrayList<>();
      }
    }
  }

  public List<ClassRoom> getClassrooms() {
    return classrooms;
  }

  public void setClassrooms(List<ClassRoom> classrooms) {
    this.classrooms = classrooms;
  }

  public List<ClassRequirement> getClassRequirements() {
    return classRequirements;
  }

  public void setClassRequirements(List<ClassRequirement> classRequirements) {
    this.classRequirements = classRequirements;
  }

  public List<Lecturer> getLecturers() {
    return lecturers;
  }

  public void setLecturers(List<Lecturer> lecturers) {
    this.lecturers = lecturers;
  }

  public ScheduleConstraint getScheduleConstraint() {
    return scheduleConstraint;
  }

  public void setScheduleConstraint(ScheduleConstraint scheduleConstraint) {
    this.scheduleConstraint = scheduleConstraint;
  }

  public SchedulePreference getSchedulePreference() {
    return schedulePreference;
  }

  public void setSchedulePreference(SchedulePreference schedulePreference) {
    this.schedulePreference = schedulePreference;
  }

  public ArrayList<ArrayList<List<Pair<ClassRoom, Lecturer>>>> getSchedules() {
    return schedules;
  }
}
