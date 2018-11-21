package com.rplsd.scheduler;
//<<<<<<< Updated upstream
//
//import java.util.ArrayList;
//
//public class App {
//    public static void main(String[] args) {
//        System.out.println("Hello World!");
//        ArrayList<ClassRoom> classRooms = new ArrayList<>();
//        ArrayList<ClassRequirement> classRequirements = new ArrayList<>();
//        ArrayList<Lecturer> lecturers = new ArrayList<>();
//        ScheduleConstraint scheduleConstraint = new ScheduleConstraint(3);
//        SchedulePreference schedulePreference = new SchedulePreference();
//        Scheduler scheduler = new Scheduler(
//                classRooms, classRequirements, lecturers, scheduleConstraint, schedulePreference);
//
//        scheduler.addClassRoom(new ClassRoom("7602", 100, new ArrayList<String>(){{
//            add("ac");
//            add("ac2");
//        }}));
//
//        ArrayList<ArrayList<Boolean>> classRoomAvailability = scheduler.getClassRoomsAvailability().get("7602");
//        for (ArrayList<Boolean> day: classRoomAvailability) {
//            for (Boolean time: day) {
//                System.out.println(time);
//            }
//        }
//=======
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class App
{
    public static void main( String[] args )
    {
        ANTLRInputStream inputStream = new ANTLRInputStream(
                "CLASSROOM 80 [projector,ac];\nCLASSROOM 7606 40 [projector,ac];"
        );
        ZadwalLexer lexer = new ZadwalLexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        ZadwalParser parser = new ZadwalParser(commonTokenStream);
//        System.out.println(parser.eval());
        ParseTree tree = parser.eval(); // parse the content and get the tree
        ZadwalListenerImpl listener = new ZadwalListenerImpl();

        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);

    }
}
