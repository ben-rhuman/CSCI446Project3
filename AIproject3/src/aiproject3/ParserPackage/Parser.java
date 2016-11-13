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
import java.util.Random;

/**
 * This class reads in and normalizes the data sets to be generic and compatible
 * with our algorithms.
 */
public abstract class Parser {

    //Variables
    protected ArrayList<ArrayList<String>> data;

    //Parser constructor
    public Parser() {
        readInData();
        removeID();
        handleMissingVal();
        moveClass();
        discretize();
        printData();
    }

    //Abstract methods
    abstract void removeID(); //Removes the IDs in the data sets that require it\
    abstract void handleMissingVal(); //Handles the missing value in the data sets
    abstract void moveClass(); //Moves the data's classification to the end of the array 
    abstract void discretize(); //Converts continous data into discrete data
    abstract String fileName(); //Returns the appropriate file name for the data set
    

    //Concrete methods
    protected void readInData() { // Reads in the data from the .txt files
        this.data = new ArrayList<ArrayList<String>>();
        
        String filePath = new File("").getAbsolutePath() + "/src/aiproject3/ParserPackage/data/" + fileName(); //Creates the file path of the desired data set for windows
        
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
                    this.data.add(data);
                }
            } catch (FileNotFoundException ex) {
                System.out.println("file not found");;
            } catch (IOException ex) {
            }
        } else {
            System.out.println("File not found");
        }
    }

    protected void replaceMissingValue() { //Randomly replaces the missing data with a value in a desired range
        Random r = new Random();
        for (int i = 0; i < data.size(); i++) { // i is the line number
            for (int j = 0; j < data.get(i).size(); j++) { // j is the data number.
                if (data.get(i).get(j).equals("?")) {
                    String attribute = "?";
                    while (attribute.equals("?")) { // While the selected attribute is null
                        attribute = data.get(r.nextInt(data.size())).get(j); // select a random line in the data and select the desired attribute.
                    }
                    data.get(i).set(j, attribute);
                    System.out.println("Replaced value: " + data.get(i).get(j));
                }
            }
        }
    }
    
    protected void discretizeConcrete(){
        int bins = 10; //Somewhat arbitrary value that will require some tuning
        double binSize;
        for (int j = 0; j < data.get(1).size() - 1 ; j++) {
            
            double low = Double.POSITIVE_INFINITY;
            double high = Double.NEGATIVE_INFINITY;
            
            for (int i = 0; i < data.size(); i++) {
                if (Double.parseDouble(data.get(i).get(j)) < low) {
                    low = Double.parseDouble(data.get(i).get(j));
                }
                if (Double.parseDouble(data.get(i).get(j)) > high) {
                    high = Double.parseDouble(data.get(i).get(j));
                }
            }
            
            binSize = (high - low)/bins;
            
            for(int i = 0; i < data.size(); i++){
                for(int k = 1; k <= bins; k++){
                    if(Double.parseDouble(data.get(i).get(j)) <= low+(binSize*k)){
                        data.get(i).set(j, Integer.toString(k));
                        break;
                    }
                }
            }
        }
    }

    private void printData() { //Prints out the data set
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).size(); j++) {
                System.out.print(data.get(i).get(j) + ", ");
            }
            System.out.println("");
        }
    }
    
    public ArrayList getData(){
        return data;
    }
}
