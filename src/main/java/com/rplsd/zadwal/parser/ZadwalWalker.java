package com.rplsd.zadwal.parser;

import com.rplsd.zadwal.Zadwal;
import com.rplsd.zadwal.scheduler.*;
import com.sun.tools.internal.jxc.ap.Const;
import javafx.util.Pair;
import sun.jvm.hotspot.utilities.ConstantTag;

import java.util.ArrayList;

public class ZadwalWalker extends ZadwalBaseListener {
    private static final char PRINT_ALL = 'a';
    private static final char PRINT_LECTURER = 'l';
    private static final char PRINT_CLASS = 'c';
    private String roomId;
    private String classId;
    private int capacity;
    private String lecturerName;
    private int attendeesCount;
    private int duration;
    private int maxCapacity;
    private int dayInWeek;
    private ArrayList<String> lecturers = new ArrayList<>();
    private ArrayList<String> facilities = new ArrayList<>();
    private ArrayList<String> teachingHours = new ArrayList<>();
    private ArrayList<Pair<String, String>> classes =new ArrayList<>();
    private ArrayList<String> days = new ArrayList<>();
    private String activeRuleType;
    private String classA;
    private String classB;
    private ArrayList<String> classFilter = new ArrayList<>();
    private char printType = 'a';

    private char durationUnit = 'h';


    @Override
    public void exitDefineClassroom(ZadwalParser.DefineClassroomContext ctx) {
        System.out.println(String.format("Define Classroom: %s %s %s", roomId, capacity, facilities));
        Classroom classroom = new Classroom(
                roomId, capacity, facilities
        );
        facilities = new ArrayList<>();
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
        facilities = new ArrayList<>();
        lecturers = new ArrayList<>();
        ZadwalContext.getInstance().getScheduler().addClassRequirement(course);
    }

