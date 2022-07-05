/*
 * Node.java
 *
 * Created on November 9, 2003, 12:57 PM
 */

package edu.umiacs.fmm;
import java.util.*;

/**
 *
 * @author  wpwy
 */
public class Box implements Comparable{
    
    int level;
    int index;
    boolean empty;
    Complex[] c;
    Complex[] dtilde;
    Complex[] d;
    Vector<Point> x;
    Vector<Point> y;
    Vector<Box> children;
    Vector<Box> dTreeChildren;
    Vector<Box> cForestChildren;
    Box parent;
    Box dTreeParent;
    Box cForestParent; //either null or same as dTreeParent
    
    int p;
    /** Creates a new instance of Node */
    public Box(int level, int index, int p) {
        this.p=p;
        this.level = level;
        this.index = index;
        empty = true;
        c = null;
        dtilde =  null;
        d =  null;
        x = new Vector<Point>();
        y = new Vector<Point>();
        c = new Complex[p];
        dtilde = new Complex[p];
        d = new Complex[p];
        for (int i=0; i<p; i++){
            c[i] = new Complex(0d);
            dtilde[i] = new Complex(0d);
            d[i] = new Complex(0d);
        }
        children = new Vector<Box>(4);
        dTreeChildren = new Vector<Box>(4);
        cForestChildren = new Vector<Box>(4);
        parent = null;
        dTreeParent = null;
        cForestParent = null;
    }
    public Box getParent(){
        return parent;
    }
    public void setParent(Box b){
        parent = b;
    }
    public Vector<Box> getChildren(){
        return children;
    }
    public boolean hasChildren(){
        return children.size()!=0;
    }
    public void addChild(Box b){
        children.addElement(b);
    }
    public Box getDTreeParent(){
        return dTreeParent;
    }
    public void setDTreeParent(Box b){
        dTreeParent = b;
    }
    public Vector<Box> getDTreeChildren(){
        return dTreeChildren;
    }
    public void addDTreeChild(Box b){
        dTreeChildren.addElement(b);
    }
    public boolean hasDTreeChildren(){
        return dTreeChildren.size()!=0;
    }
    public Box getCForestParent(){
        return cForestParent;
    }
    public void setCForestParent(Box b){
        cForestParent = b;
    }
    public void addCForestChild(Box b){
        cForestChildren.addElement(b); 
    }
    public Vector<Box> getCForestChildren(){
        return cForestChildren;
    }
    public boolean hasCForestChildren(){
        return cForestChildren.size()!=0;
    }
    
    public int getLevel() {
        return level;
    }
    public int getIndex() {
        return index;
    }
    public boolean isEmpty() {
        return empty;
    }
    public Complex[] getC() {
        return c;
    }
    public Complex[] getDtilde() {
        return dtilde;
    }
    public Complex[] getD() {
        return d;
    }
    public void addC(Complex[] inc) {
        //System.out.println("c is length "+c.length+" and inc is length "+inc.length);
        for (int i=0; i<c.length; i++)
            c[i]=c[i].add(inc[i]);
    }
    public void addDtilde(Complex[] inc) {
        for (int i=0; i<dtilde.length; i++)
            dtilde[i]=dtilde[i].add(inc[i]);
    }
    public void addD(Complex[] inc) {
        for (int i=0; i<d.length; i++)
            d[i]=d[i].add(inc[i]);
    }
    public Vector<Point> getX() {
        return x;
    }
    public Vector<Point> getY() {
        return y;
    }
    
    public Vector<Point> getXRecursive(){
        if (!hasChildren())
            return x;
        Vector<Point> ans = new Vector<Point>();
        for (Box child:children){
            ans.addAll(child.getXRecursive());
        }
        return ans;
        
    }
    public Vector<Point> getYRecursive(){
        if (!hasChildren())
            return y;
        
        Vector<Point> ans = new Vector<Point>();
        for (Box child:children){
            ans.addAll(child.getYRecursive());
        }
        return ans;
    }
    
