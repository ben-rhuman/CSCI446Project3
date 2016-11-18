/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParserPackage;

import java.util.ArrayList;

/**
 *
 * @author k28h885
 */
public class HouseVotesData extends Parser{
    
    @Override
    void removeID() {} // No IDs to remove
    
    @Override
    void moveClass(){// Moves the classification value to the end of the list
        for(int i = 0; i < data.size(); i++){
            String temp = data.get(i).get(0);
            data.get(i).remove(0);
            data.get(i).add(temp);
        }
    }
    
    @Override
    void handleMissingVal(){}// Does nothing because no missing data
    
    @Override
    String fileName(){ // Returns the data file name
        return "house-votes-84.data.txt";
    }

    @Override
    void discretize() {} //No need to data bin 

    @Override
    void attributeRange() {
     for (int i = 0; i < data.get(1).size() - 1; i++) { //iterate through all of the attributes

            ArrayList<String> values = new ArrayList<>();

                String s1 = "y";
                String s2 = "n";
                values.add(s1);
                values.add(s2);
            
            attRange.add(values);
        }
    }
}
