package Algorithms;

import java.util.ArrayList;
import java.util.Random;

public class DecisionTree {

    ArrayList<ArrayList<String>> subTraining;
    private ArrayList<ArrayList<String>> test;  // the testing set
    private ArrayList<ArrayList<String>> prune;  // the pruning set
    private ArrayList<ArrayList<String>> attRange; //list of possible values for each attribute
    private ArrayList<String> result;            // the final categorization
    private ArrayList<String> classes;          //list of all of the potential classifications
    private ArrayList<Integer> classesCount;
    private double pruneAccuracy;

    public double prunePerc = .25; //percentage of training data for pruning, must be < 1
    private Tree dt;

    public DecisionTree() {
        subTraining = new ArrayList<ArrayList<String>>();
        test = new ArrayList<ArrayList<String>>();
        prune = new ArrayList<ArrayList<String>>();
        attRange = new ArrayList<ArrayList<String>>();
        result = new ArrayList<>();
        classes = new ArrayList<>();
        
        //create decision tree
        //prune tree with pruning data
    }

    public void trainAlgorithm(ArrayList<ArrayList<String>> training) {
        //get a list of the classifications
        getClasses(training);
        initializeClassesCount();
        
        //setting up the pruning data
        double pruningAmount = prunePerc * training.size();
        //get number of prune data from training
        Random rand = new Random();
        for (int i = 0; i <= pruningAmount; i++) {
            //randomly pick it from the training data
            int index = rand.nextInt(training.size());
            prune.add(training.get(index));
            training.remove(index);
        }

        //save the subsetTraining data
        this.subTraining = training;

        //make copies for creating the tree
        ArrayList<ArrayList<String>> aRangeCopy = new ArrayList<>();
        copyAttRange(aRangeCopy);
        ArrayList<ArrayList<String>> trainCopy = new ArrayList<>();
        copyTrain(trainCopy, subTraining);

        dt = createTree(aRangeCopy, trainCopy, trainCopy);

        pruneTree();
    }

    public void pruneTree() {
        //calculate the accuracy of the original decision tree with the pruning set
        pruneAccuracy = checkAccuracy(dt);

        //consider each node for pruning
        ArrayList<Tree> stack = new ArrayList<>();
        stack.add(dt);
        boolean harmful = false; 
        //while it is not harmful to continue pruning
        while (!harmful) {
            harmful = true;
            //check each node for pruning
            while (!stack.isEmpty()) {

                Tree t = stack.remove(0);
                for (Tree child : t.getChildren()) {
                    if (child.getIsAttribute()) {
                        //find majority class for this subtree
                        countClasses(child);
                        int majIndex = findMajority(classesCount);
                        classCountReset();
                        
                        //set that class as that new location
                        child.setOldData(child.getData());
                        child.setOldChildren(child.getChildren());
                        child.setIsAttribute(false);
                        child.setData(classes.get(majIndex));
                        child.setChildren(new ArrayList<>());

                        //test tree's accuracy on pruning set
                        double accuracy = checkAccuracy(dt);
                        if (accuracy < pruneAccuracy) {
                            //if accuracy was worse, revert

                            child.setData(child.getOldData());
                            child.setChildren(child.getOldChildren());
                            child.setIsAttribute(true);
                        } else {
                            harmful = false;
                            pruneAccuracy = accuracy;
                        }
                    }
                    stack.add(child);
                }
            }
        }

    }

    public void countClasses(Tree t) {
        //count classes in given tree
        ArrayList<Tree> stack = new ArrayList<>();
        stack.add(t);

        while (!stack.isEmpty()) {
            Tree tr = stack.remove(0);
            for (Tree child : tr.getChildren()) {

                //if at a leaf node
                if (child.getChildren().isEmpty()) {
                    //find the classification
                    for (int i = 0; i < classes.size(); i++) {
                        if (child.getData().equals(classes.get(i))) {
                            
                            int amount = classesCount.get(i) + 1;
                            classesCount.set(i, amount);
                            break;
                        }
                    }
                } 
                stack.add(child);
            }
        }

    }

    public void classCountReset() {
        for (int i = 0; i < classesCount.size(); i++) {
            classesCount.set(i, 0);
        }
    }

