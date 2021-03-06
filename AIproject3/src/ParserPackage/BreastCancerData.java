/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParserPackage;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 * @author k28h885
 */
public class BreastCancerData extends Parser {

    @Override
    void removeID() { // Removes the identification number from the data set
        for (int i = 0; i < data.size(); i++) {
            data.get(i).remove(0);
        }
    }

    @Override
    void handleMissingVal() {// Changes missing values to null
        replaceMissingValue();
    }

    @Override
    void moveClass() {
    } // Classification value is already in the correct position

    @Override
    void discretize() {
    }// No need to data bin

    @Override
    public String fileName() { // Returns the data file name
        return "breast-cancer-wisconsin.data.txt";
    }

    @Override
    void attributeRange() {
        for (int i = 0; i < data.get(1).size() - 1; i++) { //iterate through all of the attributes

            ArrayList<String> values = new ArrayList<>();

            for (int j = 1; j <= 10; j++) { //this data only has values 1-10
                String s = Integer.toString(j);
                values.add(s);
            }
            attRange.add(values);
        }
    }
}
