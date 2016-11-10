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
    ArrayList<ArrayList<String>> group;
    ArrayList<ArrayList<Double>> data = new ArrayList<ArrayList<Double>>();

    //Parser constructor
    public Parser() {
        readInData();
        removeID();
        convertToNum();
        replaceMissingValue();
        moveClass();
        printData();
    }

    //Abstract methods
    abstract void convertToNum(); //Converts the data to floating point values

    abstract void removeID(); //Removes the IDs in the data sets that require it

    abstract void moveClass(); //Moves the data's classification to the end of the array 

    abstract String fileName();

    //Concrete methods
    public void readInData() { // Reads in the data from the .txt files
        this.group = new ArrayList<ArrayList<String>>();

        String filePath = new File("").getAbsolutePath() + "\\src\\aiproject3\\ParserPackage" + fileName(); //Creates the file path of the desired data set
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
            } catch (IOException ex) {
            }
        } else {
            System.out.println("File not found");
        }
    }

    private void replaceMissingValue() { //Randomly replaces the missing data with a value in a desired range
        Random r = new Random();
        for (int i = 0; i < data.size(); i++) { // i is the line number
            for (int j = 0; j < data.get(i).size(); j++) { // j is the data number.
                if (data.get(i).get(j) == null) {
                    Double attribute = null;
                    while (attribute == null) { // While the selected attribute is null
                        attribute = data.get(r.nextInt(data.size())).get(j); // select a random line in the data and select the desired attribute.
                    }
                    data.get(i).set(j, attribute);
                }
            }
        }
    }

    private void printData() {
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).size(); j++) {
                System.out.print(data.get(i).get(j) + ", ");
            }
            System.out.println("");
        }
    }
}
