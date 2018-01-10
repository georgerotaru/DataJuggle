/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.datajuggle.util;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author admin
 */
public class DateToCalendar {
    
    public Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }    
}
