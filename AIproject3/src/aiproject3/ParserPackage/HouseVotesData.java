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
    void moveClass(){// Moves the classification value to the end of the list
        for(int i = 0; i < data.size(); i++){
            double temp = data.get(i).get(0);
            data.get(i).remove(0);
            data.get(i).add(temp);
        }
    }
    
    @Override
    String fileName(){ // Returns the data file name
        return "/data/house-votes-84.data.txt";
    }

    @Override
    void removeID() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}