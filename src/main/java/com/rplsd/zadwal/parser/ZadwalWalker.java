package com.rplsd.zadwal.parser;

import com.rplsd.zadwal.scheduler.*;

import java.util.ArrayList;

public class ZadwalWalker extends ZadwalBaseListener {
    private String roomId;
    private String classId;
    private int capacity;
    private String facility;
    private String lecturerName;
    private String teachingHour;
    private int attendeesCount;
    private int duration;
    private int maxCapacity;
    private ArrayList<String> lecturers = new ArrayList<>();
    private ArrayList<String> facilities = new ArrayList<>();
    private ArrayList<String> teachingHours = new ArrayList<>();

    @Override public void exitDefineClassroom(ZadwalParser.DefineClassroomContext ctx) {
        System.out.println(String.format("Define Classroom: %s %s %s", roomId, capacity, facilities));
        Classroom classroom = new Classroom(
                roomId, capacity, facilities
        );

        SchedulerContext.getInstance().getScheduler().addClassroom(classroom);
    }
    @Override public void exitDefineLecturer(ZadwalParser.DefineLecturerContext ctx) {
        System.out.println(String.format("Define Lecturer: %s %s", lecturerName, teachingHours));
        Lecturer lecturer = new Lecturer(
                lecturerName, SchedulerContext.availabilityParser(teachingHours)
        );
        SchedulerContext.getInstance().getScheduler().addLecturer(lecturer);
    }
    @Override public void enterDefineClass(ZadwalParser.DefineClassContext ctx) {
        maxCapacity = Constants.MAX_INT;
    }

    @Override public void exitDefineClass(ZadwalParser.DefineClassContext ctx) {
        System.out.println(String.format(
                "Define Class: %s %s %s %s %s %s",
                classId, attendeesCount, maxCapacity, facilities, duration, lecturers
        ));
        Course course = new Course(
                classId, attendeesCount, maxCapacity, facilities, duration, lecturers
        );
        SchedulerContext.getInstance().getScheduler().addClassRequirement(course);
    }
    @Override public void enterDefineConstraint(ZadwalParser.DefineConstraintContext ctx) {
        System.out.println("define constraint rule entered: " + ctx.getText());
    }
    @Override public void enterDefinePreference(ZadwalParser.DefinePreferenceContext ctx) {
        System.out.println("define preference rule entered: " + ctx.getText());
    }
    @Override public void enterStartSchedule(ZadwalParser.StartScheduleContext ctx) {
        System.out.println("define schedule rule entered: " + ctx.getText());
    }

    @Override public void exitRoom_id(ZadwalParser.Room_idContext ctx) {
        roomId = ctx.getText();
    }
    @Override public void exitCapacity(ZadwalParser.CapacityContext ctx)  {
        capacity = Integer.parseInt(ctx.getText());
    }
    @Override public void enterArray_of_facilities(ZadwalParser.Array_of_facilitiesContext ctx) {
        facilities = new ArrayList<>();
    }

    @Override public void exitFacility(ZadwalParser.FacilityContext ctx) {
        facilities.add(ctx.getText());
    }
    @Override public void enterArray_of_teaching_hours(ZadwalParser.Array_of_teaching_hoursContext ctx) {
        teachingHours = new ArrayList<>();
    }
    @Override public void enterTeaching_hour(ZadwalParser.Teaching_hourContext ctx) {
        teachingHours.add(ctx.getText());
    }

    @Override public void enterLecturer_name(ZadwalParser.Lecturer_nameContext ctx) {
        lecturerName = ctx.getText();
        lecturers.add(lecturerName);
    }
    @Override public void enterClass_id(ZadwalParser.Class_idContext ctx) {
        classId = ctx.getText();
    }
    @Override public void enterAttendees_count(ZadwalParser.Attendees_countContext ctx) {
        attendeesCount = Integer.parseInt(ctx.getText());
    }
    @Override public void enterMax_capacity(ZadwalParser.Max_capacityContext ctx) {
        maxCapacity = Integer.parseInt(ctx.getText());
    }
    @Override public void enterDuration(ZadwalParser.DurationContext ctx) {
        duration = Integer.parseInt(ctx.getText());
    }

    @Override public void enterArray_of_lecturers(ZadwalParser.Array_of_lecturersContext ctx) {
        lecturers = new ArrayList<>();
    }


}
