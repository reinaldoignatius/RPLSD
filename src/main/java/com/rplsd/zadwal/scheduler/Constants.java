package com.rplsd.zadwal.scheduler;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {
  public static final char HOUR_UNIT = 'h';
  public static final char MINUTE_UNIT = 'm';
  public static final int MAX_INT = 2147483647;

  public static String[] defaultDayNames = {"Monday", "Tuesday" , "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

  public static List<String> dayNames = Arrays.asList(defaultDayNames);

  public static int daysInAWeek = 5;
  public static int hoursInADay = 11;

  public static int classDuration = 1;
  public static char durationUnit = 'h';
  public static Time startTime = new Time(07,00);
  public static int terminalWidth = 140;

  public static void setDaysInAWeek(int day) {
    daysInAWeek = day;
  }
  public static void setHoursInADay(int hour) {
    hoursInADay = hour;
  }

  public static void setClassDuration(int duration, char unit) {
    classDuration = duration;
    durationUnit = unit;
  }
  public static void setDayNames(List<String> days) {
    dayNames = days;
  }

  public static void setStartTime(Time time) {
    startTime = time;
  }

  public static String toS() {
    return String.format("%d %d %d %c %s %s", daysInAWeek, hoursInADay, classDuration, durationUnit, dayNames,startTime);
  }
}