    @Override
    public void enterStartSchedule(ZadwalParser.StartScheduleContext ctx) {
        if (ZadwalContext.getInstance().getScheduler().schedule()) {
            System.out.println("Schedule Created");
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
    public void exitLecturer_name(ZadwalParser.Lecturer_nameContext ctx) {
        lecturerName = ctx.getText();
        lecturers.add(lecturerName);
    }

    @Override
    public void exitClass_id(ZadwalParser.Class_idContext ctx) {
        classId = ctx.getText();
        classFilter.add(classId);
    }

    @Override
    public void exitAttendees_count(ZadwalParser.Attendees_countContext ctx) {
        attendeesCount = Integer.parseInt(ctx.getText());
    }

    @Override
    public void exitMax_capacity(ZadwalParser.Max_capacityContext ctx) {
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
    public void exitClass_a(ZadwalParser.Class_aContext ctx) {
        classA = classId;
    }

    @Override
    public void exitClass_b(ZadwalParser.Class_bContext ctx) {
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
    public void enterParallel(ZadwalParser.ParallelContext ctx) {
        activeRuleType = ZadwalContext.PARALLEL;
        classes = new ArrayList<>();
    }

    @Override
    public void exitDefineConstraint(ZadwalParser.DefineConstraintContext ctx) {
        switch (activeRuleType) {
            case ZadwalContext.FIXED_SCHEDULE:
                System.out.println(String.format("Define Constraint Fixed Schedule for Class %s : %s", classId, teachingHours));
                ZadwalContext.getInstance().getScheduler().getScheduleConstraint().addFixedClassSchedule(
                        classId, ZadwalContext.timeParser(teachingHours)
                );
                teachingHours = new ArrayList<>();
                break;
            case ZadwalContext.NON_CONFLICT:
                System.out.println("Define Constraint Non-Conflicting classes "+ classes);
                for(Pair<String,String> i : classes) {
                    ZadwalContext.getInstance().getScheduler().getScheduleConstraint().addNonConflictingClassRule(
                        i.getKey(), i.getValue()
                    );
                }
                classes = new ArrayList<>();
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
                teachingHours = new ArrayList<>();
                break;
            case ZadwalContext.PARALLEL:
                System.out.println("Define Constraint Parallel at "+ classes);

                for(Pair<String,String> i : classes) {
                    ZadwalContext.getInstance().getScheduler().getScheduleConstraint().addParallelClasses(
                            i.getKey(), i.getValue()
                    );
                }
                classes = new ArrayList<>();
                break;



        }
    }

    public void exitDefinePreference(ZadwalParser.DefinePreferenceContext ctx) {
        switch (activeRuleType) {
            case ZadwalContext.FIXED_SCHEDULE:
                System.out.println(String.format("Define Preference Fixed Schedule for Class %s : %s", classId, teachingHours));
                ZadwalContext.getInstance().getScheduler().getSchedulePreference().addFixedClassSchedule(
                        classId, ZadwalContext.timeParser(teachingHours)
                );
                teachingHours = new ArrayList<>();
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
                teachingHours = new ArrayList<>();
                break;
            case ZadwalContext.PARALLEL:
                System.out.println("Define Preference Parallel at "+ classes);
                for(Pair<String,String> i : classes) {
                    ZadwalContext.getInstance().getScheduler().getSchedulePreference().addParallelClasses(
                            i.getKey(), i.getValue()
                    );
                }
                classes = new ArrayList<>();
                break;
        }
    }
    @Override public void exitDay_in_week(ZadwalParser.Day_in_weekContext ctx) {
        dayInWeek = Integer.parseInt(ctx.getText());
        Constants.setDaysInAWeek(dayInWeek);
    }

    @Override public void enterArray_of_days(ZadwalParser.Array_of_daysContext ctx) {
        days = new ArrayList<>();
    }

    @Override public void exitDay_name(ZadwalParser.Day_nameContext ctx) {
        days.add(ctx.getText());
    }
    @Override public void exitArray_of_days(ZadwalParser.Array_of_daysContext ctx) {
        System.out.println("Defined day names: "+days);
        Constants.setDayNames(days);
        days = new ArrayList<>();
    }

    @Override public void exitWork_hour_duration(ZadwalParser.Work_hour_durationContext ctx) {
        int workHour = Integer.parseInt(ctx.getText());
        Constants.setHoursInADay(workHour);
    }

    @Override public void exitClass_duration(ZadwalParser.Class_durationContext ctx) {
        Constants.setClassDuration(duration, durationUnit);
    }
    @Override public void exitHour_unit(ZadwalParser.Hour_unitContext ctx) {
        durationUnit = Constants.HOUR_UNIT;
    }
    @Override public void exitMinute_unit(ZadwalParser.Minute_unitContext ctx) {
        durationUnit = Constants.MINUTE_UNIT;
    }
    @Override public void exitTime(ZadwalParser.TimeContext ctx) {
        String[] times = ctx.getText().split(":");
        if(times.length == 2) {
            Constants.setStartTime(new Time(Integer.parseInt(times[0]), Integer.parseInt(times[1])));
        } else {
            Constants.setStartTime(new Time(Integer.parseInt(times[0]), 0));
        }
    }

    @Override public void enterAll(ZadwalParser.AllContext ctx) {
        printType = PRINT_ALL;
    }
    @Override public void enterLecturer(ZadwalParser.LecturerContext cxt) {
        printType = PRINT_LECTURER;
    }
    @Override public void enterSpecific_class(ZadwalParser.Specific_classContext ctx) {
        printType = PRINT_CLASS;
    }

    @Override public void enterArray_of_class(ZadwalParser.Array_of_classContext ctx) {
        classFilter = new ArrayList<>();
    }

    @Override public void exitPrintSchedule(ZadwalParser.PrintScheduleContext ctx) {
        switch (printType){
            case PRINT_ALL:
                System.out.println("All Schedule");
                new Printer().printSchedule(ZadwalContext.getInstance().getScheduler().getSchedules());
                break;
            case PRINT_CLASS:
                System.out.println("Schedule for "+classFilter);
                new Printer().printForClass(ZadwalContext.getInstance().getScheduler().getSchedules(), classFilter);
                break;
            case PRINT_LECTURER:
                System.out.println("Schedule for "+lecturerName);
                new Printer().printForLecturer(ZadwalContext.getInstance().getScheduler().getSchedules(), lecturerName);
                break;
        }
    }

}
