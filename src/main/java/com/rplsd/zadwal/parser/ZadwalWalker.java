package com.rplsd.zadwal.parser;

import com.rplsd.zadwal.scheduler.Classroom;
import com.rplsd.zadwal.scheduler.Constants;
import com.rplsd.zadwal.scheduler.Course;
import com.rplsd.zadwal.scheduler.Lecturer;
import javafx.util.Pair;

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
    private ArrayList<Pair<String, String>> classes =new ArrayList<>();
    private String activeRuleType;
    private String classA;
    private String classB;


    @Override
    public void exitDefineClassroom(ZadwalParser.DefineClassroomContext ctx) {
        System.out.println(String.format("Define Classroom: %s %s %s", roomId, capacity, facilities));
        Classroom classroom = new Classroom(
                roomId, capacity, facilities
        );

        ZadwalContext.getInstance().getScheduler().addClassroom(classroom);
    }

    @Override
    public void exitDefineLecturer(ZadwalParser.DefineLecturerContext ctx) {
        System.out.println(String.format("Define Lecturer: %s %s", lecturerName, teachingHours));
        Lecturer lecturer = new Lecturer(
                lecturerName, ZadwalContext.availabilityParser(teachingHours)
        );
        ZadwalContext.getInstance().getScheduler().addLecturer(lecturer);
    }

    @Override
    public void enterDefineClass(ZadwalParser.DefineClassContext ctx) {
        maxCapacity = Constants.MAX_INT;
    }

    @Override
    public void exitDefineClass(ZadwalParser.DefineClassContext ctx) {
        System.out.println(String.format(
                "Define Class: %s %s %s %s %s %s",
                classId, attendeesCount, maxCapacity, facilities, duration, lecturers
        ));
        Course course = new Course(
                classId, attendeesCount, maxCapacity, facilities, duration, lecturers
        );
        ZadwalContext.getInstance().getScheduler().addClassRequirement(course);
    }

    @Override
    public void enterStartSchedule(ZadwalParser.StartScheduleContext ctx) {
        System.out.println("Schedule!");
        if (ZadwalContext.getInstance().getScheduler().schedule()) {
            ZadwalContext.getInstance().getScheduler().printSchedule();
            System.out.println(ZadwalContext.getInstance().getScheduler());

        } else {
            System.out.println("No schedule can satisfy all constraint");
        }
    }

    @Override
    public void exitRoom_id(ZadwalParser.Room_idContext ctx) {
        roomId = ctx.getText();
    }

    @Override
    public void exitCapacity(ZadwalParser.CapacityContext ctx) {
        capacity = Integer.parseInt(ctx.getText());
    }

    @Override
    public void enterArray_of_facilities(ZadwalParser.Array_of_facilitiesContext ctx) {
        facilities = new ArrayList<>();
    }

    @Override
    public void exitFacility(ZadwalParser.FacilityContext ctx) {
        facilities.add(ctx.getText());
    }

    @Override
    public void enterArray_of_teaching_hours(ZadwalParser.Array_of_teaching_hoursContext ctx) {
        teachingHours = new ArrayList<>();
    }

    @Override
    public void enterTeaching_hour(ZadwalParser.Teaching_hourContext ctx) {
        teachingHours.add(ctx.getText());
    }

    @Override
    public void enterLecturer_name(ZadwalParser.Lecturer_nameContext ctx) {
        lecturerName = ctx.getText();
        lecturers.add(lecturerName);
    }

    @Override
    public void enterClass_id(ZadwalParser.Class_idContext ctx) {
        classId = ctx.getText();
    }

    @Override
    public void enterAttendees_count(ZadwalParser.Attendees_countContext ctx) {
        attendeesCount = Integer.parseInt(ctx.getText());
    }

    @Override
    public void enterMax_capacity(ZadwalParser.Max_capacityContext ctx) {
        maxCapacity = Integer.parseInt(ctx.getText());
    }

    @Override
    public void enterDuration(ZadwalParser.DurationContext ctx) {
        duration = Integer.parseInt(ctx.getText());
    }

    @Override
    public void enterArray_of_lecturers(ZadwalParser.Array_of_lecturersContext ctx) {
        lecturers = new ArrayList<>();
    }

    @Override
    public void enterFixed_schedule(ZadwalParser.Fixed_scheduleContext ctx) {
        activeRuleType = ZadwalContext.FIXED_SCHEDULE;
    }

    @Override
    public void enterNon_conflict(ZadwalParser.Non_conflictContext ctx) {
        activeRuleType = ZadwalContext.NON_CONFLICT;
        classes = new ArrayList<>();
    }
    @Override public void enterTeaching_duration_limit(ZadwalParser.Teaching_duration_limitContext ctx) {
        activeRuleType = ZadwalContext.MAX_LECTURER_HOUR;
    }
    @Override
    public void enterClass_a(ZadwalParser.Class_aContext ctx) {
        classA = classId;
    }

    @Override
    public void enterClass_b(ZadwalParser.Class_bContext ctx) {
        classB = classId;
    }

    @Override
    public void exitPair_of_class_id(ZadwalParser.Pair_of_class_idContext ctx) {
        classes.add(new Pair(classA,classB));
    }
    @Override
    public void enterUnavailable(ZadwalParser.UnavailableContext ctx) {
        activeRuleType = ZadwalContext.RESTRICTED_TIME;
        teachingHours = new ArrayList<>();
    }
    @Override
    public void exitDefineConstraint(ZadwalParser.DefineConstraintContext ctx) {
        switch (activeRuleType) {
            case ZadwalContext.FIXED_SCHEDULE:
                System.out.println(String.format("Define Constraint Fixed Schedule for Class %s : %s", classId, teachingHours));
                ZadwalContext.getInstance().getScheduler().getScheduleConstraint().addFixedClassSchedule(
                        classId, ZadwalContext.timeParser(teachingHours)
                );
                break;
            case ZadwalContext.NON_CONFLICT:
                System.out.println("Define Constraint Non-Conflicting classes "+ classes);
                for(Pair<String,String> i : classes) {
                    ZadwalContext.getInstance().getScheduler().getScheduleConstraint().addNonConflictingClassRule(
                        i.getKey(), i.getValue()
                    );
                }
                break;
            case ZadwalContext.MAX_LECTURER_HOUR:
                System.out.println("Define Constraint Teaching duration limit per day "+ duration+ "hour");
                ZadwalContext.getInstance().getScheduler().getScheduleConstraint().setMaxLecturerHourInADay(
                        duration
                );
                break;
            case ZadwalContext.RESTRICTED_TIME:
                System.out.println("Define Constraint Unavailable at "+ teachingHours);
                for(String i : teachingHours) {
                    Pair<Integer,Integer> pairTimeDay = ZadwalContext.timeConverter(i);
                    ZadwalContext.getInstance().getScheduler().getScheduleConstraint().addRestrictedTime(
                            pairTimeDay.getKey(), pairTimeDay.getValue()
                    );
                }

        }
    }

    public void exitDefinePreference(ZadwalParser.DefinePreferenceContext ctx) {
        switch (activeRuleType) {
            case ZadwalContext.FIXED_SCHEDULE:
                System.out.println(String.format("Define Preference Fixed Schedule for Class %s : %s", classId, teachingHours));
                ZadwalContext.getInstance().getScheduler().getSchedulePreference().addFixedClassSchedule(
                        classId, ZadwalContext.timeParser(teachingHours)
                );
                break;
            case ZadwalContext.NON_CONFLICT:
                System.out.println("Define Preference Non-Conflicting classes "+ classes);
                for(Pair<String,String> i : classes) {
                    ZadwalContext.getInstance().getScheduler().getSchedulePreference().addNonConflictingClassRule(
                            i.getKey(), i.getValue()
                    );
                }
                break;
            case ZadwalContext.MAX_LECTURER_HOUR:
                System.out.println("Define Preference Teaching duration limit per day "+ duration+ "hour");
                ZadwalContext.getInstance().getScheduler().getSchedulePreference().setMaxLecturerHourInADay(
                        duration
                );
                break;
            case ZadwalContext.RESTRICTED_TIME:
                System.out.println("Define Preference Unavailable at "+ teachingHours);
                for(String i : teachingHours) {
                    Pair<Integer,Integer> pairTimeDay = ZadwalContext.timeConverter(i);
                    ZadwalContext.getInstance().getScheduler().getSchedulePreference().addRestrictedTime(
                            pairTimeDay.getKey(), pairTimeDay.getValue()
                    );
                }

        }
    }
}
