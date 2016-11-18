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
package Algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math;
import java.util.Random;

/**
 *
 * @author Ben
 */
public class TreeAugmentedNB implements ILearningAlgorithm {

    private ArrayList<ArrayList<String>> trainingData;
    private ArrayList<ArrayList<String>> testData;

    private HashMap<String, Integer> jointCondi; //condition joint probability given a class 
    private ArrayList<String> classList;
    private HashMap<String, Integer> numClass;
    private int root;

    public TreeAugmentedNB() {
        trainingData = new ArrayList<>(); //Initializes the training data list
        testData = new ArrayList<>(); //Initializes the testing data list
        classList = new ArrayList<>();
        jointCondi = new HashMap(); //hold the data for condition joint probability
        numClass = new HashMap();
    }

    @Override
    public void trainAlgorithm(ArrayList<ArrayList<String>> data) {
        trainingData = data;
        findJointCondi();
        findClasses();
    }

    @Override
    public ArrayList<String> testAlgorithm(ArrayList<ArrayList<String>> data) {
        ArrayList<String> classResult = new ArrayList<>();
        testData = data;

        for (ArrayList<String> testData1 : testData) {
            classResult.add(TANclass(testData1));
        }
//        System.out.println("Classification List: ");
//        for (String c : classResult) {
//            System.out.println(c);
//        }
        return classResult;
    }

    private void findJointCondi() {
        for (ArrayList<String> trainingData1 : trainingData) {
            for (int j = 0; j < trainingData1.size(); j++) {
                for (int k = j + 1; k < trainingData1.size(); k++) {
                    String key = j + "," + trainingData1.get(j) + "," + k + "," + trainingData1.get(k) + "," + trainingData1.get(trainingData1.size() - 1); //Key keeps track of the attribute and the value for two items occuring at the same time and the resulting class
                    if (jointCondi.get(key) != null) { //Increments the number of times this attribute and class have occured together.
                        jointCondi.put(key, jointCondi.get(key) + 1);
                    } else {
                        jointCondi.put(key, 1); //Takes care of the case when there is a null value at the specified place in the hashtable
                    }
//                    System.out.println("Key: " + key);
//                    System.out.println(jointCondi.get(key));
                    key = k + "," + trainingData1.get(k) + "," + j + "," + trainingData1.get(j) + "," + trainingData1.get(trainingData1.size() - 1);
                    if (jointCondi.get(key) != null) { //Increments the number of times this attribute and class have occured together.
                        jointCondi.put(key, jointCondi.get(key) + 1);
                    } else {
                        jointCondi.put(key, 1); //Takes care of the case when there is a null value at the specified place in the hashtable
                    }
                }
            }
        }
    }

    private void findClasses() { //Creates a set of the classification in the data set
        trainingData.stream().filter((trainingData1) -> (!inList(trainingData1.get(trainingData1.size() - 1)))).forEach((trainingData1) -> {
            classList.add(trainingData1.get(trainingData1.size() - 1));
        });
    }

    private boolean inList(String target) { //Check if the target is in the classList
        if (numClass.get(target) != null) { // counts up the occurances of each class
            numClass.put(target, numClass.get(target) + 1);
        } else {
            numClass.put(target, 1); //Takes care of the case when there is a null value at the specified place in the hashtable
        }

        if (classList.stream().anyMatch((classList1) -> (target.equals(classList1)))) {
            return true;
        }
        return false;
    }

