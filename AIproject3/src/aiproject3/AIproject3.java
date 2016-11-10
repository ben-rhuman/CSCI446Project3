package aiproject3;

import aiproject3.ParserPackage.*;
import java.util.ArrayList;

/**
 *
 * @author k28h885
 */
public class AIproject3 {

    public static void main(String[] args) {
        Parser p = new BreastCancerData();
        ArrayList<ArrayList<String>> data = p.getData();

    }
}
