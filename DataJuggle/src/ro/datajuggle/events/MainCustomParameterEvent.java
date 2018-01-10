/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.datajuggle.events;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import com.restfb.types.Event;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import ro.datajuggle.util.AccessToken;
import ro.datajuggle.util.SetDate;

/**
 *
 * @author admin
 */
public class MainCustomParameterEvent {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        FacebookClient fbClient = new DefaultFacebookClient(new AccessToken().getAccessToken(), Version.VERSION_2_11);
        String id;
        String eventParameter;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input search parameter for an event: ");
        eventParameter = scanner.nextLine();
        SetDate setDate = new SetDate();
        setDate.inputDate();
        long unixDate = setDate.transformToUnixTimestamp();
        Connection<JsonObject> targetedSearch = fbClient.fetchConnection("search", JsonObject.class, Parameter.with("q", eventParameter), Parameter.with("type", "event"), Parameter.with("since", unixDate));
        
        Workbook wb = new HSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();
        Sheet sheet = wb.createSheet(eventParameter);
        Row row = sheet.createRow(0);
            row.createCell(0).setCellValue(createHelper.createRichTextString("Name"));
            row.createCell(1).setCellValue(createHelper.createRichTextString("Start time"));
            row.createCell(2).setCellValue(createHelper.createRichTextString("End Time"));
            row.createCell(3).setCellValue(createHelper.createRichTextString("Going"));
            row.createCell(4).setCellValue(createHelper.createRichTextString("Interested"));
            row.createCell(5).setCellValue(createHelper.createRichTextString("Country"));
            row.createCell(6).setCellValue(createHelper.createRichTextString("City"));
            row.createCell(7).setCellValue(createHelper.createRichTextString("Place"));
            row.createCell(8).setCellValue(createHelper.createRichTextString("Link"));
        
        int counter = 0;
        int rowCount = 1;
        for (List<JsonObject> page : targetedSearch) {
            int insiderCounter = 0;
            for (JsonObject oneByOne : page) {
                row = sheet.createRow(rowCount);
                id = targetedSearch.getData().get(insiderCounter).getString("id","I cannot find any Event ID");
                Event event = fbClient.fetchObject(id, Event.class, Parameter.with("fields", "interested_count,attending_count,description,end_time,id,name,place,start_time,admins"));
                
                String linkToEvent = "https://www.facebook.com/"+event.getId();
                System.out.println("\033[0;31m"+"Link to event: "+"\033[0m"+linkToEvent);
                row.createCell(8).setCellValue(createHelper.createRichTextString(linkToEvent));
                
                String eventName = event.getName();
                System.out.println("\033[0;31m"+"Name of event: "+"\033[0m"+eventName);
                row.createCell(0).setCellValue(createHelper.createRichTextString(eventName));
                
                System.out.println("\033[0;31m"+"Event description: "+"\033[0m"+event.getDescription());
                
                Date eventStartTime = event.getStartTime();
                //Date eventEndTime = event.getEndTime();
                System.out.println("\033[0;31m"+"From "+"\033[0m"+eventStartTime+"\033[0;31m"+" to ");//+"\033[0m"+eventEndTime);
                row.createCell(1).setCellValue(createHelper.createRichTextString(eventStartTime.toString()));
                //row.createCell(2).setCellValue(createHelper.createRichTextString(eventEndTime.toString()));
                
                Integer eventAttendingCount = event.getAttendingCount();
                System.out.println("\033[0;31m"+"People going: "+"\033[0m"+eventAttendingCount);
                row.createCell(3).setCellValue(createHelper.createRichTextString(eventAttendingCount.toString()));
                
                Long eventInterestedCount = event.getInterestedCount();
                System.out.println("\033[0;31m"+"People interested: "+"\033[0m"+event.getInterestedCount());
                row.createCell(4).setCellValue(createHelper.createRichTextString(eventAttendingCount.toString()));

                try { 
                    String eventCountry = event.getPlace().getLocation().getCountry();
                    System.out.println("\033[0;31m"+"Country: "+"\033[0m"+eventCountry);
                    row.createCell(5).setCellValue(createHelper.createRichTextString(eventCountry));
                } catch (NullPointerException e) {
                    System.out.println("Country: no country set");
                    row.createCell(5).setCellValue(createHelper.createRichTextString("null"));
                }
                 try { 
                     String eventCity = event.getPlace().getLocation().getCity();
                     System.out.println("\033[0;31m"+"City: "+"\033[0m"+eventCity);
                     row.createCell(6).setCellValue(createHelper.createRichTextString(eventCity));
                } catch (NullPointerException e) {
                    System.out.println("\033[0;31mCity: \033[0mno city set");
                    row.createCell(6).setCellValue(createHelper.createRichTextString("null"));
                }
                try { 
                    String eventPlace = event.getPlace().getName();
                    System.out.println("\033[0;31m"+"Place: "+"\033[0m"+eventPlace);
                    row.createCell(7).setCellValue(createHelper.createRichTextString(eventPlace));
                } catch (NullPointerException e) {
                    System.out.println("\033[0;31mPlace: \033[0mno place set");
                    row.createCell(7).setCellValue(createHelper.createRichTextString("null"));
                }
                try { System.out.println("\033[0;31m"+"Admin: "+"\033[0m"+event.getOwner().getCategoryList().toString());
                } catch (NullPointerException e) {
                    System.out.println("\033[0;31mAdmin: \033[0mno admin found");
                }
                insiderCounter++;
                counter++;
                rowCount++;
                System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
            }
        }
        System.out.println("Total events for your search: "+counter+" (using the parameter "+eventParameter+")");
        
        String outputFileName = eventParameter+".xls";
        FileOutputStream fileOut = new FileOutputStream(outputFileName);
        wb.write(fileOut);
        fileOut.close();
    }
    
}
