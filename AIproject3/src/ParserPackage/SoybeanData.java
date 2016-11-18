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
    void handleMissingVal() {
    }// Changes missing values to null

    @Override
    void moveClass() {
    } // Classification value is already in the correct position

    @Override
    void discretize() {
    } //No need to data bin

    @Override
    public String fileName() { // Returns the data file name
        return "soybean-small.data.txt";
    }

    @Override
    void attributeRange() {
        for (int i = 0; i < data.get(1).size() - 1; i++) { //iterate through all of the attributes

            ArrayList<String> values = new ArrayList<>();

            if (i == 34 || i == 25 || i == 23 || i == 17 || i == 14 || i == 13 || i == 12 || i == 9 || i == 8 || i == 7 || i == 3 || i == 2) {
                for (int j = 0; j <= 2; j++) { //these attributes only have values 0-2
                    String s = Integer.toString(j);
                    values.add(s);
                }
            } else if (i == 27 || i == 21 || i == 20 || i == 6 || i == 5) {
                for (int j = 0; j <= 3; j++) { //these attributes only have values 0-3
                    String s = Integer.toString(j);
                    values.add(s);
                }
            } else if (i == 28) {
                for (int j = 0; j <= 4; j++) { //these attributes only have values 0-4
                    String s = Integer.toString(j);
                    values.add(s);
                }
            } else if (i == 0) {
                for (int j = 0; j <= 6; j++) { //these attributes only have values 0-6
                    String s = Integer.toString(j);
                    values.add(s);
                }
            } else {
                for (int j = 0; j <= 1; j++) { //these attributes only have values 0-1
                    String s = Integer.toString(j);
                    values.add(s);
                }
            }
            attRange.add(values);
        }
    }
}
