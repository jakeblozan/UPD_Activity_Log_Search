import java.io.*;
import java.util.*;

/**
 * Searches saved Cal Poly UPD Activity Log webpages for keywords or by location.
 *
 * @author (Jake Blozan)
 * @version (6/4/19)
 */
public class Search
{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Welcome! Please enter the name of the document you would like to search (with extension):\n");
        // String fileName = scan.nextLine();
        System.out.print("\n");
        String fileName = "Activity Logs - University Police Department - Cal Poly.html";

        new Trimmer().trim(fileName);

        System.out.println("Would you like to scan by details, or by location?\n");
        String scanType = scan.nextLine();
        System.out.print("\n");

        boolean valid = false;
        while (!valid) {
            if (scanType.equalsIgnoreCase("details") || scanType.equalsIgnoreCase("detail") || scanType.equalsIgnoreCase("det")) {
                valid = true;
                scanType = "detail";
            }
            else if (scanType.equalsIgnoreCase("location") || scanType.equalsIgnoreCase("loc")) {
                valid = true;
                scanType = "location";
            }
            else {
                System.out.println("Sorry, I didn't get that. Please enter \"details\", or \"location\":\n");
                scanType = scan.nextLine();
                System.out.print("\n");
            }
        }

        int numHits = 0;

        if (scanType.equals("detail")) {
            System.out.println("Please enter the term you would like to search for:\n");
            String input = scan.nextLine();
            System.out.print("\n");

            System.out.println("Searching now...\n");
            numHits = searchDetail(getEntries(), input);
        }

        if (scanType.equals("location")) {
            System.out.println("Please enter the place you would like to search for:\n");
            String input = scan.nextLine();
            System.out.print("\n");

            System.out.println("Searching now...\n");
            numHits = searchLocation(getEntries(), input);
        }

        System.out.println("Success! " + numHits + " entries located! Results can be found in the results.html file. Have a good one!");
    }

    public static Map<Integer, ArrayList<String>> getEntries() {
        String database = "output.html";
        Map<Integer, ArrayList<String>> entries = new TreeMap<>();
        try {
            FileReader fileReader = new FileReader(database);
            BufferedReader reader = new BufferedReader(fileReader);
            ArrayList<String> items = new ArrayList<String>();
            String line;
            boolean active = false;
            int numEntries = 0;

            while ((line = reader.readLine()) != null) {
                if (line.equals("<tr>")) {
                    active = true;
                    numEntries++;
                }
                else if (line.equals("</tr>")) {
                    active = false;
                    ArrayList<String> temp = new ArrayList<>(items);
                    entries.put(numEntries, temp);
                    items.clear();
                }
                else if (active) {
                    items.add(line.substring(line.indexOf("<td>"),line.indexOf("</td>") + 5));
                }
                // System.out.println(items);
                // System.out.println(numEntries);
                // System.out.println(active);
                // System.out.println("\n");

            }           
            reader.close();

            // for (Map.Entry<Integer, ArrayList<String>> entry : entries.entrySet()) {
            // int key = entry.getKey();
            // ArrayList<String> value = entry.getValue();

            // System.out.printf("%s : %s\n", key, value);
            // }
            // System.out.println(entries.get(3520));
            // System.out.println(numEntries);
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                database + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + database + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
        return entries;
    }

    public static int searchDetail(Map<Integer, ArrayList<String>> entries, String input) {
        String outputName = "results.html";
        int numHits = 0;
        try {
            FileWriter fileWriter = new FileWriter(outputName);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            for (int i = 1; i <= entries.size(); i++) {
                ArrayList<String> item = new ArrayList<>(entries.get(i));
                if (item.get(3).toLowerCase().indexOf(input.toLowerCase()) != -1) {
                    numHits++;
                }
            }

            writer.write("<!DOCTYPE html><html lang=\"en\"><head>" +
                "<title>Activity Logs - University Police Department - Cal Poly</title>" +
                "</head><body>number of entries found: " + numHits + "\n<p>Activity Logs, summarizing incidents reported to the University Police over the past sixty days, " +
                "appear with the most recent log at the top of the list. An updated log is generally posted by 9:00 AM the following day.</p>" +
                "<!--from cache--><table><thead><tr><th>Date/Time Reported</th><th>Date/Time Occurred</th><th>Location</th><th>Details</th><th>Disposition</th></tr></thead><tbody>");

            boolean areWeIn = false;
            for (int i = 1; i <= entries.size(); i++) {
                ArrayList<String> item = new ArrayList<>(entries.get(i));
                if (item.get(3).toLowerCase().indexOf(input.toLowerCase()) != -1) {
                    areWeIn = true;
                }
                else {
                    areWeIn = false;
                }
                if (areWeIn) {
                    writer.write("\n<tr>");
                    for (int j = 0; j < 5; j++) {
                        writer.write("\n\t" + item.get(j));
                    }
                    writer.write("\n</tr>");
                }
            }
            writer.write("</body></html>");
            writer.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                outputName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '" 
                + outputName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
        return numHits;
    }

    public static int searchLocation(Map<Integer, ArrayList<String>> entries, String input) {
        String outputName = "results.html";
        int numHits = 0;
        try {
            FileWriter fileWriter = new FileWriter(outputName);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            for (int i = 1; i <= entries.size(); i++) {
                ArrayList<String> item = new ArrayList<>(entries.get(i));
                if (item.get(2).toLowerCase().indexOf(input.toLowerCase()) != -1) {
                    numHits++;
                }
            }

            writer.write("<!DOCTYPE html><html lang=\"en\"><head>" +
                "<title>Activity Logs - University Police Department - Cal Poly</title>" +
                "</head><body>number of entries found: " + numHits + "\n<p>Activity Logs, summarizing incidents reported to the University Police over the past sixty days, " +
                "appear with the most recent log at the top of the list. An updated log is generally posted by 9:00 AM the following day.</p>" +
                "<!--from cache--><table><thead><tr><th>Date/Time Reported</th><th>Date/Time Occurred</th><th>Location</th><th>Details</th><th>Disposition</th></tr></thead><tbody>");

            boolean areWeIn = false;
            for (int i = 1; i <= entries.size(); i++) {
                ArrayList<String> item = new ArrayList<>(entries.get(i));
                if (item.get(2).toLowerCase().indexOf(input.toLowerCase()) != -1) {
                    areWeIn = true;
                }
                else {
                    areWeIn = false;
                }
                if (areWeIn) {
                    writer.write("\n<tr>");
                    for (int j = 0; j < 5; j++) {
                        writer.write("\n\t" + item.get(j));
                    }
                    writer.write("\n</tr>");
                }
            }
            writer.write("</body></html>");
            writer.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                outputName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '" 
                + outputName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
        return numHits;
    }
}