package com.rplsd.zadwal.scheduler;

import com.google.gson.Gson;
import com.sun.tools.internal.jxc.ap.Const;
import javafx.util.Pair;

import java.util.*;

import static com.rplsd.zadwal.scheduler.Constants.daysInAWeek;
import static com.rplsd.zadwal.scheduler.Constants.hoursInADay;

public class Scheduler {
  private List<Classroom> classrooms;
  private List<Course> courses;
  private List<Lecturer> lecturers;
  private ScheduleRule scheduleConstraint;
  private ScheduleRule schedulePreference;

  private ArrayList<ArrayList<List<ScheduleItem>>> schedules;
  private HashMap<String, ArrayList<ArrayList<Boolean>>> classroomsAvailability;
  private HashMap<String, ArrayList<ArrayList<Boolean>>> lecturersAvailability;
  private Set<String> addedCoursesName;

  public Scheduler(ArrayList<Classroom> classrooms,
                   ArrayList<Course> courses,
                   ArrayList<Lecturer> lecturers,
                   ScheduleRule scheduleConstraint,
                   ScheduleRule schedulePreference) {
    this.classrooms = classrooms;
    this.courses = courses;
    this.lecturers = lecturers;
    this.scheduleConstraint = scheduleConstraint;
    this.schedulePreference = schedulePreference;
    resetSchedules();
  }
  private void resetSchedules(){
    this.schedules = new ArrayList<>(daysInAWeek);
    for (int day = 0; day < daysInAWeek; day++) {
      schedules.add(new ArrayList<>(hoursInADay));
      for (int time = 0; time < hoursInADay; time++) {
        schedules.get(day).add(new ArrayList<>());
      }
    }
    classroomsAvailability = new HashMap<>();
    for (Classroom classroom : classrooms) {
      ArrayList<ArrayList<Boolean>> classroomAvailability = new ArrayList<>(daysInAWeek);
      for (int day = 0; day < daysInAWeek; day++) {
        classroomAvailability.add(new ArrayList<>(hoursInADay));
        for (int time = 0; time < hoursInADay; time++) {
          classroomAvailability.get(day).add(true);
        }
      }
      classroomsAvailability.put(classroom.getId(), classroomAvailability);
    }
    lecturersAvailability = new HashMap<>();
    for (Lecturer lecturer: lecturers) {
      ArrayList<ArrayList<Boolean>> lecturerAvailability = new ArrayList<>();
      for (int day = 0; day < daysInAWeek; day++) {
        lecturerAvailability.add(new ArrayList<>(hoursInADay));
        for (int time = 0; time < hoursInADay; time++) {
          lecturerAvailability.get(day).add(lecturer.getAvailability().get(day).get(time));
        }
      }
      lecturersAvailability.put(lecturer.getName(), lecturerAvailability);
    }
    addedCoursesName = new HashSet<>();
  }
  public List<Classroom> getClassrooms() {
    return classrooms;
  }

  public HashMap<String, ArrayList<ArrayList<Boolean>>> getClassroomsAvailability() {
    return classroomsAvailability;
  }

  public void setClassrooms(List<Classroom> classrooms) {
    this.classrooms = classrooms;
  }

