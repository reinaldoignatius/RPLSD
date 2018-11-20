package com.rplsd.scheduler;

import javafx.util.Pair;

import java.util.*;

import static com.rplsd.scheduler.Constants.DAYS_IN_A_WEEK;
import static com.rplsd.scheduler.Constants.HOURS_IN_A_DAY;

public class App {
    public static void main(String[] args) {
        ArrayList<ClassRoom> classRooms = new ArrayList<>();
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Lecturer> lecturers = new ArrayList<>();

        Map<String, Set<String>> nonConflictingClasses = new HashMap<>();
        Map<String, Set<Pair<Integer, Integer>>> fixedClassSchedules = new HashMap<>();
        Set<Pair<Integer, Integer>> restrictedHours = new HashSet<>();

        ScheduleRule scheduleConstraint = new ScheduleRule(nonConflictingClasses, fixedClassSchedules, restrictedHours);
        ScheduleRule schedulePreference = new ScheduleRule(nonConflictingClasses, fixedClassSchedules, restrictedHours);
        Scheduler scheduler = new Scheduler(
                classRooms, courses, lecturers, scheduleConstraint, schedulePreference);

        scheduler.getScheduleConstraint().addNonConflictingClassRule("RPLSD", "NLP");
        scheduler.getScheduleConstraint().addRestrictedTime(0, 1);

        ArrayList<ArrayList<Boolean>> availability = new ArrayList<>(DAYS_IN_A_WEEK);
        for (int day = 0; day < DAYS_IN_A_WEEK; day++) {
            availability.add(new ArrayList<>(HOURS_IN_A_DAY));
            for (int time = 0; time < HOURS_IN_A_DAY; time++) {
                availability.get(day).add(new Boolean(false));
            }
        }

        availability.get(0).set(1, true);
        availability.get(0).set(2, true);
        availability.get(0).set(3, true);
        availability.get(0).set(4, true);

        scheduler.addLecturer(new Lecturer("bayu", availability));

        availability.get(0).set(3, false);
        availability.get(0).set(4, false);

        scheduler.addLecturer(new Lecturer("ayu", availability));

        scheduler.addClassRoom(new ClassRoom("7602", 100, new ArrayList<String>(){{
            add("ac");
            add("ac2");
        }}));

        Course course = new Course("RPLSD", 50, 100, new ArrayList<String>(){{
            add("ac");
            add("ac2");
        }}, 2, new ArrayList<String>(){{
            add("bayu");
        }});

        Course course2 = new Course("NLP", 50, 100, new ArrayList<String>(){{
            add("ac");
            add("ac2");
        }}, 2, new ArrayList<String>(){{
            add("ayu");
        }});

        scheduler.addClassRequirement(course);
        scheduler.addClassRequirement(course2);

        boolean possible = scheduler.schedule(scheduleConstraint, 0, 0);
        if (possible) scheduler.printSchedule();

//        System.out.println(scheduler.findSatisfyingClassRooms(course).toString());
//        System.out.println(scheduler.findSatisyingLecturers(course).toString());



//        ArrayList<ArrayList<Boolean>> classRoomAvailability = scheduler.getClassRoomsAvailability().get("7602");
//        for (ArrayList<Boolean> day: classRoomAvailability) {
//            for (Boolean time: day) {
//                System.out.println(time);
//            }
//        }
    }
}