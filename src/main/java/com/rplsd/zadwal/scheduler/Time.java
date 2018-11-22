package com.rplsd.zadwal.scheduler;

public class Time {
    private int hour;
    private int minute;

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }
    public Time(Time time) {
        this.hour = time.hour;
        this.minute = time.minute;
    }
    public void addHour(int hour) {
        this.hour = (this.hour +hour) % 24;
    }
    public void addMinute(int minute) {
        int m = this.minute + minute;
        if(m >= 60) {
            int divisor = m/60;
            m = m % 60;
            this.addHour(divisor);

        }
        this.minute = m;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", hour,minute);
    }
}
