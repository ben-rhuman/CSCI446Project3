package aiproject3.Algorithms;

public class Pair {  //class used to hold the neighbor and its distance

    public int index;
    public double dist; 
    
    public Pair(int index, double dist) {
        this.index = index;
        this.dist = dist;
    }
    
    public void setPair(int i, double d){
        this.index = i;
        this.dist = d;
    }
    
    public void setIndex(int i){
        this.index = index;

    }
    
    public void SetDistance(int d){
     dist = d;   
    }

}