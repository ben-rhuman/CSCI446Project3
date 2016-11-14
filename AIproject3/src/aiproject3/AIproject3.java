package aiproject3;

import aiproject3.Algorithms.*;
import aiproject3.ParserPackage.*;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author k28h885
 */
public class AIproject3 {

    public static void main(String[] args) {
        Parser g = new GlassData();   
//        Parser g = new BreastCancerData();     
//        Parser g = new HouseVotesData();      
//        Parser g = new IrisData();
//        Parser g = new SoybeanData();

        ILearningAlgorithm A = new NaiveBayes();
        A.trainAlgorithm(g.getData());
        
        
        ArrayList<ArrayList<String>> data = g.getData();
        ArrayList<ArrayList<String>> testData = new ArrayList<>();
        for(int i = 0; i < data.size(); i++){    
            testData.add(new ArrayList<>());
            for(int j = 0; j < data.get(i).size() - 1; j++){
                testData.get(i).add(data.get(i).get(j));
            }
        }
        
        A.testAlgorithm(testData);
    }
}
