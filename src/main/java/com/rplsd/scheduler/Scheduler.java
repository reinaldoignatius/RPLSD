package com.rplsd.scheduler;

import javafx.util.Pair;

import java.util.*;

import static com.rplsd.scheduler.Constants.DAYS_IN_A_WEEK;
import static com.rplsd.scheduler.Constants.HOURS_IN_A_DAY;

public class Scheduler {
  private List<ClassRoom> classRooms;
  private List<Course> courses;
  private List<Lecturer> lecturers;
  private ScheduleRule scheduleConstraint;
  private ScheduleRule schedulePreference;

  private ArrayList<ArrayList<List<ScheduleItem>>> schedules;
  private HashMap<String, ArrayList<ArrayList<Boolean>>> classRoomsAvailability;
  private HashMap<String, ArrayList<ArrayList<Boolean>>> lecturersAvailability;

  public Scheduler(ArrayList<ClassRoom> classRooms,
                   ArrayList<Course> courses,
                   ArrayList<Lecturer> lecturers,
                   ScheduleRule scheduleConstraint,
                   ScheduleRule schedulePreference) {
    this.classRooms = classRooms;
    this.courses = courses;
    this.lecturers = lecturers;
    this.scheduleConstraint = scheduleConstraint;
    this.schedulePreference = schedulePreference;
    schedules = new ArrayList<>(DAYS_IN_A_WEEK);
    for (int day = 0; day < DAYS_IN_A_WEEK; day++) {
      schedules.add(new ArrayList<>(HOURS_IN_A_DAY));
      for (int time = 0; time < HOURS_IN_A_DAY; time++) {
        schedules.get(day).add(new ArrayList<>());
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

  public List<Course> getCourses() {
    return courses;
  }

  public void setCourses(List<Course> courses) {
    this.courses = courses;
  }

  public void addClassRequirement(Course course) {
    courses.add(course);
  }

  public List<Lecturer> getLecturers() {
    return lecturers;
  }

  public void setLecturers(List<Lecturer> lecturers) {
    this.lecturers = lecturers;
  }

  public void addLecturer(Lecturer lecturer) {
    this.lecturers.add(lecturer);
    ArrayList<ArrayList<Boolean>> lecturerAvailability = new ArrayList<>();
    for (int day = 0; day < DAYS_IN_A_WEEK; day++) {
      lecturerAvailability.add(new ArrayList<>(HOURS_IN_A_DAY));
      for (int time = 0; time < HOURS_IN_A_DAY; time++) {
        lecturerAvailability.get(day).add(new Boolean(lecturer.getAvailability().get(day).get(time)));
      }
    }
    lecturersAvailability.put(lecturer.getName(), lecturerAvailability);
  }

  public ScheduleRule getScheduleConstraint() {
    return scheduleConstraint;
  }

  public void setScheduleConstraint(ScheduleRule scheduleConstraint) {
    this.scheduleConstraint = scheduleConstraint;
  }

  public ScheduleRule getSchedulePreference() {
    return schedulePreference;
  }

  public void setSchedulePreference(ScheduleRule schedulePreference) {
    this.schedulePreference = schedulePreference;
  }

  public ArrayList<ArrayList<List<ScheduleItem>>> getSchedules() {
    return schedules;
  }

  public Course findCourse(String courseName) {
    for (Course course: courses) {
      if (course.getCourseName().equals(courseName)) return course;
    }
    return null;
  }

  private List<ClassRoom> findSatisfyingClassRooms(Course course) {
    List<ClassRoom> satisfyingClassRooms = new ArrayList<>();
    for (ClassRoom classRoom: classRooms) {
      if (classRoom.isSatisfying(course)) {
        satisfyingClassRooms.add(classRoom);
      }
    }
    return satisfyingClassRooms;
  }

  private boolean checkLecturersAvailability(List<String> requiredLecturersNames, int day, int time) {
    for (String requiredLecturerName: requiredLecturersNames) {
      if (!lecturersAvailability.get(requiredLecturerName).get(day).get(time)) return false;
    }
    return true;
  }

  private boolean checkNonConflictingConstraint(ScheduleRule rule, String courseName, int day, int time) {
    for (ScheduleItem scheduleItem: schedules.get(day).get(time)) {
      if (rule.getNonConflictingClasses().containsKey(courseName)) {
        if (rule.getNonConflictingClasses().get(courseName).contains(scheduleItem.getCourseName())) return false;
      }
    }
    return true;
  }

  private boolean schedule(ScheduleRule rule, int currentClassRequirementIndex, int currentHour) {
    if (currentClassRequirementIndex >= courses.size()) return true;
    Course currentCourse = courses.get(currentClassRequirementIndex);
    Set<Pair<Integer, Integer>> fixedClassSchedule = new HashSet<>();
    boolean fixedSchedule = false;
    if (rule.getFixedClassSchedules().containsKey(currentCourse.getCourseName())) {
      fixedClassSchedule = rule.getFixedClassSchedules().get(currentCourse.getCourseName());
      fixedSchedule = true;
    }
    List<ClassRoom> satisfyingClassrooms = findSatisfyingClassRooms(currentCourse);
//    List<Lecturer> satisfyingLecturers = findSatisyingLecturers(courses.get(currentClassRequirementIndex));
    for (ClassRoom satisfyingClassroom : satisfyingClassrooms) {
//      for (Lecturer satisfyingLecturer: satisfyingLecturers) {
      for (int day = 0; day < DAYS_IN_A_WEEK; day++) {
        for (int time = 0; time < HOURS_IN_A_DAY; time++) {
          if (!fixedSchedule || fixedClassSchedule.contains(new Pair<>(day, time))) {
            if (!rule.getRestrictedTime().contains(new Pair<>(day, time))) {
              List<String> requiredLecturersNames = currentCourse.getLecturers();
              if (classRoomsAvailability.get(satisfyingClassroom.getId()).get(day).get(time) &&
                      checkLecturersAvailability(requiredLecturersNames, day, time) &&
                      checkNonConflictingConstraint(rule, currentCourse.getCourseName(), day, time)
              ) {
                ScheduleItem scheduleItem = new ScheduleItem(currentCourse.getCourseName(), satisfyingClassroom.getId(), requiredLecturersNames);
                schedules.get(day).get(time).add(scheduleItem);
                classRoomsAvailability.get(satisfyingClassroom.getId()).get(day).set(time, false);
                for (String lectureName : requiredLecturersNames) {
                  lecturersAvailability.get(lectureName).get(day).set(time, false);
                }
                int nextHour = currentHour < currentCourse.getHours() - 1 ? currentHour + 1 : 0;
                int nextClassRequirementIndex = nextHour == 0 ? currentClassRequirementIndex + 1 : currentClassRequirementIndex;
                if (schedule(rule, nextClassRequirementIndex, nextHour)) return true;
                schedules.get(day).get(time).remove(scheduleItem);
                classRoomsAvailability.get(satisfyingClassroom.getId()).get(day).set(time, true);
                for (String lectureName : requiredLecturersNames) {
                  lecturersAvailability.get(lectureName).get(day).set(time, true);
                }
              }
            }
          }
        }
      }
//      }
    }
    return false;
  }

  public boolean schedule() {
    // TODO: sort classroom by capacity
    ScheduleRule fullScheduleRule = scheduleConstraint.add(schedulePreference);
    if (schedule(fullScheduleRule, 0, 0)) return true;
    return schedule(scheduleConstraint, 0, 0);
  }

  public void printSchedule() {
    for (int day = 0; day < DAYS_IN_A_WEEK; day++) {
      for (int time = 0; time < HOURS_IN_A_DAY; time++) {
        System.out.println(String.format("Day %s - Time %s: [", day, time));
        for (ScheduleItem scheduleItem: schedules.get(day).get(time)) {
          System.out.println(scheduleItem.toString());
        }
        System.out.println("]");
      }
    }
  }
}
