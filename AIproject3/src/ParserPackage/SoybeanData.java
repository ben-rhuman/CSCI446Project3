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
public class SoybeanData extends Parser {

    @Override
    void removeID() {
    } //Does nothing since no IDs to remove

    @Override
    void handleMissingVal(){}// Changes missing values to null
    
    @Override
    void moveClass() {
    } // Classification value is already in the correct position

    @Override
    void discretize() {} //No need to data bin
    
    @Override
    String fileName() { // Returns the data file name
        return "soybean-small.data.txt";
    }
}
