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
public class IrisData extends Parser {

    //catetgories:
    // 1 - Iris Setosa
    // 2 - Iris Versicolour
    // 3 - Iris Virginica
    @Override
    void convertToNum() { // Converts the String data into floating point data
        for (int i = 0; i < group.size(); i++) {
            
            ArrayList<Double> list = new ArrayList<Double>();

            for (int j = 0; j < group.get(i).size(); j++) {
                String s = group.get(i).get(j); //getting the first string
                double value;
                if (s.equals("Iris-setosa")) {
                    value = 1;
                } else if (s.equals("Iris-versicolor")) {
                    value = 2;
                } else if (s.equals("Iris-virginica")) {
                    value = 3;
                } else {
                    value = Double.parseDouble(s); //converting to a double
                }
                list.add(value);

            }
            data.add(list);
        }
    }
    /////////fix data

    @Override
    void removeID() {
    } //Does nothing since no IDs to remove

    @Override
    void moveClass() {
    } // Classification value is already in the correct position

    @Override
    String fileName() { // Returns the data file name
        return "/data/iris.data.txt";
    }
}
