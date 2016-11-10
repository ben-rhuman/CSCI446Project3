/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aiproject3.Algorithms;

import java.util.ArrayList;

/**
 *
 * @author k28h885
 */
public interface ILearningAlgorithm {
    public void trainAlgorithm(ArrayList<ArrayList<String>> data); //Trains the algorithm with the provided data partition
    
    public ArrayList<String> testAlgorithm(ArrayList<ArrayList<String>> data); //Returns a list of classifications of the testing set
}
