/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.datajuggle.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author admin
 */
public class CreateExcelTableFromDatabase {

private String username = "events";
private String passwd = "events";
private String host = "jdbc:derby://localhost:1527/events;create=true";
private String driver = "org.apache.derby.jdbc.ClientDriver";
private Connection connection = null;
private Statement statement = null;
private ResultSet resultSet = null;   
    
    public CreateExcelTableFromDatabase() throws SQLException, FileNotFoundException, IOException {
        try {
            Class driverClass = Class.forName(driver);
            connection = DriverManager.getConnection(host, username, passwd);
            System.out.println("Connection to Java DB established!");
            
            statement = connection.createStatement();      
             
            ResultSet resultSet = statement.executeQuery("Select * from APP.EVENTS_ABOUT");
            
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("event_details");
            
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((char) 0).setCellValue("event_id");
            rowhead.createCell((char) 1).setCellValue("event_name");
            rowhead.createCell((char) 2).setCellValue("event_city");
            rowhead.createCell((char) 3).setCellValue("event_place");
            rowhead.createCell((int) 4).setCellValue("attending");
            rowhead.createCell((int) 5).setCellValue("interested");
            rowhead.createCell((int) (double) 6).setCellValue("place_latitude");
            rowhead.createCell((int) (double) 7).setCellValue("place_longitude");
            rowhead.createCell((char) 9).setCellValue("start_date");
            rowhead.createCell((char) 10).setCellValue("start_time");
            rowhead.createCell((char) 11).setCellValue("end_date");
            rowhead.createCell((char) 12).setCellValue("end_time");
            rowhead.createCell((char) 13).setCellValue("event_url");
            
            int i = 1;
            while (resultSet.next()){
                HSSFRow row = sheet.createRow((short) i);
                row.createCell((char) 0).setCellValue(resultSet.getString("ID"));
                row.createCell((char) 0).setCellValue(resultSet.getString("NAME"));
                row.createCell((char) 0).setCellValue(resultSet.getString("CITY"));
                row.createCell((char) 0).setCellValue(resultSet.getString("PLACE"));
                row.createCell((int) 0).setCellValue(resultSet.getString("ATTENDING"));
                row.createCell((int) 0).setCellValue(resultSet.getString("INTERESTED"));
                row.createCell((int) (double) 0).setCellValue(resultSet.getString("LATITUDE"));
                row.createCell((int) (double) 0).setCellValue(resultSet.getString("LONGITUDE"));
                row.createCell((char) 0).setCellValue(resultSet.getString("START_DATE"));
                row.createCell((char) 0).setCellValue(resultSet.getString("START_TIME"));
                row.createCell((char) 0).setCellValue(resultSet.getString("END_DATE"));
                row.createCell((char) 0).setCellValue(resultSet.getString("END_TIME"));
                row.createCell((char) 0).setCellValue(resultSet.getString("EVENT_URL"));
                i++;
            }
            String outputPath = "D:/20event_aboutt.xls";
            FileOutputStream fileOut = new FileOutputStream(outputPath);
            workbook.write(fileOut);
            fileOut.close();
        } catch (ClassNotFoundException | SQLException | IOException e) {
               e.printStackTrace();
        }
    }
}