    public double checkAccuracy(Tree t) {
        double correct = 0.0;
        int last = prune.get(0).size() - 1;
        //use the pruning set
        for (int i = 0; i < prune.size(); i++) {
            String s = null;
            Tree current = t;

            do { //continue until leaf node
                if (current.getIsAttribute()) { //check if the node was marked as an attribute node
                    int index = Integer.parseInt(current.getData()); //get the attribute number
                    String value = prune.get(i).get(index); //get the value from the test list

                    for (int j = 0; j < current.getChildren().size(); j++) {
                        if (current.getChild(j).getData().equals(value)) { //find the child that contains that value
                            current = current.getChild(j);//move to that node
                            break;
                        }
                    }
                } else { //if the node is a value node there will only be one child node
                    current = current.getChild(0);
                }
            } while (!current.getChildren().isEmpty());
            s = current.getData();
            if (s.equals(prune.get(i).get(last))) { //if the classification was correct
                correct = correct + 1;
            }
        }
        double accuracy = correct / ((double) prune.size());
        System.out.println("accuracy " + accuracy);
        return accuracy;
    }

    public int findMajority(ArrayList<Integer> classCount) {
        int index = 0;
        int max = 0;
        for (int i = 0; i < classCount.size(); i++) {
            if (classCount.get(i) > max) {
                max = classCount.get(i);
                index = i;
            } else if (classCount.get(i) == max) { //choose randomly if they are the same
                if (Math.random() < .5) {
                    index = i;
                }
            }
        }
        return index;
    }

    public ArrayList<String> testAlgorithm(ArrayList<ArrayList<String>> testList) {
        this.test = testList;

        //using the test list follow the tree to find the classification
        for (int i = 0; i < testList.size(); i++) {
            String s = null;
            Tree current = dt;

            do { //continue until leaf node
                if (current.getIsAttribute()) { //check if the node was marked as an attribute node
                    int index = Integer.parseInt(current.getData()); //get the attribute number
                    String value = testList.get(i).get(index); //get the value from the test list

                    for (int j = 0; j < current.getChildren().size(); j++) {
                        if (current.getChild(j).getData().equals(value)) { //find the child that contains that value
                            current = current.getChild(j);//move to that node
                            break;
                        }
                    }
                } else { //if the node is a value node there will only be one child node
                    current = current.getChild(0);
                }
            } while (!current.getChildren().isEmpty());
            s = current.getData();
            result.add(s);
        }

        return result;
    }

    public Tree createTree(ArrayList<ArrayList<String>> attributes, ArrayList<ArrayList<String>> dataList, ArrayList<ArrayList<String>> parentDataList) {

        //first check if the dataList is empty, if so use the parentList to find the class
        if (dataList.isEmpty()) {
            String classification = pluralityValue(parentDataList);
            Tree t = new Tree(classification, classes);

            return t;
            //if the data instances contain only one class return that class
        } else if (allSameClass(dataList)) {
            String classification = pluralityValue(dataList);
            Tree t = new Tree(classification, classes);

            return t;
            //if there are no more attributes
        } else if (attributes.isEmpty()) {
            String classification = pluralityValue(dataList);
            Tree t = new Tree(classification, classes);

            return t;
        }
        //finding the attribute with the largest gain ratio
        int attributeTag = findBestAttribute(attributes, dataList); ///adjust the attributes return
        int aIndex = 0;

        String s = Integer.toString(attributeTag);

        Tree newTree = new Tree(s, classes);
        newTree.setIsAttribute(true);

        //get index of attribute with that tag
        int tagLocation = attributes.get(0).size() - 1;
        for (int i = 0; i < attributes.size(); i++) {
            if (attributes.get(i).get(tagLocation).equals(s)) {
                aIndex = i;
                break;
            }
        }

        //a sublist removing the already used attribute
        ArrayList<ArrayList<String>> subAttributes = new ArrayList<ArrayList<String>>();
        //fill that list
        for (int i = 0; i < attributes.size(); i++) {
            if (i == aIndex) {
                //removed from new list
            } else {
                subAttributes.add(attributes.get(i));
            }
        }

        //go through each of the attribute's values
        for (int i = 0; i < attributes.get(aIndex).size() - 1; i++) {

            //a sublist only containing the given value
            ArrayList<ArrayList<String>> subList = new ArrayList<ArrayList<String>>();

            //fill that list
            for (int j = 0; j < dataList.size(); j++) {
                String val = attributes.get(aIndex).get(i);
                //if the data contains the given value
                if (dataList.get(j).get(aIndex).equals(val)) {
                    subList.add(dataList.get(j));
                }
            }

            //recursively call to get the subtree
            Tree subTree = createTree(subAttributes, subList, dataList);

            //add branch(attriubte) and subtree to tree
            Tree child = new Tree(attributes.get(aIndex).get(i), classes);
            newTree.addChild(child);

            child.addChild(subTree);
        }

        return newTree;
    }


