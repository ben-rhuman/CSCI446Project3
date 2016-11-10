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
public class SoybeanData extends Parser {

    @Override
    void convertToNum() { // Converts the String data into floating point data

        //catetgories:
        //1 - D1
        //2 - D2
        //3 - D3
        //4 - D4
        for (int i = 0; i < group.size(); i++) {
            
            ArrayList<Double> list = new ArrayList<Double>();

            for (int j = 0; j < group.get(i).size(); j++) {
                String s = group.get(i).get(j); //getting the first string
                double value;
                if (s.equals("D1")) {
                    value = 1;
                } else if (s.equals("D2")) {
                    value = 2;
                } else if (s.equals("D3")) {
                    value = 3;
                } else if (s.equals("D4")) {
                    value = 4;
                } else {
                    value = Double.parseDouble(s); //converting to a double
                }
                list.add(value);

            }
            data.add(list);
        }
    }

    @Override
    void removeID() {
    } //Does nothing since no IDs to remove

    @Override
    void moveClass() {
    } // Classification value is already in the correct position

    @Override
    String fileName() { // Returns the data file name
        return "/data/soybean-small.data.txt";
    }
}
