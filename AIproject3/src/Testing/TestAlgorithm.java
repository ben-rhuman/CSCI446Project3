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

import Algorithms.*;
import ParserPackage.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class automates testing for the four different machine learning
 * techniques used (K-Nearest Neighbor (KNN), Naive Bayes (NB), Tree Augmented
 * Naive Bayes (TAN), and Iterative Dichotomizer 3 (ID3)). It uses 10-fold
 * cross-validation to train and test the algorithms.
 *
 * This testing algorithm partitions each data set, then trains and tests each
 * algorithm on the data set. It does this for each data set included.
 */
public class TestAlgorithm {

    int k = 10; //K-fold value

    //3D arrayLists to hold the partitioned data sets
    private ArrayList<ArrayList<ArrayList<String>>> partitionData;

    public TestAlgorithm() {
        runValidation();
    }

    private void runValidation() {
        partitionData = partition(shuffle(new GlassData().getData()));
        
        List<Integer> loss = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            loss.add(0);
        }
       
        /*
        *algorithm.add(new ID3());
        *Is where the last algorithm goes.
        * 
        */
        ArrayList<ArrayList<String>> trainingData;
        ArrayList<ArrayList<String>> testData;

        for (int i = 0; i < k; i++) { //Current partition for testing
            trainingData = new ArrayList<>();
            testData = new ArrayList<>();
            List<ILearningAlgorithm> algorithm = new ArrayList<>(4);
            algorithm.add(new KNearestNeighbor());
            algorithm.add(new NaiveBayes());
            algorithm.add(new TreeAugmentedNB());
            
            for (int j = 0; j < k; j++) {
                for (int k = 0; k < partitionData.get(j).size(); k++) {
                    if (i == j) { //If j is the current testing partition
                        testData.add(partitionData.get(j).get(k));
                    } else{
                        trainingData.add(partitionData.get(j).get(k));
                    }
                }
            }
            
            for(int j = 0; j < algorithm.size(); j++){
                train(algorithm.get(j), trainingData);
                loss.set(j, calcLoss(test(algorithm.get(j), testData), testData) + loss.get(j));
            }          
        }
        System.out.println("KNN Loss: " + loss.get(1));
    }
    
    private Integer calcLoss(ArrayList<String> classes, ArrayList<ArrayList<String>> testData){
        return 1;
    }

    private ArrayList partition(ArrayList<ArrayList<String>> data) { //Breaks the data into 10 partitions
        int partSize = data.size() / k; //Size of each partition
        ArrayList<ArrayList<ArrayList<String>>> partData = new ArrayList<>(10);
        int low = 0;
        for (int i = 1; i < k; i++) {
            partData.add(new ArrayList());
            for (int j = low; j < i * partSize; j++) {
                partData.get(i - 1).add(data.get(j)); //Puts the sublist in the partition list
            }
            low += partSize;
        }
        partData.add(new ArrayList());
        System.out.println(partData.size());
        for (int j = low; j < data.size() - 1; j++) {
            partData.get(k - 1).add(data.get(j)); //Puts the remaining data in the last partition if the data is not evenly dividable
        }

//        for (int i = 0; i < partData.size(); i++) {
//            System.out.println("Partition " + i + ":");
//            for (int j = 0; j < partData.get(i).size(); j++) {
//                for(int k = 0; k < partData.get(i).get(j).size(); k++){
//                System.out.print(partData.get(i).get(j).get(k) + ", ");
//                }
//                System.out.println("");
//            }
//            System.out.println("");
//        }
        return partData;
    }

    private ArrayList shuffle(ArrayList<ArrayList<String>> data) {
        Random r = new Random();
        ArrayList temp;
        int indexOne = 0;
        int indexTwo = 0;

        for (int i = 0; i < data.size(); i++) { //Shuffles it data.size() times
            indexOne = r.nextInt(data.size());
            indexTwo = r.nextInt(data.size());
            temp = data.get(indexOne);
            data.set(indexOne, data.get(indexTwo));
            data.set(indexTwo, temp);
        }
        return data;
    }

    private void train(ILearningAlgorithm al, ArrayList<ArrayList<String>> data) {
        al.trainAlgorithm(data);
    }

    private ArrayList test(ILearningAlgorithm al, ArrayList<ArrayList<String>> data) {
        return al.testAlgorithm(data);
    }
}