    public boolean allSameClass(ArrayList<ArrayList<String>> dataList) {
        //check if all the instances of data have the same class
        int last = dataList.get(0).size() - 1;
        String c = null;
        for (int i = 0; i < dataList.size(); i++) {
            if (i == 0) {
                c = dataList.get(i).get(last);
            } else if (!c.equals(dataList.get(i).get(last))) {
                return false;
            }
        }
        return true;
    }

    public String pluralityValue(ArrayList<ArrayList<String>> dataList) {
        ArrayList<Integer> classCount = new ArrayList(classes.size());  //a list to hold the count of each occurence of the classes
        int last = dataList.get(0).size() - 1;
        String c = null;
        //for each of the classes count up the occurences
        for (int i = 0; i < classes.size(); i++) {
            int count = 0; //reset our counter
            for (int j = 0; j < dataList.size(); j++) {

                if (dataList.get(j).get(last).equals(classes.get(i))) { //if the neighbor countains the class
                    //count up the occurences of each class                    
                    count++;
                }
            }
            classCount.add(i, count);
        }

        //find the majority class based off our count list
        int majorityIndex = 0;
        int maxCount = 0;
        for (int i = 0; i < classCount.size(); i++) {
            if (classCount.get(i) > maxCount) {
                majorityIndex = i;
                maxCount = classCount.get(i);
            } else if (classCount.get(i) == maxCount) { //choose randomly if they are the same
                if (Math.random() < .5) {
                    majorityIndex = i;

                }
            }
        }
        c = classes.get(majorityIndex);
        //return the classification that wins majority, or randomly among ties

        return c;
    }

    public int findBestAttribute(ArrayList<ArrayList<String>> attributes, ArrayList<ArrayList<String>> dataList) {
        ArrayList<Double> gainRatios = new ArrayList<>();

        //calculate the gain of each of the attributes
        ArrayList<Double> attributeGain = calcGain(attributes, dataList);

        //calculate the split info for each of the attributes
        ArrayList<Double> splitInfo = calcSplitInfo(attributes, dataList);

        //calculate the gain ratio for each of the attributes
        for (int i = 0; i < attributes.size(); i++) {
            if (splitInfo.get(i) != 0) {
                double ag = (double) attributeGain.get(i);
                double si = (double) splitInfo.get(i);
                double gr = (ag) / (si);

                gainRatios.add(gr);
            } else {
                gainRatios.add(0.0);
                ///System.out.println("???");

            }
        }

        //find the attribute with the maximum gainRatio
        int chosenIndex = 0;
        double maxGainR = 0;
        for (int i = 0; i < gainRatios.size(); i++) {
            if (gainRatios.get(i) > maxGainR) {
                chosenIndex = i;
                maxGainR = gainRatios.get(i);
            } else if (gainRatios.get(i) == maxGainR) { //choose randomly if they are the same
                if (Math.random() < .5) {
                    chosenIndex = i;
                    maxGainR = gainRatios.get(i);
                }
            }
        }
        int tagLocation = attributes.get(0).size() - 1;
        String s = attributes.get(chosenIndex).get(tagLocation);
        int attributeTag = Integer.parseInt(s);

        return attributeTag;
    }

    public ArrayList<Double> calcSplitInfo(ArrayList<ArrayList<String>> attributes, ArrayList<ArrayList<String>> dataList) {
        ArrayList<Double> splitInfo = new ArrayList<>(); //splitInfo for all of the attributes

        for (int i = 0; i < attributes.size(); i++) { //iterate through attributes

            double spInfo = 0; //splitInfo given attribute values

            //sum up the occurences of the value in the data partition
            for (int j = 0; j < attributes.get(i).size() - 1; j++) { //iterate through attribute range of values

                int count = 0;

                for (int k = 0; k < dataList.size(); k++) { //iterate through data partition
                    if (dataList.get(k).get(i).equals(attributes.get(i).get(j))) { //if data contains the value we are looking for
                        count++;
                    }
                }

                //calculate the splitInfo given the value, add it to the current attribute's splitInfo
                if (!dataList.isEmpty()) {
                    double c = (double) count;
                    double dl = (double) dataList.size();
                    double p = (c) / (dl);
                    spInfo -= p * log2(p);
                } else {
                    System.out.println("?????????????????????");
                }
            }

            splitInfo.add(spInfo);      //keep track of each attributes splitInfo
        }

        return splitInfo;
    }

