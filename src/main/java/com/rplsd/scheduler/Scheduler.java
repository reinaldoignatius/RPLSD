package com.rplsd.scheduler;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.rplsd.scheduler.Constants.DAYS_IN_A_WEEK;
import static com.rplsd.scheduler.Constants.HOURS_IN_A_DAY;

public class Scheduler {
  private List<ClassRoom> classRooms;
  private List<ClassRequirement> classRequirements;
  private List<Lecturer> lecturers;
  private ScheduleConstraint scheduleConstraint;
  private SchedulePreference schedulePreference;

  private ArrayList<ArrayList<List<Pair<ClassRoom, Lecturer>>>> schedules;
  private HashMap<String, ArrayList<ArrayList<Boolean>>> classRoomsAvailability;
  private HashMap<String, ArrayList<ArrayList<Boolean>>> lecturersAvailability;

  public Scheduler(ArrayList<ClassRoom> classRooms,
                   ArrayList<ClassRequirement> classRequirements,
                   ArrayList<Lecturer> lecturers,
                   ScheduleConstraint scheduleConstraint,
                   SchedulePreference schedulePreference) {
    this.classRooms = classRooms;
    this.classRequirements = classRequirements;
    this.lecturers = lecturers;
    this.scheduleConstraint = scheduleConstraint;
    this.schedulePreference = schedulePreference;
    schedules = new ArrayList<>(DAYS_IN_A_WEEK);
    for (ArrayList<List<Pair<ClassRoom, Lecturer>>> day: schedules) {
      day = new ArrayList<>(HOURS_IN_A_DAY);
      for (List<Pair<ClassRoom, Lecturer>> time: day) {
        time = new ArrayList<>();
      }
    }
    classRoomsAvailability = new HashMap<>();
    for (ClassRoom classRoom: classRooms) {
      ArrayList<ArrayList<Boolean>> classRoomAvailability = new ArrayList<>(DAYS_IN_A_WEEK);
      for (int day = 0; day < DAYS_IN_A_WEEK; day++) {
        classRoomAvailability.add(new ArrayList<>(HOURS_IN_A_DAY));
        for (int time = 0; time < HOURS_IN_A_DAY; time++) {
          classRoomAvailability.get(day).add(new Boolean(true));
        }
      }
      classRoomsAvailability.put(classRoom.getId(), classRoomAvailability);
    }
    lecturersAvailability = new HashMap<>();
    for (Lecturer lecturer: lecturers) {
      ArrayList<ArrayList<Boolean>> lecturerAvailability = new ArrayList<>();
      for (int day = 0; day < DAYS_IN_A_WEEK; day++) {
        lecturerAvailability.add(new ArrayList<>(HOURS_IN_A_DAY));
        for (int time = 0; time < HOURS_IN_A_DAY; time++) {
          lecturerAvailability.get(day).add(new Boolean(lecturer.getAvailability().get(day).get(time)));
        }
      }
      lecturersAvailability.put(lecturer.getName(), lecturerAvailability);
    }
  }

  public List<ClassRoom> getClassRooms() {
    return classRooms;
  }

  public HashMap<String, ArrayList<ArrayList<Boolean>>> getClassRoomsAvailability() {
    return classRoomsAvailability;
  }

  public void setClassRooms(List<ClassRoom> classRooms) {
    this.classRooms = classRooms;
  }

  public void addClassRoom(ClassRoom classRoom) {
    classRooms.add(classRoom);
    ArrayList<ArrayList<Boolean>> classRoomAvailability = new ArrayList<>(DAYS_IN_A_WEEK);
    for (int day = 0; day < DAYS_IN_A_WEEK; day++) {
      classRoomAvailability.add(new ArrayList<>(HOURS_IN_A_DAY));
      for (int time = 0; time < HOURS_IN_A_DAY; time++) {
        classRoomAvailability.get(day).add(new Boolean(true));
      }
    }
    classRoomsAvailability.put(classRoom.getId(), classRoomAvailability);
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

  private boolean assignSchedule(ClassRequirement classRequirement) {
    return true;
  }

  public void manageSchedule() {
    // Create schedule based on class rooms, class requirements, lecturers, constraint, and preference

  }
}
