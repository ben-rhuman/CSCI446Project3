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
public class HouseVotesData extends Parser{
    
    @Override
    void convertToNum(){ //Converts the String data into floating point data
    
    }
    
    @Override
    void removeID(){} //Does nothing since no IDs to remove
        
    @Override
    void moveClass(){// Moves the classification value to the end of the array
    
    }
    
    @Override
    String fileName(){ // Returns the data file name
        return "/data/house-votes-84.data.txt";
    }
}
