package com.rplsd.scheduler;

import java.util.List;

public class Scheduler {
  private List<ClassRoom> classrooms;
  private List<ClassRequirement> classRequirements;
  private List<Lecturer> lecturers;
  private ScheduleConstraint scheduleConstraint;
  private SchedulePreference schedulePreference;

  public Scheduler(List<ClassRoom> classrooms, List<ClassRequirement> classRequirements, List<Lecturer> lecturers, ScheduleConstraint scheduleConstraint, SchedulePreference schedulePreference) {
    this.classrooms = classrooms;
    this.classRequirements = classRequirements;
    this.lecturers = lecturers;
    this.scheduleConstraint = scheduleConstraint;
    this.schedulePreference = schedulePreference;
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
}
