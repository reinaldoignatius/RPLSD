package com.rplsd.zadwal.parser;

import com.rplsd.zadwal.scheduler.Constants;
import com.rplsd.zadwal.scheduler.ScheduleRule;
import com.rplsd.zadwal.scheduler.Scheduler;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ZadwalContext {
    private static ZadwalContext instance_;

    private Scheduler scheduler;
    public static final String FIXED_SCHEDULE = "fixed_schedule";
    public static final String MAX_LECTURER_HOUR = "max_lecturer_hour";
    public static final String RESTRICTED_TIME = "restricted_time";
    public static final String NON_CONFLICT = "non_conflict";

    public static ZadwalContext getInstance() {
        if(instance_ == null) {
            instance_ = new ZadwalContext();
        }
        return instance_;
    }
    private ZadwalContext() {
        this.scheduler = new Scheduler(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ScheduleRule(new HashMap<>(), new HashMap<>(), new HashSet<>(), Constants.MAX_INT),
                new ScheduleRule(new HashMap<>(), new HashMap<>(), new HashSet<>(), Constants.MAX_INT));
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public static ArrayList< ArrayList<Boolean> > availabilityParser(ArrayList<String> teachingHours) {
        ArrayList<ArrayList<Boolean>> res = new ArrayList<>();
        for(int i=0;i<Constants.DAYS_IN_A_WEEK;i++) {
            ArrayList<Boolean> buff = new ArrayList<Boolean>(Constants.HOURS_IN_A_DAY);
            for(int j=0; j<Constants.HOURS_IN_A_DAY; j++) {
                buff.add(false);
            }
            res.add(buff);
        }
        for(String i : teachingHours) {
            int day = Integer.parseInt(i.substring(0,1))-1;
            int hour = Integer.parseInt(i.substring(1))-1;
            res.get(day).set(hour, true);
        }
        return res;
    }

    public static Set<Pair<Integer,Integer>> timeParser(ArrayList<String> teachingHours) {
        Set<Pair<Integer,Integer>> res = new HashSet<>();
        for(String i : teachingHours) {
            int day = Integer.parseInt(i.substring(0,1))-1;
            int hour = Integer.parseInt(i.substring(1))-1;
            res.add(new Pair(day,hour));
        }
        return res;
    }

    public static Pair<Integer,Integer> timeConverter(String time) {
        int day = Integer.parseInt(time.substring(0,1))-1;
        int hour = Integer.parseInt(time.substring(1))-1;
        return new Pair(day,hour);
    }
}
