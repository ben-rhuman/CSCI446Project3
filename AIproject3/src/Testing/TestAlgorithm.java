/*
 * Copyright (C) 2016 Ben Rhuman
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Testing;

import ParserPackage.*;
import java.util.ArrayList;

/**
 * This class automates testing for the four different
 * machine learning techniques used (K-Nearest Neighbor (KNN), Naive Bayes (NB),
 * Tree Augmented Naive Bayes (TAN), and Iterative Dichotomizer 3 (ID3)).
 * It uses 10-fold cross-validation to train and test the algorithms.
 * 
 * This testing algorithm partitions each data set, then trains and tests 
 * each algorithm on the data set. It does this for each data set included.
 */
public class TestAlgorithm {
    
    //3D arrayLists to hold the partitioned data sets
    private ArrayList<ArrayList<ArrayList<String>>> partitionData; 

    
    public TestAlgorithm(){
        partitionData = partition(shuffle(new GlassData().getData()));
    }
    
    private ArrayList partition(ArrayList<ArrayList<String>> data){ //Breaks the data into 10 partitions
        
    }
    
    private ArrayList shuffle(ArrayList<ArrayList<String>> data){
        
    }
    
    private void train(){
        
    }
    
    private void test(){
        
    }
}
