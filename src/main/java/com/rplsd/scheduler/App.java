package com.rplsd.scheduler;

import javafx.util.Pair;

import java.util.*;

import static com.rplsd.scheduler.Constants.DAYS_IN_A_WEEK;
import static com.rplsd.scheduler.Constants.HOURS_IN_A_DAY;

public class App {
    public static void main(String[] args) {
        ArrayList<Classroom> classrooms = new ArrayList<>();
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Lecturer> lecturers = new ArrayList<>();

        Map<String, Set<String>> nonConflictingClassesConstraints = new HashMap<>();
        Map<String, Set<Pair<Integer, Integer>>> fixedClassSchedulesConstraints = new HashMap<>();
        Set<Pair<Integer, Integer>> restrictedTimeConstraints = new HashSet<>();
        Map<String, Set<String>> nonConflictingClassesPreferences = new HashMap<>();
        Map<String, Set<Pair<Integer, Integer>>> fixedClassSchedulesPreferences = new HashMap<>();
        Set<Pair<Integer, Integer>> restrictedTimePreferences = new HashSet<>();

        ScheduleRule scheduleConstraint = new ScheduleRule(nonConflictingClassesConstraints, fixedClassSchedulesConstraints, restrictedTimeConstraints, 2);
        ScheduleRule schedulePreference = new ScheduleRule(nonConflictingClassesPreferences, fixedClassSchedulesPreferences, restrictedTimePreferences, 1);
        Scheduler scheduler = new Scheduler(
                classrooms, courses, lecturers, scheduleConstraint, schedulePreference);

        scheduler.getSchedulePreference().addNonConflictingClassRule("RPLSD", "NLP");
        scheduler.getScheduleConstraint().addFixedClassSchedule("RPLSD", new HashSet<Pair<Integer, Integer>>(){{
            add(new Pair<>(0, 1));
            add(new Pair<>(0, 2));
        }});
        scheduler.getScheduleConstraint().addRestrictedTime(0, 0);

        ArrayList<ArrayList<Boolean>> availability = new ArrayList<>(DAYS_IN_A_WEEK);
        for (int day = 0; day < DAYS_IN_A_WEEK; day++) {
            availability.add(new ArrayList<>(HOURS_IN_A_DAY));
            for (int time = 0; time < HOURS_IN_A_DAY; time++) {
                availability.get(day).add(new Boolean(false));
            }
        }

        availability.get(0).set(1, true);
        availability.get(0).set(2, true);

        scheduler.addLecturer(new Lecturer("bayu", availability));

        scheduler.addLecturer(new Lecturer("ayu", availability));

        scheduler.addClassroom(new Classroom("7602", 110, new ArrayList<String>(){{
            add("ac");
            add("ac2");
        }}));

        scheduler.addClassroom(new Classroom("7606", 100, new ArrayList<String>(){{
            add("ac");
            add("ac2");
        }}));

        Course course = new Course("RPLSD", 50, Integer.MAX_VALUE, new ArrayList<String>(){{
            add("ac");
            add("ac2");
        }}, 2, new ArrayList<String>(){{
            add("bayu");
        }});

        Course course2 = new Course("NLP", 50, Integer.MAX_VALUE, new ArrayList<String>(){{
            add("ac");
            add("ac2");
        }}, 2, new ArrayList<String>(){{
            add("ayu");
        }});

        scheduler.addClassRequirement(course);
        scheduler.addClassRequirement(course2);

        boolean possible = scheduler.schedule();
        if (possible) scheduler.printSchedule();

//        System.out.println(scheduler.findSatisfyingClassRooms(course).toString());
//        System.out.println(scheduler.findSatisyingLecturers(course).toString());



//        ArrayList<ArrayList<Boolean>> classRoomAvailability = scheduler.getClassroomsAvailability().get("7602");
//        for (ArrayList<Boolean> day: classRoomAvailability) {
//            for (Boolean time: day) {
//                System.out.println(time);
//            }
//        }
    }
}