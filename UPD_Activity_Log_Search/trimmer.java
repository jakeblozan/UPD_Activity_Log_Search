import java.io.*;
import java.util.*;

/**
 * Trims saved Cal Poly UPD Activity Log webpages.
 *
 * @author (Jake Blozan)
 * @version (6/3/19)
 */
public class trimmer
{
    public static void main(String[] args) {
        String fileName = "html activity record.htm";
        String data = "";
        
        try {
            Scanner dataScan = new Scanner(new File(fileName));
            while (dataScan.hasNext()) {
                data += dataScan.nextLine();
            }
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

        System.out.println(data);
    }
}
