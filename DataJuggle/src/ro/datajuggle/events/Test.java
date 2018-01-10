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
public class Test {

    /**
     * @param args the command line arguments
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        int i = 1;
        while (i!=0) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("press any no. to continue or pres 0 to exit: ");
            i = scanner.nextInt();
            EventsAbout events = new EventsAbout();
        }
    }
    
}