    public ArrayList<Double> calcGain(ArrayList<ArrayList<String>> attributes, ArrayList<ArrayList<String>> dataList) {
        double entropyDP;    //current entropy of whole data partition

        ArrayList<Double> attributeGain = new ArrayList<>();

        //calculate entropy of the data partition
        entropyDP = calcEntropy(dataList);

        //calculate entropy as well as gain for each attriubte
        for (int i = 0; i < attributes.size(); i++) { //iterate through attributes
            double gain = 0;
            double entropyAV = 0; //entropy given attribute values
            //sum up the occurences of the value in the data partition

            for (int j = 0; j < attributes.get(i).size() - 1; j++) { //iterate through attribute range of values
                ArrayList<ArrayList<String>> subList = new ArrayList<>();

                int count = 0;

                for (int k = 0; k < dataList.size(); k++) { //iterate through data partition
                    if (dataList.get(k).get(i).equals(attributes.get(i).get(j))) { //if data contains the value we are looking for
                        count++;
                        subList.add(dataList.get(k));
                    }
                }

                //calculate the entropy given the value, add it to the current attribute's entropy
                if (dataList.size() != 0) {
                    double c = (double) count;
                    double dl = (double) dataList.size();
                    entropyAV += ((c) / (dl)) * calcEntropy(subList);
                } else {
                    System.out.println("?????????????????????");
                }
            }

            gain = entropyDP - entropyAV;//calculate gain
            attributeGain.add(gain);    //keep track of each attribute's gain
        }
        return attributeGain;
    }

    public double calcEntropy(ArrayList<ArrayList<String>> dataList) {
        if (dataList.isEmpty()) {
            return 0;
        }
        int last = dataList.get(0).size() - 1; //index to class 
        ArrayList classCount = new ArrayList<>();
        for(int i = 0; i < classes.size();i++){
            classCount.add(0);
        }  //a list to hold the count of each occurence of the classes
        double entropyDP = 0;

        //for each of the classes count up how many times they occurred in the subSet set
        for (int i = 0; i < classes.size(); i++) {
            int count = 0; //reset our counter
            for (int j = 0; j < dataList.size(); j++) {

                if (dataList.get(j).get(last).equals(classes.get(i))) { //if the subSet instance countains the class

                    //count up the occurences of each class                    
                    count++;
                }
            }
            classCount.set(i, count);
        }

        //calculate entropy for whole current data partition
        for (int i = 0; i < classes.size(); i++) {
            if (!dataList.isEmpty()) {
                
                double cc = (double)( (int)classCount.get(i));
                double dl = (double) dataList.size();
                double p = (cc) / (dl); //probability of class occurring
                entropyDP -= p * log2(p);
            }
        }
        return entropyDP;
    }

    private double log2(double n) { //returns log base 2 of a value
        if (n == 0) {
            return 0;
        }
        return Math.log(n) / Math.log(2);
    }
    
    public void initializeClassesCount(){
        classesCount = new ArrayList<>();
        for(int i = 0; i < classes.size();i++){
            classesCount.add(0);
        }
    }

    private void copyAttRange(ArrayList<ArrayList<String>> copy) {
        for (int i = 0; i < attRange.size(); i++) {
            copy.add(attRange.get(i));
        }
    }

    private void copyTrain(ArrayList<ArrayList<String>> copy, ArrayList<ArrayList<String>> subT) {
        for (int i = 0; i < subT.size(); i++) {
            copy.add(subT.get(i));
        }
    }
    public void setAttRange(ArrayList<ArrayList<String>> aRng) {
        //adding an indicator to the attribute's list, as to which attribute it is assigned to
        for (int i = 0; i < aRng.size(); i++) {
            String s = Integer.toString(i);
            aRng.get(i).add(s);
        }

        this.attRange = aRng;

    }
    
    public void printTree(Tree dt) {

        ArrayList<Tree> stack = new ArrayList<>();
        stack.add(dt);

        while (!stack.isEmpty()) {
            Tree t = stack.remove(0);
            for (Tree child : t.getChildren()) {

                if (child.getIsAttribute()) {
                    //find majority class for this subtree
                    System.out.println("attribute " + child.getData());
                } else {
                    System.out.println("child " + child.getData());
                }
                stack.add(child);
            }
        }

    }

    private void getClasses(ArrayList<ArrayList<String>> trainData) { //creates a list of the classes from the training data
        int last = trainData.get(0).size() - 1;
        for (int i = 0; i < trainData.size(); i++) {
            String c = trainData.get(i).get(last);

            if (!classes.contains(c)) {
                classes.add(c);
            }
        }
    }

}
