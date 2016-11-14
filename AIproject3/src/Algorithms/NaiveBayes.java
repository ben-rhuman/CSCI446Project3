/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Naive Bayes Machine Learning Algorithm
 * Group 8: Ben Rhuman, Isaac, Sotelo, Danny Kumpf
 *
 * This algorithm uses a training set to aquire prior probabilities used to
 * estimate the probability of a classification based on the testing set.
 */
public class NaiveBayes implements ILearningAlgorithm {

    private ArrayList<ArrayList<String>> trainingData;
    private ArrayList<ArrayList<String>> testData;

    private final ArrayList<HashMap<String, Integer>> priors;
    private ArrayList<String> classList;

    public NaiveBayes() {
        trainingData = new ArrayList<>(); //Initializes the training data list
        testData = new ArrayList<>(); //Initializes the testing data list
        priors = new ArrayList<>(); //Holds the prior probabilities for the training data
        classList = new ArrayList<>();
    }

    @Override
    public void trainAlgorithm(ArrayList<ArrayList<String>> data) {
        trainingData = data;
        findPriors();
        findClasses();
        System.out.println("Class List:");
        classList.stream().forEach((classList1) -> {
            System.out.println(classList1);
        });
    }

    @Override
    public ArrayList<String> testAlgorithm(ArrayList<ArrayList<String>> data) {
        ArrayList<String> classResult = new ArrayList<>();
        testData = data;

        for (ArrayList<String> testData1 : testData) {
            classResult.add(NBclass(testData1));
        }
        System.out.println("Classification List: ");
        for (String c : classResult) {
            System.out.println(c);
        }
        return classResult;
    }

    //Class Specific/Non interface methods
    //For training
    private void findPriors() { // This collects all the data necissary to compute the prior probabilities of the training data
        for (int j = 0; j < trainingData.get(0).size(); j++) {
            priors.add(new HashMap<>());
            for (ArrayList<String> trainingData1 : trainingData) {
                String key = trainingData1.get(j) + "," + trainingData1.get(trainingData1.size() - 1); // Creates a key of type "attributeA" + "ClassA"
                if (priors.get(j).get(key) != null) { //Increments the number of times this attribute and class have occured together.
                    priors.get(j).put(key, priors.get(j).get(key) + 1);
                } else {
                    priors.get(j).put(key, 1); //Takes care of the case when there is a null value at the specified place in the hashtable
                }
                //System.out.println("Key: " + key);
                //System.out.println(priors.get(j).get(key));
            }
        }
    }

    private void findClasses() {
        trainingData.stream().filter((trainingData1) -> (!inList(trainingData1.get(trainingData1.size() - 1)))).forEach((trainingData1) -> {
            classList.add(trainingData1.get(trainingData1.size() - 1));
        });
    }

    private boolean inList(String target) { //Check if the target is in the classList
        if (classList.stream().anyMatch((classList1) -> (target.equals(classList1)))) {
            return true;
        }
        return false;
    }

    //For testing
    private String NBclass(ArrayList<String> dataFragment) {  //dataFragment is one line of attributes in the data
        String classification = null; //The current most likely class
        double highestProb = 0; // The probability of the most likely classification

        for (int i = 0; i < classList.size(); i++) {
            int numClass = priors.get(priors.size() - 1).get(classList.get(i) + "," + classList.get(i));
            double prob = (double) numClass / trainingData.size();

            for (int j = 0; j < dataFragment.size(); j++) {
                String key = dataFragment.get(j) + "," + classList.get(i);
                if (priors.get(j).get(key) != null) {
                    prob *= (priors.get(j).get(key) / (double) numClass);
                } else {
                    prob = 0; //This can be change to reflect a lack of knowledge in the training set
                }
            }
            if (prob > highestProb) { //Replaces the classification with the new highest classification
                highestProb = prob;
                classification = classList.get(i);
            }
        }
        return classification;
    }
}