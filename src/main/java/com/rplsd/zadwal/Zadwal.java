package com.rplsd.zadwal;
import com.rplsd.zadwal.parser.ZadwalDriver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Zadwal
{
    public static void main( String[] args ) {
        ZadwalDriver driver = new ZadwalDriver();
        if(args.length == 0) {
            System.out.println("Start interactive Zadwal interpreter");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                String line;
                System.out.print(">");
                while((line = br.readLine()) != null) {
                    if(line != "\n") driver.start(line);
                    System.out.print(">");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
