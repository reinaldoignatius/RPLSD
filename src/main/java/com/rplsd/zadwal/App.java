package com.rplsd.zadwal;
import com.rplsd.zadwal.parser.ZadwalDriver;

import java.util.Scanner;

public class App
{
    public static void main( String[] args ) {
//        Scanner scanner = new Scanner(System.in);
//        scanner.nextLine();
        ZadwalDriver driver = new ZadwalDriver();
        driver.start("CLASSROOM R7602 50 [projector,ac];"+
                "CLASSROOM R7606 50 [projector, ac, internet];"+
                "Lecturer YA [11,12,13,14,21,22,23,24,31,32,33,34];"
                );
    }
}
