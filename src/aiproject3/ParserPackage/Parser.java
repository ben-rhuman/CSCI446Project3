/*
 * Group 8: Ben Rhuman, Isaac Sotelo,                                                                                                                                                       
 */
package aiproject3.ParserPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class reads in and normalizes 
 * the data sets to be generic and compatible 
 * with our algorithms.
 */
public abstract class Parser {
    //Variables
     ArrayList<ArrayList<String>> group;
     
    //Parser constructor
    public Parser(){
        readInData();
        convertToNum();
        removeID();
        replaceMissingValue();
        moveClass();
    }
    
    //Abstract methods
    abstract void convertToNum(); //Converts the data to floating point values
    abstract void removeID(); //Removes the IDs in the data sets that require it
    abstract void moveClass(); //Moves the data's classification to the end of the array 
    abstract String fileName();
    
    //Concrete methods
    public void readInData() { // Reads in the data from the .txt files

        ArrayList<ArrayList<String>> group;
        group = new ArrayList<ArrayList<String>>();

        String filePath = new File("").getAbsolutePath() + "\\src\\aiproject3\\ParserPackage\\data\\" + fileName();
        File file = new File(filePath);

        if (file.isFile()) {
            BufferedReader inputStream = null;

            try {
                inputStream = new BufferedReader(new FileReader(file));
                String line;

                while ((line = inputStream.readLine()) != null) {
                    ArrayList<String> data = new ArrayList<>();

                    String[] tokens = line.split(",");
                    for (String t : tokens) {
                        data.add(t);

                    }
                    group.add(data);
                }

            } catch (FileNotFoundException ex) {
                System.out.println("file not found");;
            } catch (IOException ex) {}

        }

        for (int i = 0; i < group.size(); i++) {
            for (int j = 0; j < group.get(i).size(); j++) {
                System.out.println(group.get(i).get(j));
            }
        }
       
    }
    
    private void replaceMissingValue(){ //Randomly replaces the missing data with a value in a desired range
        
    }
}
