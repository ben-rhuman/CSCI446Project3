/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aiproject3.ParserPackage;

/**
 *
 * @author k28h885
 */
public class BreastCancerData extends Parser {
    
    @Override
    void convertToNum(){ // Converts the String data into floating point data
    
    }
    
    @Override
    void removeID(){ // Removes the identification number from the data set
        
    }
    
    @Override
    void moveClass(){} // Classification value is already in the correct position
    
    @Override
    String fileName(){ // Returns the data file name
        return "/data/breast-cancer-wisconsin.data.txt";
    }
}
