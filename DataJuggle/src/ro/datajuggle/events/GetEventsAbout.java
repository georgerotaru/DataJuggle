/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.datajuggle.events;

import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author admin
 */
public class GetEventsAbout {

    /**
     * @param args the command line arguments
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //DBConnect db = new DBConnect();
        
        String userAnswer;
        boolean inputAnother = true;
        while (inputAnother) {
            EventsAbout events = new EventsAbout();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input another event? (yes/no): ");
            userAnswer = scanner.nextLine();
            if (userAnswer.equals("no")) {
                inputAnother = false;
            }
        }
    }
}
