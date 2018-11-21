package com.rplsd.zadwal;
import com.rplsd.zadwal.parser.ZadwalDriver;

import java.util.Scanner;

public class App
{
    public static void main( String[] args ) {
        ZadwalDriver driver = new ZadwalDriver();
        driver.start(
                "CLASSROOM R7602 100 [projector,ac];"+
                "CLASSROOM R7606 50 [projector, ac, internet];"+
                "CLASSROOM R7604 40 [projector];"+
                "Lecturer YA [11,12,13,14,21,22,23,24,31,32,33,34];"+
                "Lecturer MZC [11,12,13,14,21,22,23,24,31,32,33,34];"+
                "lecturer SAR [11,12,12,14,21,22,23,24,31,32,33,34];"+
                "lecturer PS [11,12,13,14,15,16,17,18,19,110,111];"+
                "CLASS IF4151K1 YA 50 [projector] 2;"+
                "CLASS IF4031K2 [MZC,SAR] 20 50 [projector,ac] 3;"+
                "CLASS IF4090K2 PS 50 [projector,ac] 2;"+
                "CONSTRAINT FIXED SCHEDULE IF4090K2 [14,15];"+
                "CONSTRAINT TEACHING DURATION LIMIT 3;"+
                "CONSTRAINT UNAVAILABLE [55,56];"+
                "PREFERENCE NON-CONFLICT [IF4090K2:IF4031K2, IF4090K2:IF4151K1];"+
                "SCHEDULE;"
                );
    }
}
