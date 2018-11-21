package com.rplsd.zadwal.parser;

import com.rplsd.zadwal.scheduler.Classroom;
import com.rplsd.zadwal.scheduler.SchedulerContext;

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
        System.out.println(String.format("Define Classroom: %s %s %s", roomId, capacity, facilities));
        Classroom classroom = new Classroom(
                roomId, capacity, facilities
        );

        SchedulerContext.getInstance().getScheduler().addClassroom(classroom);
    }
    @Override public void exitDefineLecturer(ZadwalParser.DefineLecturerContext ctx) {
        System.out.println(String.format("Define Lecturer: %s %s", lecturerName, teachingHours));
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


}
