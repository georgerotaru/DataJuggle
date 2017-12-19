/*
 * This is used for transforming a simple userInputedDate format from "MM/dd/yyyy HH:mm:ss"
 * to unix timestamp and the other way around
 * Time will automaticaly be set to 00:00:00
 */
package ro.datajuggle.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Transform simple userInputedDate format to unix timestamp
 * @author George
 */
public class SetDate {
private int inputYear;
private int inputMonth;
private int inputDay;
private String userInputedDate;

    public String inputDate() {
        Scanner sc = new Scanner(System.in);
        // input year, month, day
        System.out.println("Input year: ");
        inputYear = sc.nextInt();
        System.out.println("Input month: ");
        inputMonth = sc.nextInt();
        System.out.println("Input day: ");
        inputDay = sc.nextInt();
        // concatenate month,day,year,time
        userInputedDate = inputMonth+"/"+inputDay+"/"+inputYear+" 00:00:00";
        return userInputedDate;
    }

/**
 * Transform simple date format to unix timestamp
     * @return unixTime
 */    
    public long transformToUnixTimestamp() {
        //String dateString = "12/17/2017 00:00:00";
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(userInputedDate);
        } catch (ParseException ex) {
            Logger.getLogger(SetDate.class.getName()).log(Level.SEVERE, null, ex);
        }
        long unixTime = (long)date.getTime()/1000;
        return unixTime;
    }
}
