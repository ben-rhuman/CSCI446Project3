package aiproject3;

import aiproject3.Algorithms.KNearestNeighbor;
import aiproject3.ParserPackage.*;
import aiproject3.Algorithms.*;
import java.util.ArrayList;

/**
 *
 * @author k28h885
 */
public class AIproject3 {

    public static void main(String[] args) {
//        Parser g = new GlassData();   
        Parser b = new BreastCancerData();
//        Parser h = new HouseVotesData();      
//        Parser i = new IrisData();
//        Parser s = new SoybeanData();



//---------------- just for testing
        ArrayList<String> s = new ArrayList<>(); //results
           
        KNearestNeighbor kn = new KNearestNeighbor();
        
        kn.trainAlgorithm(b.split(true)); //start training
        s = kn.testAlgorithm(b.split(false)); //send the testing data
       
        for(int i = 0; i < s.size(); i++){
            System.out.print(s.get(i) + ",");
            if((i+1)%10 == 0){
                System.out.println("");
            }
        }
//-------------------
    }
}
