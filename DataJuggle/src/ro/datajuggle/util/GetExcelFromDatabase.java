/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.datajuggle.util;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class GetExcelFromDatabase {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws SQLException, IOException {
        CreateExcelTableFromDatabase cetfb = new CreateExcelTableFromDatabase();
    }
    
}
