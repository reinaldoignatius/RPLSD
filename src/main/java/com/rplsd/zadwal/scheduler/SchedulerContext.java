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

}
