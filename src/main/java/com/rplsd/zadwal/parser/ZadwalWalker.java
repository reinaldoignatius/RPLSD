package com.rplsd.zadwal.parser;

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
    private ArrayList<String> lecturers;
    private ArrayList<String> facilities;
    private ArrayList<String> teachingHours;

    @Override public void exitDefineClassroom(ZadwalParser.DefineClassroomContext ctx) {
        System.out.println("Enter Define Classroom:" + ctx.getText());
    }
    @Override public void enterDefineLecturer(ZadwalParser.DefineLecturerContext ctx) {
        System.out.println("define lecturer rule entered: " + ctx.getText());
    }
    @Override public void enterDefineClass(ZadwalParser.DefineClassContext ctx) {
        System.out.println("define class rule entered: " + ctx.getText());
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
}
