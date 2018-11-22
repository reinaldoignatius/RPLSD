package com.rplsd.zadwal;
import com.rplsd.zadwal.parser.ZadwalDriver;
import com.rplsd.zadwal.scheduler.Constants;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
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
                System.out.print("> ");
                while((line = br.readLine()) != null) {
                    if(line != "\n") driver.start(line);
                    System.out.print("> ");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if(args.length == 1) {
            try {
                byte[] encoded = Files.readAllBytes(Paths.get(args[0]));
                driver.start(new String(encoded, "UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}
