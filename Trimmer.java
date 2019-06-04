import java.io.*;
import java.util.*;

/**
 * Trims saved Cal Poly UPD Activity Log webpages.
 *
 * @author (Jake Blozan)
 * @version (6/3/19)
 */
public class Trimmer
{
    public static void main(String[] args) {
        String fileName = "Activity Logs - University Police Department - Cal Poly.html";
        System.out.println("Now trimming...");
        trim(fileName);
        System.out.println("Done!");
    }

    public static void trim(String fileName) {
        String startMark = "Activity Logs, summarizing incidents reported to the University Police";
        String endMark = "<!--END MAIN CONTENT AREA: DO NOT EDIT BELOW-->";

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fileReader);
            System.out.println("File located, scanning...");
            int lines = 0;
            while (reader.readLine() != null) lines++;
            //System.out.println(lines);
            reader.close();

            // while (dataScan.hasNext()) {
            // data += dataScan.nextLine();
            // lineNum++;
            // System.out.println("Current line: " + lineNum);
            // }

            fileReader = new FileReader(fileName);
            reader = new BufferedReader(fileReader);
            FileWriter fileWriter = new FileWriter("output.html");
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write("<!DOCTYPE html><html lang=\"en\"><head>" +
                "<title>Activity Logs - University Police Department - Cal Poly</title>" +
                "</head><body>");

            boolean areWeIn = false;
            for (int i = 0; i < lines; i++) {
                String line = reader.readLine();
                if (line.indexOf(startMark) != -1) {
                    areWeIn = true;
                }
                else if (line.indexOf(endMark) != -1) {
                    areWeIn = false;
                }
                if (areWeIn) {
                    writer.write("\n" + line);
                }
            }
            
            writer.write("</body></html>");

            reader.close();
            writer.close();
        }

        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
    }
}
