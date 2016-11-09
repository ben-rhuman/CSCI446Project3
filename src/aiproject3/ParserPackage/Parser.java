/*
 * Group 8: Ben Rhuman, Isaac Sotelo,                                                                                                                                                       
 */
package aiproject3.ParserPackage;

/**
 * This class reads in and normalizes 
 * the data sets to be generic and compatible 
 * with our algorithms.
 */
public abstract class Parser {
    
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
    
    //Concrete methods
    private void readInData(){ // Reads in the data from the .txt files
        
    }
    private void replaceMissingValue(){ //Randomly replaces the missing data with a value in a desired range
        
    }
}
