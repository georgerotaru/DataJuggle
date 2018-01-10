/*
 * Connect to events database
 */
package ro.datajuggle.events;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author george
 */
public class DBConnect {

        // login credentials for EVENTS
        private String host = "jdbc:derby://localhost:1527/events";
        private String username = "events";
        private String passwd = "events";
        private String driver = "org.apache.derby.jdbc.ClientDriver";
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

    public DBConnect() throws ClassNotFoundException, SQLException {

        try {
            Class driverClass = Class.forName(driver);
            connection = DriverManager.getConnection(host, username, passwd);
            statement = connection.createStatement();
            System.out.println("Connection to Java DB established!");
            //statement.execute("INSERT INTO APP.EVENTS_ABOUT V    ALUES ('3','testName','testCity','testPlace',1,2,1.1,2.2,'19.01.2017','18:00')");
                
        } catch (ClassNotFoundException ex) {
                System.out.println("Can't connect to Java DB database");
                Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
