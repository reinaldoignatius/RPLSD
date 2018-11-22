package com.rplsd.zadwal.scheduler;

import com.sun.tools.internal.jxc.ap.Const;

import java.util.ArrayList;
import java.util.List;
public class Printer {
    int terminalWidth= 140;
    int columnWidth = 15;
    int nColumn;
    boolean printRoom = true;
    boolean printLecturer = true;
    public Printer() {
        nColumn = Constants.daysInAWeek +1;
    }

    public Printer(boolean printLecturer, boolean printRoom) {
        this.printRoom = printRoom;
        this.printLecturer = printLecturer;
    }
    private void horizontalLine(){
        System.out.print("\u2500");
    }
    private void horizontalLine(int n) {
        for(int i =0;i<n;i++) {
            horizontalLine();
        }
    }

    private void downHorizontalLine(){
        System.out.print("\u252c");
    }
    private void upHorizontalLine(){
        System.out.print("\u2534");
    }
    private void verticalLine() {
        System.out.print("\u2502");
    }
    private void topLeft() {
        System.out.print("\u250c");
    }
    private void topRight() {
        System.out.print("\u2510");
    }
    private void bottomLeft() {
        System.out.print("\u2514");
    }
    private void bottomRight(){
        System.out.print("\u2518");
    }
    private void endl() {
        System.out.println();
    }
    private void topBorder() {
        topLeft();
        for(int i=0;i<nColumn;i++){
            horizontalLine(columnWidth);
            if(i != nColumn-1) downHorizontalLine();
            else topRight();
        }
        endl();

    }

    private void bottomBorder(){
        bottomLeft();
        for(int i=0;i<nColumn;i++){
            horizontalLine(columnWidth);
            if(i != nColumn-1) upHorizontalLine();
            else bottomRight();
        }
        endl();
    }
    private void middleLeft(){
        System.out.print("\u251c");
    }
    private void middleRight(){
        System.out.print("\u2524");
    }
    private void middleHorizontalLine(){
        System.out.print("\u253c");
    }
    private void middleBorder(int n){
        middleLeft();
        for(int i=0;i<n;i++){
            horizontalLine(columnWidth);
            if(i != n-1) middleHorizontalLine();
            else middleRight();
        }
        endl();
    }
    private void printColumn(Object content){
        verticalLine();
        System.out.format("%-"+columnWidth+"s",content);
    }
    private void printColumn(Object content, boolean right){
        verticalLine();
        System.out.format("%-"+columnWidth+"s",content);
        verticalLine();
    }
    public void printSchedule(ArrayList<ArrayList<List<ScheduleItem>>> schedules) {
        topBorder();
        printColumn("Time");
        for(int i=0;i<Constants.daysInAWeek; i++) {
            printColumn(Constants.dayNames.get(i));
        }
        verticalLine();
        endl();
        Time currentTime = Constants.startTime;
        middleBorder(nColumn);
        for(int j=0;j<Constants.hoursInADay;j++) {
            printColumn(currentTime);
            ArrayList<List<ScheduleItem>> scheduleList = new ArrayList<>();
            int maxScheduleItem = 0;
            for(int i=0;i<Constants.daysInAWeek;i++) {
                List<ScheduleItem> buf = schedules.get(i).get(j);
                maxScheduleItem = maxScheduleItem > buf.size() ? maxScheduleItem : buf.size();
                scheduleList.add(buf);
            }
            if(maxScheduleItem == 0) {
                for(int i=0;i<Constants.daysInAWeek;i++) {
                    printColumn("");
                }
                verticalLine();
                endl();
            }
            else {
                for(int i=0;i<maxScheduleItem;i++) {
                    if(i!=0) {
                        printColumn("");
                        middleBorder(nColumn-1);
                        printColumn("");
                    }
                    for(int k=0;k < nColumn-1;k++) {
                        List<ScheduleItem> current_schedule = scheduleList.get(k);
                        if(i < current_schedule.size()) {
                            printColumn(current_schedule.get(i).getCourseName());
                        } else {
                            printColumn("");
                        }
                    }
                    verticalLine();endl();
                    printColumn("");
                    for(int k=0;k < nColumn-1;k++) {
                        List<ScheduleItem> current_schedule = scheduleList.get(k);
                        if(i < current_schedule.size()) {
                            printColumn(current_schedule.get(i).getClassRoomId());
                        } else {
                            printColumn("");
                        }
                    }
                    verticalLine();endl();
                    printColumn("");
                    for(int k=0;k < nColumn-1;k++) {
                        List<ScheduleItem> current_schedule = scheduleList.get(k);
                        if(i < current_schedule.size()) {
                            printColumn(current_schedule.get(i).getFormattedLecturersName());
                        } else {
                            printColumn("");
                        }
                    }
                    verticalLine();endl();

                }
            }
            if(j != Constants.hoursInADay-1) middleBorder(nColumn);
            else bottomBorder();
            if(Constants.durationUnit == Constants.MINUTE_UNIT) {
                currentTime.addMinute(Constants.classDuration);
            } else {
                currentTime.addHour(Constants.classDuration);
            }
        }
   }
}
