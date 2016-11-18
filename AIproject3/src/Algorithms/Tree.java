package Algorithms;

import java.util.ArrayList;

public class Tree {

    private String data;    //the attribute this subTree contains
    private ArrayList<Tree> children; //all of its potential values
    private ArrayList<String> classes;
    private String oldData;
    private ArrayList<Tree> oldChildren = new ArrayList<>();
    
    private boolean isAttribute = false; //whether node is an attribute or not

    public Tree(String rootData, ArrayList<String> classes) {
        this.data = rootData;
        children = new ArrayList<Tree>();
        this.classes = classes;
        
    }
    

    public void setIsAttribute(boolean set) {
        isAttribute = set;
    }

    public void addChild(Tree child) {
        children.add(child);
    }

    public String getData() {
        return data;
    }

    public Tree getChild(int index) {
        return children.get(index);
    }

    public ArrayList<Tree> getChildren() {
        return children;
    }

    public boolean getIsAttribute() {
        return isAttribute;
    }

    public void setData(String data){
        this.data = data;
    }
    public void setChildren(ArrayList<Tree> children){
        this.children = children;
    }
    public void setOldData(String data){
        oldData = data;
    }
    public String getOldData(){
        return oldData;
    }
    public void setOldChildren(ArrayList<Tree> oldChildren){
        this.oldChildren = oldChildren;
    }
    public ArrayList<Tree> getOldChildren(){
        return oldChildren;
    }
    
}
