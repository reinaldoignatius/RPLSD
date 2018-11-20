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

  private boolean checkFixedSchedule(ScheduleRule rule, String courseName, int day, int time) {
    Set<Pair<Integer, Integer>> fixedClassSchedule = new HashSet<>();
    boolean isScheduleFixed = rule.getFixedClassSchedules().containsKey(courseName);
    if (isScheduleFixed) {
      fixedClassSchedule = rule.getFixedClassSchedules().get(courseName);
    }
    return !isScheduleFixed || fixedClassSchedule.contains(new Pair<>(day, time));
  }

  private boolean checkLectureMaxHourInADay(ScheduleRule rule, List<String>lecturersName, int day) {
    int count = 0;
    for (List<ScheduleItem> time: schedules.get(day)){
      for (ScheduleItem item: time) {
        for (String lecturerName: lecturersName) {
          if (item.getLecturerNames().contains(lecturerName)) {
            ++count;
            if (count >= rule.getMaxLecturerHourInADay()) return false;
          }
        }
      }
    }
    return true;
  }

  private boolean checkConstraints(ScheduleRule rule, Course course, ClassRoom classRoom, int day, int time) {
    if (!(classRoomsAvailability.get(classRoom.getId()).get(day).get(time))) return false;
    if (!checkLecturersAvailability(course.getLecturers(), day, time)) return false;
    if (!checkNonConflictingConstraint(rule, course.getCourseName(), day, time)) return false;
    if (rule.getRestrictedTime().contains(new Pair<>(day, time))) return false;
    if (!checkFixedSchedule(rule, course.getCourseName(), day, time)) return false;
    if (!checkLectureMaxHourInADay(rule, course.getLecturers(), day)) return false;
    return true;
  };

  private boolean schedule(ScheduleRule rule, int currentClassRequirementIndex, int currentHour) {
    if (currentClassRequirementIndex >= courses.size()) return true;
    Course currentCourse = courses.get(currentClassRequirementIndex);

    List<ClassRoom> satisfyingClassrooms = findSatisfyingClassRooms(currentCourse);
    for (ClassRoom satisfyingClassroom : satisfyingClassrooms) {
      for (int day = 0; day < DAYS_IN_A_WEEK; day++) {
        for (int time = 0; time < HOURS_IN_A_DAY; time++) {
          if (checkConstraints(rule, currentCourse, satisfyingClassroom, day, time)) {
            ScheduleItem scheduleItem = new ScheduleItem(currentCourse.getCourseName(), satisfyingClassroom.getId(), currentCourse.getLecturers());
            schedules.get(day).get(time).add(scheduleItem);
            classRoomsAvailability.get(satisfyingClassroom.getId()).get(day).set(time, false);
            for (String lectureName : currentCourse.getLecturers()) {
              lecturersAvailability.get(lectureName).get(day).set(time, false);
            }
            int nextHour = currentHour < currentCourse.getHours() - 1 ? currentHour + 1 : 0;
            int nextClassRequirementIndex = nextHour == 0 ? currentClassRequirementIndex + 1 : currentClassRequirementIndex;
            if (schedule(rule, nextClassRequirementIndex, nextHour)) return true;
            schedules.get(day).get(time).remove(scheduleItem);
            classRoomsAvailability.get(satisfyingClassroom.getId()).get(day).set(time, true);
            for (String lectureName : currentCourse.getLecturers()) {
              lecturersAvailability.get(lectureName).get(day).set(time, true);
            }
          }
        }
      }
    }
    return false;
  }

  public boolean schedule() {
    // TODO: sort classroom by capacity
    if (schedule(scheduleConstraint.add(schedulePreference), 0, 0)) return true;
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
