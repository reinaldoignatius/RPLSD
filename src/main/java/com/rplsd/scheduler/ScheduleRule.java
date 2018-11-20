package com.rplsd.scheduler;

import javafx.util.Pair;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ScheduleRule {
  private Map<String, Set<String>> nonConflictingClasses;
  private Map<String, Set<Pair<Integer, Integer>>> fixedClassSchedules;
  private Set<Pair<Integer, Integer>> restrictedHours;

  public ScheduleRule(Map<String, Set<String>> nonConflictingClasses,
                      Map<String, Set<Pair<Integer, Integer>>> fixedClassSchedules,
                      Set<Pair<Integer, Integer>> restrictedHours) {
    this.nonConflictingClasses = nonConflictingClasses;
    this.fixedClassSchedules = fixedClassSchedules;
    this.restrictedHours = restrictedHours;
  }

  public Map<String, Set<String>> getNonConflictingClasses() {
    return nonConflictingClasses;
  }

  public void setNonConflictingClasses(Map<String, Set<String>> nonConflictingClasses) {
    this.nonConflictingClasses = nonConflictingClasses;
  }

  public Map<String, Set<Pair<Integer, Integer>>> getFixedClassSchedules() {
    return fixedClassSchedules;
  }

  public void setFixedClassSchedules(Map<String, Set<Pair<Integer, Integer>>> fixedClassSchedules) {
    this.fixedClassSchedules = fixedClassSchedules;
  }

  public Set<Pair<Integer, Integer>> getRestrictedHours() {
    return restrictedHours;
  }

  public void setRestrictedHours(Set<Pair<Integer, Integer>> restrictedHours) {
    this.restrictedHours = restrictedHours;
  }

  public void addNonConflictingClassRule(String className, String otherClassName) {
    try {
      nonConflictingClasses.get(className).add(otherClassName);
    } catch (NullPointerException e) {
      nonConflictingClasses.put(className, new HashSet<String>(){{
        add(otherClassName);
      }});
    }
    try {
      nonConflictingClasses.get(otherClassName).add(className);
    } catch (NullPointerException e) {
      nonConflictingClasses.put(otherClassName, new HashSet<String>(){{
        add(className);
      }});
    }
  }

  public void addFixedClassSchedule(String className, int day, int time) {
    try {
      fixedClassSchedules.get(className).add(new Pair<>(day, time));
    } catch (NullPointerException e) {
      fixedClassSchedules.put(className, new HashSet<Pair<Integer, Integer>>(){{
        add(new Pair<>(day, time));
      }});
    }
  }

  public void addRestrictedHour(int day, int time) {
      restrictedHours.add(new Pair<>(day, time));
  }

}
