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
    void removeID() { // Removes the identification number from the data set
        for (int i = 0; i < data.size(); i++) {
            data.get(i).remove(0);
        }
    }
    
    @Override
    void handleMissingVal(){// Changes missing values to null
        replaceMissingValue();
    }

    @Override
    void moveClass() {
    } // Classification value is already in the correct position

    @Override
    String fileName() { // Returns the data file name
        return "breast-cancer-wisconsin.data.txt";
    }
}
