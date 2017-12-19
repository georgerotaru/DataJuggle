/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.datajuggle.events;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author admin
 */
public class WriteToExcel {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // create a new workbook
        Workbook wb = new HSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();
        // create a new sheet
        Sheet sheet = wb.createSheet("result");
        // create a new row
        for (int i=0; i<2;i++){
            Row row = sheet.createRow(i);
            // create cells
            //Cell cell = row.createCell(0);
            row.createCell(0).setCellValue(createHelper.createRichTextString("Name"));
            row.createCell(1).setCellValue(createHelper.createRichTextString("Description"));
            row.createCell(2).setCellValue(createHelper.createRichTextString("Start time"));
            row.createCell(3).setCellValue(createHelper.createRichTextString("End Time"));
            row.createCell(4).setCellValue(createHelper.createRichTextString("Going"));
            row.createCell(5).setCellValue(createHelper.createRichTextString("Interested"));
            row.createCell(6).setCellValue(createHelper.createRichTextString("Country"));
            row.createCell(7).setCellValue(createHelper.createRichTextString("City"));
            row.createCell(8).setCellValue(createHelper.createRichTextString("Place"));
            row.createCell(9).setCellValue(createHelper.createRichTextString("Link"));
            row.createCell(10).setCellValue(createHelper.createRichTextString("Created by"));
        }
        FileOutputStream fileOut = new FileOutputStream("findmefirst.xls");
        wb.write(fileOut);
        fileOut.close();
    }
    
}
