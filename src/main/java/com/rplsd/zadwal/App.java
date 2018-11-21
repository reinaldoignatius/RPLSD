package com.rplsd.zadwal;
import com.rplsd.zadwal.parser.ZadwalDriver;

import java.util.Scanner;

public class App
{
    public static void main( String[] args ) {
//        Scanner scanner = new Scanner(System.in);
//        scanner.nextLine();
        ZadwalDriver driver = new ZadwalDriver();
        driver.start("classroom R7602 50 [projector,ac];");
    }
}