    private String TANclass(ArrayList<String> dataFragment) {  //dataFragment is one line of attributes in the data
        String classification = null; //The current most likely class
        double highestProb = -1; // The probability of the most likely classification

        for (int i = 0; i < classList.size(); i++) {
            double[][] graph = new double[dataFragment.size() - 1][dataFragment.size() - 1]; //Holds weights and if it is marked as used
            double[] prob = new double[dataFragment.size() - 1];
            for (int row = 0; row < graph.length; row++) {
                double x;
                if (jointCondi.get(row + "," + dataFragment.get(row) + "," + (dataFragment.size() - 1) + "," + classList.get(i) + "," + classList.get(i)) == null) {
                    x = 0.0001;
                    //System.out.println(row + "," + dataFragment.get(row) + "," + (dataFragment.size()-1) + "," + classList.get(i)+ "," + classList.get(i));
                    //System.out.println("Null (2)");
                } else {
                    x = jointCondi.get(row + "," + dataFragment.get(row) + "," + (dataFragment.size() - 1) + "," + classList.get(i) + "," + classList.get(i)); //P(x|c)
                }
                prob[row] = x;

                for (int col = 0; col < graph.length; col++) {
                    if (row == col) {
                        graph[row][col] = 0; // Of there is no edge
                        //graph[row][col][1] = 1; //Flags it as marked
                    } else {
                        //System.out.println(trainingData.size());
                        double classProb = (double) (numClass.get(classList.get(i))) / (trainingData.size()); //P(c)
                        double jointCond;
                        double y;

                        if (jointCondi.get(row + "," + dataFragment.get(row) + "," + col + "," + dataFragment.get(col) + "," + classList.get(i)) == null) {
                            jointCond = 0.0001;
                            //System.out.println(row + "," + dataFragment.get(row) + "," + col + "," + dataFragment.get(col) + "," + classList.get(i));
                            //System.out.println("Null (1)");
                        } else {
                            jointCond = jointCondi.get(row + "," + dataFragment.get(row) + "," + col + "," + dataFragment.get(col) + "," + classList.get(i)); //P(x,y|c)
                        }

                        if (jointCondi.get(col + "," + dataFragment.get(col) + "," + (dataFragment.size() - 1) + "," + classList.get(i) + "," + classList.get(i)) == null) {
                            y = 0.0001;
                            //System.out.println(col + "," + dataFragment.get(col) + "," + (dataFragment.size()-1) + "," + classList.get(i)+ "," + classList.get(i));
                            //System.out.println("Null (3)");
                        } else {
                            y = jointCondi.get(col + "," + dataFragment.get(col) + "," + (dataFragment.size() - 1) + "," + classList.get(i) + "," + classList.get(i)); //P(y|c)
                        }
                        //System.out.println("ClassP: " + classProb);
                        //System.out.println("jointCond: " + jointCond);
                        //System.out.println("X: " + (x / numClass.get(classList.get(i))));
                        //System.out.println("Y: " + y);

                        graph[row][col] = classProb * jointCond * (jointCond / (x * y)); //Weights the edge based on the conditional mutual information equation
                        //graph[row][col][1] = 0; //Flags it as unmarked
                    }
                    //System.out.print(graph[row][col][0] + ", ");
                }
                //graph[row][graph.length][0] = x; //Holds onto the P(Attribute|Class)
                //graph[row][graph.length][1] = 1;
                //System.out.println();   
            }
            double classificationVal = calcProbability(maxDirectedSpan(graph), prob);
            if(classificationVal > highestProb){
                highestProb = classificationVal;
                classification = classList.get(i);
            }
        }
        return classification;
    }

    private double calcProbability(double[][] graph, double[] prob) {
        double classProb = 0;
        for(int i = 0; i < graph.length; i++){
            classProb += pathProb(graph, i, prob);
        }
        return classProb;
    }

    private double pathProb(double[][] graph, int root, double[] prob) {
        double pathP = 0;
        double highestP = prob[root];
        for (int i = 0; i < graph.length; i++) {
            if (graph[root][i] > 0) {
                pathP = pathProb(graph, i, prob);
                if(pathP + graph[root][i] > highestP){
                    highestP = pathP + graph[root][i];
                }
            }
        }
        return highestP;
    }

