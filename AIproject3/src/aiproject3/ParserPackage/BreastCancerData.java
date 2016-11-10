/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aiproject3.ParserPackage;

import java.util.ArrayList;

/**
 *
 * @author k28h885
 */
public class BreastCancerData extends Parser {

    @Override
    void convertToNum() { // Converts the String data into floating point data
        for (int i = 0; i < group.size(); i++) {
            
            ArrayList<Double> list = new ArrayList<Double>();
            for (int j = 0; j < group.get(i).size(); j++) {
                String s = group.get(i).get(j); //getting the first string
                Double value;
                if (s.equals("?")) {
                    value = null;
                   
                } else {
                    value = Double.parseDouble(s); //converting to a double
                   
                }
                list.add(value);
            }
            data.add(list);
        }
    }

    @Override
    void removeID() { // Removes the identification number from the data set
        for (int i = 0; i < group.size(); i++) {
            group.get(i).remove(0);
        }
    }

    @Override
    void moveClass() {
    } // Classification value is already in the correct position

    @Override
    String fileName() { // Returns the data file name
        return "/data/breast-cancer-wisconsin.data.txt";
    }
}
