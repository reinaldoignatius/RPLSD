package com.rplsd.scheduler;

import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        ArrayList<ClassRoom> classRooms = new ArrayList<>();
        ArrayList<ClassRequirement> classRequirements = new ArrayList<>();
        ArrayList<Lecturer> lecturers = new ArrayList<>();
        ScheduleConstraint scheduleConstraint = new ScheduleConstraint(3);
        SchedulePreference schedulePreference = new SchedulePreference();
        Scheduler scheduler = new Scheduler(
                classRooms, classRequirements, lecturers, scheduleConstraint, schedulePreference);

        scheduler.addClassRoom(new ClassRoom("7602", 100, new ArrayList<String>(){{
            add("ac");
            add("ac2");
        }}));

        ArrayList<ArrayList<Boolean>> classRoomAvailability = scheduler.getClassRoomsAvailability().get("7602");
        for (ArrayList<Boolean> day: classRoomAvailability) {
            for (Boolean time: day) {
                System.out.println(time);
            }
        }
    }
}