    private double[][] maxDirectedSpan(double[][] graph) {
        double weightArray[][] = graph;
        int visited[] = new int[graph.length];
        double dist[] = new double[graph.length];
        int path[] = new int[graph.length];
        int verticeCount = graph.length;
        int current, total;
        double maxcost;

        for (int i = 0; i < verticeCount; i++) {
            path[i] = visited[i] = 0;
            dist[i] = -1;
        }

        current = 0;
        dist[current] = 0;
        total = 1;
        visited[current] = 1;
        while (total != verticeCount) {
            for (int i = 0; i < verticeCount; i++) {
                if (weightArray[current][i] != 0) {
                    if (visited[i] == 0) {
                        if (dist[i] < weightArray[current][i]) {
                            dist[i] = weightArray[current][i];
                            path[i] = current;
                        }
                    }
                }
            }
            maxcost = -1;
            for (int i = 0; i < verticeCount; i++) {
                if (visited[i] == 0) {
                    if (dist[i] > maxcost) {
                        maxcost = dist[i];
                        current = i;
                    }
                }
            }
            visited[current] = 1;
            total++;
        }

        maxcost = 0;
        for (int i = 0; i < verticeCount; i++) {
            maxcost = maxcost + dist[i];
        }

//        System.out.print("\n Maximum cost = " + maxcost);
//        System.out.print("\n Maximum Spanning tree is ");

        double[][] maxSpan = new double[graph.length][graph.length];
        for (int i = 0; i < verticeCount; i++) {
//            System.out.print("\n vertex " + i + " is connected to " + path[i] + " by distance: " + dist[i]);
            maxSpan[i][path[i]] = dist[i];
            maxSpan[path[i]][i] = dist[i];
        }

//        System.out.println();
//        for (int i = 0; i < verticeCount; i++) {
//            for (int j = 0; j < verticeCount; j++) {
//                System.out.print(maxSpan[i][j] + ", ");
//            }
//            System.out.println();
//        }
        return directGraphHelper(maxSpan);
    }

    private double[][] directGraphHelper(double[][] graph) {
        Random r = new Random();
        this.root = r.nextInt(graph.length);
        return directGraph(graph, root);
    }

    private double[][] directGraph(double[][] graph, int root) {
        for (int i = 0; i < graph.length; i++) {
            if (graph[root][i] > 0) {
                graph[i][root] = 0;
                graph = directGraph(graph, i);
            }
        }
        return graph;
    }
}

//    private void primAlgorithm(double[][] graph) {
//        double weightArray[][] = graph;
//        int visited[] = new int[graph.length];
//        double d[] = new double[graph.length];
//        int p[] = new int[graph.length];
//        int verticeCount = graph.length;
//        int current, total;
//        double mincost;
//
//        for (int i = 0; i < verticeCount; i++) {
//            p[i] = visited[i] = 0;
//            d[i] = 32767;
//        }
//
//        current = 1;
//        d[current] = 0;
//        total = 1;
//        visited[current] = 1;
//        while (total != verticeCount) {
//            for (int i = 0; i < verticeCount; i++) {
//                if (weightArray[current][i] != 0) {
//                    if (visited[i] == 0) {
//                        if (d[i] > weightArray[current][i]) {
//                            d[i] = weightArray[current][i];
//                            p[i] = current;
//                        }
//                    }
//                }
//            }
//            mincost = 32767;
//            for (int i = 0; i < verticeCount; i++) {
//                if (visited[i] == 0) {
//                    if (d[i] < mincost) {
//                        mincost = d[i];
//                        current = i;
//                    }
//                }
//            }
//            visited[current] = 1;
//            total++;
//        }
//
//        mincost = 0;
//        for (int i = 0; i < verticeCount; i++) {
//            mincost = mincost + d[i];
//        }
//
//        System.out.print("\n Minimum cost=" + mincost);
//        System.out.print("\n Minimum Spanning tree is");
//
//        for (int i = 0; i < verticeCount; i++) {
//            System.out.print("\n vertex " + i + " is connected to " + p[i]);
//        }
//    }




// P(x,y|c) = joint (x,y,c)/#of c