  public void addClassroom(Classroom classroom) {
    classrooms.add(classroom);
    ArrayList<ArrayList<Boolean>> classroomAvailability = new ArrayList<>(daysInAWeek);
    for (int day = 0; day < daysInAWeek; day++) {
      classroomAvailability.add(new ArrayList<>(hoursInADay));
      for (int time = 0; time < hoursInADay; time++) {
        classroomAvailability.get(day).add(true);
      }
    }
    classroomsAvailability.put(classroom.getId(), classroomAvailability);
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
    for (int day = 0; day < daysInAWeek; day++) {
      lecturerAvailability.add(new ArrayList<>(hoursInADay));
      for (int time = 0; time < hoursInADay; time++) {
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
      if (course.getName().equals(courseName)) return course;
    }
    return null;
  }

  private List<Classroom> findSatisfyingClassrooms(Course course) {
    List<Classroom> satisfyingClassrooms = new ArrayList<>();
    for (Classroom classroom : classrooms) {
      if (classroom.isSatisfying(course)) {
        satisfyingClassrooms.add(classroom);
      }
    }
    return satisfyingClassrooms;
  }

  private boolean checkLecturersAvailability(List<String> requiredLecturersNames, int day, int time) {
    for (String requiredLecturerName: requiredLecturersNames) {
//      System.out.println(requiredLecturerName+ " " + day + " "+ time);
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

  private boolean checkParallelClasses(ScheduleRule rule, String courseName, int day, int time) {
    if (rule.getParallelClasses().containsKey(courseName)) {
      for (String parallelClassName : rule.getParallelClasses().get(courseName)) {
        if (addedCoursesName.contains(parallelClassName)) {
          boolean isParallel = false;
          for (ScheduleItem scheduleItem : schedules.get(day).get(time)) {
            if (scheduleItem.getCourseName().equals(parallelClassName)) {
              isParallel = true;
              break;
            }
          }
          if (!isParallel) {
            return false;
          }
        }
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

  private boolean checkConstraints(ScheduleRule rule, Course course, Classroom classroom, int day, int time) {
    if (!(classroomsAvailability.get(classroom.getId()).get(day).get(time))) return false;
    if (!checkLecturersAvailability(course.getLecturers(), day, time)) return false;
    if (!checkNonConflictingConstraint(rule, course.getName(), day, time)) return false;
    if (!checkParallelClasses(rule, course.getName(), day, time)) return false;
    if (rule.getRestrictedTime().contains(new Pair<>(day, time))) return false;
    if (!checkFixedSchedule(rule, course.getName(), day, time)) return false;
    if (!checkLectureMaxHourInADay(rule, course.getLecturers(), day)) return false;
    return true;
  };

  private boolean schedule(ScheduleRule rule, int currentClassRequirementIndex, int currentHour) {
    if (currentClassRequirementIndex >= courses.size()) return true;
    Course currentCourse = courses.get(currentClassRequirementIndex);

    List<Classroom> satisfyingClassrooms = findSatisfyingClassrooms(currentCourse);
    for (Classroom satisfyingClassroom : satisfyingClassrooms) {
      for (int day = 0; day < daysInAWeek; day++) {
        for (int time = 0; time < hoursInADay; time++) {
          if (checkConstraints(rule, currentCourse, satisfyingClassroom, day, time)) {
            ScheduleItem scheduleItem = new ScheduleItem(currentCourse.getName(), satisfyingClassroom.getId(), currentCourse.getLecturers());
            schedules.get(day).get(time).add(scheduleItem);
            classroomsAvailability.get(satisfyingClassroom.getId()).get(day).set(time, false);
            for (String lectureName : currentCourse.getLecturers()) {
              lecturersAvailability.get(lectureName).get(day).set(time, false);
            }
            addedCoursesName.add(currentCourse.getName());
            int nextHour = currentHour < currentCourse.getHours() - 1 ? currentHour + 1 : 0;
            int nextClassRequirementIndex = nextHour == 0 ? currentClassRequirementIndex + 1 : currentClassRequirementIndex;
            if (schedule(rule, nextClassRequirementIndex, nextHour)) return true;
            schedules.get(day).get(time).remove(scheduleItem);
            classroomsAvailability.get(satisfyingClassroom.getId()).get(day).set(time, true);
            for (String lectureName : currentCourse.getLecturers()) {
              lecturersAvailability.get(lectureName).get(day).set(time, true);
            }
            addedCoursesName.remove(currentCourse.getName());
          }
        }
      }
    }
    return false;
  }

  private void sortClassroomAscendingByCapacity() {
    class SortByCapacity implements Comparator<Classroom> {
      // Used for sorting in ascending order of capacity
      @Override
      public int compare(Classroom o1, Classroom o2) {
        return o1.getCapacity() - o2.getCapacity();
      }
    }
    classrooms.sort(new SortByCapacity());
  }

  public boolean schedule() {
    resetSchedules();
    sortClassroomAscendingByCapacity(); //To prioritize class room with smaller capacity in room selection
    if (schedule(scheduleConstraint.add(schedulePreference), 0, 0)) return true;
    return schedule(scheduleConstraint, 0, 0);
  }

  public void printSchedule() {
    for (int day = 0; day < daysInAWeek; day++) {
      for (int time = 0; time < hoursInADay; time++) {
        System.out.println(String.format("Day %s - Time %s: [", day, time));
        for (ScheduleItem scheduleItem: schedules.get(day).get(time)) {
          System.out.println(scheduleItem.toString());
        }
        System.out.println("]");
      }
    }
    new Printer().printSchedule(schedules);
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
//    return "";
  }
}
