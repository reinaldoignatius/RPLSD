package com.rplsd.zadwal.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SchedulerContext {
    private static SchedulerContext instance_;

    private Scheduler scheduler;

    public static SchedulerContext getInstance() {
        if(instance_ == null) {
            instance_ = new SchedulerContext();
        }
        return instance_;
    }
    private SchedulerContext() {
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

}
