package Algorithms;

import java.util.ArrayList;

public class KNearestNeighbor implements ILearningAlgorithm{

    private ArrayList<ArrayList<String>> training = new ArrayList<ArrayList<String>>();// the training set
    private ArrayList<ArrayList<String>> test = new ArrayList<ArrayList<String>>(); // the testing set
    private ArrayList<String> result = new ArrayList<>();                     // the final categorizations
    private ArrayList<String> classes = new ArrayList<>();
    private int last = 0; //get the index of the class, the last value
    private int k = 3; //number of k neighbors - tuned to 3, based on resulting loss factors

    MinHeap heap;

    public KNearestNeighbor() {

    }

    public void trainAlgorithm(ArrayList<ArrayList<String>> training) {
        this.training = training;
        last = training.get(0).size() - 1;
        getClasses();               //get a list of the classes

    }

    public ArrayList<String> testAlgorithm(ArrayList<ArrayList<String>> testList) {
        this.test = testList;

        String c;
        for (int i = 0; i < test.size(); i++) {
            heap = new MinHeap(training.size());
            c = findNeighborsDist(test.get(i));
            result.add(c);

        }

        return result;
    }

    private String findNeighborsDist(ArrayList<String> t) {
        String classification = null;

        for (int i = 0; i < training.size(); i++) {  //find the distance of each neighbor
            double dist = 0;

            for (int j = 0; j < t.size() - 1; j++) {    //start by going through each attribute

                String testVal = t.get(j);
                String trainVal = training.get(i).get(j);
                dist += valueDistanceMetric(j, testVal, trainVal); //find the distance of the attribute values

            }
            //find distance of all neighbors put them in min heap
            Pair p = new Pair(i, dist);
            heap.insert(p);

        }
        heap.minHeap(); //minimize heap from the root

        //get classification from minimum k neighbors from heap
        classification = kNeighborsClass();

        return classification;
    }

    private String kNeighborsClass() {
        ArrayList<Pair> kNeighbors = new ArrayList<>(); //a list of the neighbor instances
        ArrayList<Integer> classCount = new ArrayList(classes.size());  //a list to hold the count of each instance of the neighbor
        String classification = null;

        for (int i = 0; i < k; i++) {   //add the minimal distance neighbors to a list
            kNeighbors.add(heap.remove());
        }

        for (int i = 0; i < classes.size(); i++) {  //for each of the classes count up how many of the neighbors contain each class
            int count = 0; //reset our counting
            for (int j = 0; j < kNeighbors.size(); j++) {
                int index = kNeighbors.get(j).index;
                if (training.get(index).get(last).equals(classes.get(i))) { //if the neighbor countains the class

                    //count up the occurences of each class                    
                    count++;
                }
            }
            classCount.add(i, count);
        }

        int majorityIndex = 0;

        for (int i = 0; i < classCount.size(); i++) { //find the majority class based off our count list
            if (classCount.get(i) > majorityIndex) {
                majorityIndex = i;
            } else if (classCount.get(i) == majorityIndex) { //choose randomly if they are the same
                if (Math.random() < .5) {
                    majorityIndex = i;
                }
            }
        }
        classification = classes.get(majorityIndex);
        //return the classification that wins majority, or randomly among ties
        return classification;
    }

    private double valueDistanceMetric(int attribute, String testValue, String trainValue) {
        double distance = 0;
        double difference = 0;
        double countTest = 0;       //occurences of the test value in data 
        double countTrain = 0;      //occurences of the train value in data 
        double countTestC = 0;      //occurences of the test value in data alongside the class
        double countTrainC = 0;     //occurences of the train value in data alongside the class

        for (int i = 0; i < classes.size(); i++) { //for each class we will add up the "distances"
            countTestC = 0;
            countTrainC = 0;
            countTest = 0;
            countTrain = 0;

            for (int j = 0; j < training.size(); j++) {  //count the occurences of the values

                if (training.get(j).get(attribute).equals(testValue)) { //if the testValue occurred
                    countTest++;
                    if (training.get(j).get(last).equals(classes.get(i))) { //if the class is equal to the class we are currently looking at
                        countTestC++;
                    }
                }
                if (training.get(j).get(attribute).equals(trainValue)) { //if the trainValue occurred
                    countTrain++;
                    if (training.get(j).get(last).equals(classes.get(i))) { //if the class is equal to the class we are currently looking at
                        countTrainC++;
                    }
                }
            }
            //---------
            //make the calculation of the distance
            if (countTest != 0) {
                countTest = countTestC / countTest;
            }
            if (countTrain != 0) {
                countTrain = countTrainC / countTrain;
            }
            difference = countTest - countTrain;
            difference = Math.abs(difference);

            distance += difference;
        }

        return distance;
    }

    private void getClasses() { //creates a list of the classes from the training data

        for (int i = 0; i < training.size(); i++) {
            String c = training.get(i).get(last);

            if (!classes.contains(c)) {
                classes.add(c);
            }
        }
    }
    public int getK(){
        return k;
    }
    
    public void setK(int n){
        this.k = n;
    }

    @Override
    public void setAttRange(ArrayList<ArrayList<String>> aRng) {
       
    }
            
}
    
            


