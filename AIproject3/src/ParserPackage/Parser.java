/*
 * Group 8: Ben Rhuman, Isaac Sotelo,                                                                                                                                                       
 */
package ParserPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

/**
 * This class reads in and normalizes the data sets to be generic and compatible
 * with our algorithms.
 */
public abstract class Parser {

    //Variables
    protected ArrayList<ArrayList<String>> data;   //the instances of our data
    protected ArrayList<ArrayList<String>> attRange; //the range of values for each attribute, for ID3
    private int BINS = 6; //Tuned to 6 bins
    
    //Parser constructor
    public Parser() {
        
        readInData();
        removeID();
        handleMissingVal();
        moveClass();
        discretize();
        attributeRange();
        //printData();
        
    }

    //Abstract methods
    abstract void removeID(); //Removes the IDs in the data sets that require it\

    abstract void handleMissingVal(); //Handles the missing value in the data sets

    abstract void moveClass(); //Moves the data's classification to the end of the array 

    abstract void discretize(); //Converts continous data into discrete data


    abstract void attributeRange(); //obtain the range of what each attribute can be, for ID3
    
    public abstract String fileName(); //Returns the appropriate file name for the data set


    
    

    //Concrete methods
    
    protected final void readInData() { // Reads in the data from the .txt files
        this.data = new ArrayList<ArrayList<String>>();
        this.attRange = new ArrayList<ArrayList<String>>();
        
        String filePath = new File("").getAbsolutePath() + "/src/data/" + fileName(); //Creates the file path of the desired data set for windows

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
                }
            }
        }
    }

    protected void discretizeConcrete() {
        int bins = BINS; //Somewhat arbitrary value that will require some tuning
        double binSize;
        for (int j = 0; j < data.get(1).size() - 1; j++) {

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

            binSize = (high - low) / bins;

            for (int i = 0; i < data.size(); i++) {
                for (int k = 1; k <= bins; k++) {
                    if (Double.parseDouble(data.get(i).get(j)) <= low + (binSize * k)) {
                        data.get(i).set(j, Integer.toString(k));
                        break;
                    }
                }
            }
        }
    }
    
////ArrayList<ArrayList<String>> d2 = new ArrayList<ArrayList<String>>();
////    public ArrayList<ArrayList<String>> split(boolean train) { //to test out algorithms 
////        ArrayList<ArrayList<String>> d1 = new ArrayList<ArrayList<String>>();
////        
////        
////        
////         for (int i = 0; i < data.size(); i++){
////             if(Math.random()<.10){
////                 d2.add(data.get(i));
////             }else{
////                 d1.add(data.get(i));
////             }
////             
////         }
////         
////        
////        if (train) {
////            return d1;
////        } else {
////            return d2;
////        }
////    }

    private void printData() { //Prints out the data set
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).size(); j++) {
                System.out.print(data.get(i).get(j) + ", ");
            }
            System.out.println("");
        }
    }

    public ArrayList getData() {
        return data;
    }
    public ArrayList getAttRange(){
        return attRange;
    }
    public int getBins(){
        return BINS;
    }
}
