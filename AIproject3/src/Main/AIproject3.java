package Main;

import Algorithms.NaiveBayes;
import Algorithms.ILearningAlgorithm;
import ParserPackage.GlassData;
import ParserPackage.Parser;
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
//        Parser g = new GlassData();   

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


////---------------- just for testing
//        ArrayList<String> s = new ArrayList<>(); //results
//           
//        KNearestNeighbor kn = new KNearestNeighbor();
//        
//        kn.trainAlgorithm(b.split(true)); //start training
//        s = kn.testAlgorithm(b.split(false)); //send the testing data
//       
//        for(int i = 0; i < s.size(); i++){
//            System.out.print(s.get(i) + ",");
//            if((i+1)%10 == 0){
//                System.out.println("");
//            }
//        }
////-------------------
    }
}