    public void setLevel(int i) {
        this.level=i;
    }
    public void setIndex(int i) {
        this.index = i;
    }
    public void setEmpty(boolean e) {
        this.empty=e;
    }
    public void setC(Complex[] c) {
        this.c=c;
    }
    public void setDtilde(Complex[] dtilde){
        this.dtilde = dtilde;
    }
    public void setD(Complex[] d) {
        this.d=d;
    }
    public void setX(Point[] p) {
        if (p!=null) {
            this.x = new Vector<Point>();
            for (int i=0; i<p.length; i++)
                if (p[i]!=null)
                    this.x.addElement(p[i]);
        }
    }
    public void setX(Vector<Point> p){
        x = new Vector<Point>();
        x.addAll(p);
    }
    public void setY(Point[] p) {
        if (p!=null) {
            this.x = new Vector<Point>();
            for (int i=0; i<p.length; i++)
                if (p[i]!=null)
                    this.y.addElement(p[i]);
        }
    }
    public void setY(Vector<Point> p){
        y = new Vector<Point>();
        y.addAll(p);
    }
    public void addX(Point p) {
        if (p!=null)
            this.x.addElement(p);
    }
    public void addY(Point p) {
        if (p!=null)
            this.y.addElement(p);
    }
    /*public Box getParent(FmmTree t) {
        return t.getBox(level-1, index >> 2);
    }
     
    public Box[] getChildren(FmmTree t) {
        try {
            Box[] ans = new Box[4];
            for (int i=0; i<4; i++){
                ans[i] = t.getBox(level+1, (index<<2)+i);
            }
            return ans;
     
        }
        catch (Exception e){
            return null;
        }
    }
     */
    public double getSize() {
        //diameter
        return Math.pow(2, -level);
    }
    public Point getCenter() {
        return new Point(Util.uninterleave(index, level).add(new Complex(0.5, 0.5)).multiply(getSize()));
    }
    public Vector<Box> getNeighbors(FmmTree t) {
        try{
            Vector<Box> ans = new Vector<Box>();
            Complex tmp = Util.uninterleave(index, level);
            int x = (int)tmp.real();
            int y = (int)tmp.imaginary();
            for (int i=-1; i<=1; i++)
                for (int j=-1; j<=1; j++)
                    if (  (i!=0||j!=0) && x+i>=0 && x+i<Math.pow(2, level) && y+j>=0 && y+j<Math.pow(2, level))
                        ans.addElement( t.getBox(level, Util.interleave(x+i, y+j, level)) );
            //Collections.sort(ans);
            return ans;
        } catch (Exception e){
            return new Vector<Box>();
        }
    }
     public Vector<Box> getNeighbors(FmmTreeNonAdaptive t) {
        try{
            Vector<Box> ans = new Vector<Box>();
            Complex tmp = Util.uninterleave(index, level);
            int x = (int)tmp.real();
            int y = (int)tmp.imaginary();
            for (int i=-1; i<=1; i++)
                for (int j=-1; j<=1; j++)
                    if (  (i!=0||j!=0) && x+i>=0 && x+i<Math.pow(2, level) && y+j>=0 && y+j<Math.pow(2, level))
                        ans.addElement( t.getBox(level, Util.interleave(x+i, y+j, level)) );
            //Collections.sort(ans);
            return ans;
        } catch (Exception e){
            return new Vector<Box>();
        }
    }
    public Vector<Box> getNeighborsE4(FmmTree t) {
        try {
            Vector<Box> myNeighborsVec = getNeighbors(t);
            Vector<Box> myParentsNeighbors = getParent().getNeighbors(t);
            Vector<Box> myParentsNeighborsAtMyLevelVec = new Vector<Box>();
            for (Box parentsNeighbor:myParentsNeighbors){
                for (Box thisChild:parentsNeighbor.getChildren())
                    myParentsNeighborsAtMyLevelVec.addElement(thisChild);
            }
            
            Vector<Box> ans = new Vector<Box>();
            for (int i=0; i<myParentsNeighborsAtMyLevelVec.size(); i++) {
                Box b = (Box)myParentsNeighborsAtMyLevelVec.elementAt(i);
                if (!this.equals(b) && !myNeighborsVec.contains(b))
                    ans.addElement(b);
            }
            
            return ans;
        } catch (Exception e){
            return new Vector<Box>();
        }
    }
    public Vector<Box> getNeighborsE4(FmmTreeNonAdaptive t) {
        try {
            Vector<Box> myNeighborsVec = getNeighbors(t);
            Vector<Box> myParentsNeighbors = getParent().getNeighbors(t);
            Vector<Box> myParentsNeighborsAtMyLevelVec = new Vector<Box>();
            for (Box parentsNeighbor:myParentsNeighbors){
                for (Box thisChild:parentsNeighbor.getChildren())
                    myParentsNeighborsAtMyLevelVec.addElement(thisChild);
            }
            
            Vector<Box> ans = new Vector<Box>();
            for (int i=0; i<myParentsNeighborsAtMyLevelVec.size(); i++) {
                Box b = (Box)myParentsNeighborsAtMyLevelVec.elementAt(i);
                if (!this.equals(b) && !myNeighborsVec.contains(b))
                    ans.addElement(b);
            }
            
            return ans;
        } catch (Exception e){
            return new Vector<Box>();
        }
    }
    public int compareTo(Object o) {
        Box b = (Box)o;
        if (level<b.level)
            return -1;
        else if (level>b.level)
            return 1;
        else if (index<b.index)
            return -1;
        else if (index>b.index)
            return 1;
        else return 0;
    }
    public boolean equals(Object o){
        return compareTo(o)==0;
    }
    public String getLabel(){
        return "("+getLevel()+","+getIndex()+")";
    }
    public String toString() {
        return "Box "+getLabel();
        /*
        String ans =  "box (l="+level+", n="+index+")\n";
        String ansc = "           C: ";
        String ansdt ="      Dtilde: ";
        String ansd = "           D: ";
        for (int i=0; i<c.length; i++) {
            ansc+=c[i].toString()+" ";
            ansdt+=dtilde[i].toString()+" ";
            ansd+=dtilde[i].toString()+" ";
        }
        ans += ansc+"\n"+ansdt+"\n"+ansd+"\n";
        return ans;
         */
    }

    /**
     * Holds value of property inDTree.
     */
    private boolean inDTree=false;

    /**
     * Getter for property inDTree.
     * @return Value of property inDTree.
     */
    public boolean isInDTree() {

        return this.inDTree;
    }

    /**
     * Setter for property inDTree.
     * @param inDTree New value of property inDTree.
     */
    public void setInDTree(boolean inDTree) {

        this.inDTree = inDTree;
    }

    /**
     * Holds value of property inCForest.
     */
    private boolean inCForest =false;

    /**
     * Getter for property inCForest.
     * @return Value of property inCForest.
     */
    public boolean isInCForest() {

        return this.inCForest;
    }

    /**
     * Setter for property inCForest.
     * @param inCForest New value of property inCForest.
     */
    public void setInCForest(boolean inCForest) {

        this.inCForest = inCForest;
    }
    
    
    public boolean isCForestLeaf(){
        return inCForest&&!hasCForestChildren();
    }
    public boolean isDTreeLeaf(){
        return inDTree&&!hasDTreeChildren();
    }
}
