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
public class NaiveBayes implements ILearningAlgorithm {

    private ArrayList<ArrayList<String>> trainingData;
    private ArrayList<ArrayList<String>> testData;
    
    public NaiveBayes(){
        trainingData = new ArrayList<ArrayList<String>>(); 
        testData = new ArrayList<ArrayList<String>>(); 
    }
    
    @Override
    public void trainAlgorithm(ArrayList<ArrayList<String>> data) {
        
    }

    @Override
    public ArrayList<String> testAlgorithm(ArrayList<ArrayList<String>> data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
