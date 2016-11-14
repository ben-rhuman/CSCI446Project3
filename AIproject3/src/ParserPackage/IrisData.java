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
public class IrisData extends Parser {

    @Override
    void removeID() {
    } //Does nothing since no IDs to remove

    @Override
    void handleMissingVal(){}// No missing values

    @Override
    void moveClass() {
    } // Classification value is already in the correct position

    @Override
    void discretize() { //Using a data binning method to discretize data
        super.discretizeConcrete();
    }    
    
    @Override
    String fileName() { // Returns the data file name
        return "iris.data.txt";
    }
}
