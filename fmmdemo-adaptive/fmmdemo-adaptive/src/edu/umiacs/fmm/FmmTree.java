/*
 * Tree.java
 *
 * Created on November 13, 2003, 8:33 AM
 */

package edu.umiacs.fmm;
import java.util.*;
/**
 *
 * @author  wpwy
 */
public class FmmTree{
    public static final int MAX_NUM_LEVEL=8;
    final int dimension = 2;
    //int numOfLevels;
    Vector<Point> x;
    Vector<Point> y;
    Potential potential;
    Vector<Vector<Box>> struct;
    int q;
    public static final int DEFAULT_NUM_LEVEL = 3;
    int currLevel;
    long numTrans;
    long numExp;
    long numDirect;
    
    /** Creates a new instance of Tree */
    public FmmTree() {
        //numOfLevels = DEFAULT_NUM_LEVEL;
        currLevel = DEFAULT_NUM_LEVEL-1;
        x = new Vector<Point>();
        y = new Vector<Point>();
        struct = new Vector<Vector<Box>>();
        potential = new Potential();
    }
    public FmmTree(Vector<Point> x, Vector<Point> y, Potential p){
        //default level of division
        this.x = x;
        this.y = y;
        this.potential = p;
        struct = new Vector<Vector<Box>>();
        //numOfLevels = DEFAULT_NUM_LEVEL;
        initDefaultStruct();
        
    }
    private void initDefaultStruct(){
        for (int i=0; i<DEFAULT_NUM_LEVEL; i++) {
            increaseLevel();
        }
        clearLeafData();
        setLeafData();
    }
    private void clearLeafData(){
        for (Box b:struct.lastElement()) {
            b.setX(new Vector<Point>());
            b.setY(new Vector<Point>());
        }
    }
    private void setLeafData(){
        int numOfLevels = struct.size();
        for (Point p:x)
            struct.lastElement().elementAt(p.getBoxIndex(numOfLevels-1)).addX(p);
        for (Point p:y)
            struct.lastElement().elementAt(p.getBoxIndex(numOfLevels-1)).addY(p);
    }
    
    public static FmmTree build(int th, Point[] x, Point[] y, Potential p){
        Vector<Point> xv = new Vector<Point>();
        Vector<Point> yv = new Vector<Point>();
        for (Point px:x)
            xv.addElement(px);
        for (Point py:y)
            yv.addElement(py);
        return build(th, xv, yv, p);
    }
    public static FmmTree build(int maxClusterThreshold, Vector<Point> x, Vector<Point> y, Potential potential) {
        if (maxClusterThreshold<=0){
            return build(x, y, potential);
        }
        FmmTree ans = new FmmTree(x, y, potential);
        while( !ans.isThreshHoldMet(maxClusterThreshold) && ans.getNumOfLevels()<MAX_NUM_LEVEL) {
            ans.clearLeafData();
            ans.increaseLevel();
            ans.setLeafData();
        }
        
        //now build D tree
        ans.buildDTree(maxClusterThreshold);
        //now build Cforest
        ans.buildCForest();
        ans.setQ(maxClusterThreshold);
        return ans;
    }
    public static FmmTree build(Point[] x, Point[] y, Potential p){
        Vector<Point> xv = new Vector<Point>();
        Vector<Point> yv = new Vector<Point>();
        for (Point px:x)
            xv.addElement(px);
        for (Point py:y)
            yv.addElement(py);
        return build(xv, yv, p);
    }
    public static FmmTree build(Vector<Point> x, Vector<Point> y, Potential p){
        int N = x.size();
        int dl = Math.round((float)(Math.log(N)/Math.log(2)/2));
        int q = Math.round((float)(9*N*Math.pow(2, -dl)));
        //System.out.println("Clustering: "+q);
        return build(q, x, y, p);
    }

    public void setQ(int q){
        this.q=q;
    }
    public int getQ(){
        return q;
    }

    private void buildCForest(){
        int minlevel = 2;
        for (Vector<Box> level:struct){
            for (Box b:level){
                if (b.getLevel()>=minlevel && b.isInDTree()){
                    Vector<Box> nei4 = b.getNeighborsE4(this);
                    for (Box nei4b:nei4){
                        if (nei4b.getXRecursive().size()>0)
                            nei4b.setInCForest(true);
                    }
                }
            }
        }
        for (int i=minlevel; i<struct.size()-1; i++){
            Vector<Box> level = struct.elementAt(i);
            for (Box b:level){
                if (b.isInCForest()){
                    boolean hasChildInForest = false;
                    for (Box child:b.getChildren())
                        if (child.isInCForest())
                            hasChildInForest = true;
                    if (hasChildInForest) {
                        for (Box child:b.getChildren()){
                            child.setCForestParent(b);
                            b.addCForestChild(child);
                        }
                    }
                }
            }
        }
    }
    private void buildDTree(int maxClusterThreshold){
        this.getRoot().setInDTree(true);
        buildDTreeHelper(this.getRoot(), maxClusterThreshold);
    }
    private void buildDTreeHelper(Box root, int th){
        for (Box child:root.getChildren()) {
            child.setInDTree(true);
            child.setDTreeParent(root);
            root.addDTreeChild(child);
            
            int xCount = child.getXRecursive().size();
            for (Box childNeighbor:child.getNeighbors(this))
                xCount+=childNeighbor.getXRecursive().size();
            if ( (xCount>th && child.getYRecursive().size()>0 && child.hasChildren()) ||
                    child.getLevel()==1)
                buildDTreeHelper(child, th);
        }
    }
    private void increaseLevel(){
        int i = struct.size();
        int size = (int)Math.pow(4,i);  // 4 ^ (i-1)+1
        Vector<Box> leveli = new Vector<Box>(size) ;
        for (int j=0; j<size; j++) {
            Box b = new Box(i, j, potential.p);
            Box bParent = getBox(i-1, j >> 2);
            if (bParent!=null){
                b.setParent(bParent);
                bParent.addChild(b);
            }
            leveli.addElement(b);
        }
        struct.addElement(leveli);
    }
    private boolean isThreshHoldMet(int maxClusterThreshold){
        for (Box b:struct.lastElement()){
            int xCount = b.getX().size();
            for (Box bb:b.getNeighbors(this))
                xCount+=bb.getX().size();
            if (xCount>maxClusterThreshold&& b.getYRecursive().size()>0)
                return false;
        }
        return true;
    }
    private void printComplex(Complex[] c){
        for (Complex cc:c)
            System.out.print(cc+" ");
    }

    // S,SS, in CForest
    private void upwardPass(double[] u){
        for (int l=struct.size()-1; l>=2; l--){
            for (Box b:struct.elementAt(l)){
                if (b.isCForestLeaf()){ //black node
                    for(Point px:b.getXRecursive()){
                        Complex[] B = potential.getSCoeff(px.getCoord(), b.getCenter().getCoord());
                        double thisU = u[x.indexOf(px)];
                        for (int k=0; k<B.length; k++) {
                            B[k]=B[k].multiply(thisU);
                        }
                        b.addC(B);
                        numExp++;
                    }
                }
                else if (b.hasCForestChildren()) {//gray node
                    for (Box childB:b.getCForestChildren()) {
                        if (childB.isInCForest()) {
                            Complex from = childB.getCenter().getCoord();
                            Complex to = b.getCenter().getCoord();
                            b.addC(potential.getSS(from, to, childB.getC()));
                            this.numTrans++;
                        }
                        else {//white node... direct expansion
                            for (Point px:childB.getXRecursive()){
                                this.numExp++;
                                Complex[] B = potential.getSCoeff(px.getCoord(), b.getCenter().getCoord());
                                double thisU = u[x.indexOf(px)];
                                for (int k=0; k<B.length; k++) {
                                    B[k]=B[k].multiply(thisU);
                                }
                                b.addC(B);
                                printComplex(B);
                            }
                        }
                    }
                }
                //else white node.  do nothing.
            }
        }
    }
    // SR, in DTree
    private void downwardPass1(){
        for (Box thisBox:struct.elementAt(2)) {
            downwardPass1Helper(thisBox);
        }
    }
    private void downwardPass1Helper(Box thisBox){
        if(thisBox.isInDTree()  && thisBox.getYRecursive().size()>0){
            for (Box thisE4Box:thisBox.getNeighborsE4(this)){
                if (thisE4Box.getXRecursive().size()>0){
                    Complex from = thisE4Box.getCenter().getCoord();
                    Complex to = thisBox.getCenter().getCoord();
                    thisBox.addDtilde( potential.getSR(from, to, thisE4Box.getC()));
                    numTrans++;
                }
            }
            for (Box child:thisBox.getDTreeChildren()) {
                downwardPass1Helper(child);
            }
        }
    }
    // RR, in DTree
    private void downwardPass2(){
        for(Box b:struct.elementAt(2)){
            b.addD(b.getDtilde());
            downwardPass2Helper(b);
        }
    }
    private void downwardPass2Helper(Box thisBox){
        if (thisBox.getYRecursive().size()>0){
            Complex from = thisBox.getCenter().getCoord();
            for (Box child:thisBox.getDTreeChildren()) {
                if (child.getYRecursive().size()>0){
                    Complex to = child.getCenter().getCoord();
                    child.addD( potential.getRR(from, to, thisBox.getD()));
                    child.addD( child.getDtilde());
                    downwardPass2Helper(child);
                    numTrans++;
                }
            }
        }
    }
    // solve tree
    public double[] solve(double[] u) {
        upwardPass(u);
        //System.out.println(this.toString());
        downwardPass1();
        downwardPass2();
        
        double[] v = new double[y.size()];
        solveHelper(getRoot(), v, u);
        /*
         
        //reinitialize struct();
        initStruct();
         */
        //double[] vv = new double[y.size()];
        //System.out.println("V norm: "+Util.getError(vv, v));
        return v;
    }
    // R + NearField
    private void solveHelper(Box thisBox, double[] v, double[] u){
        //System.out.println("solving: "+thisBox);
        if (thisBox.isDTreeLeaf()){
            //System.out.println("is leave");
            for (Point thisY:thisBox.getYRecursive()){
                double regPart = 0d;
                Complex[] d = thisBox.getD();
                Complex[] rVec = potential.getRVector(thisY.getCoord(), thisBox.getCenter().getCoord());
                numExp++;
                for (int k=0; k<d.length; k++){
                    regPart += d[k].multiply(rVec[k]).real();
                }
                
                double sinPart = 0d;
                Vector<Box> nei = thisBox.getNeighbors(this);
                Vector<Box> neiandme = new Vector<Box>(nei);
                neiandme.addElement(thisBox);
                
                for (Box neibox:neiandme){
                    for (Point thisX:  neibox.getXRecursive()) {
                        double thisU = u[x.indexOf(thisX)];
                        double inc = potential.direct(thisY.getCoord(), thisX.getCoord()).multiply(thisU).real();
                        sinPart+=inc;
                        numDirect++;
                    }
                }
                v[y.indexOf(thisY)] = sinPart+regPart;
                //System.out.println("Setting v["+y.indexOf(thisY)+"] to "+(sinPart+regPart));
            }
        }
        
        else {
            //System.out.println("not leave");
            for(Box child:thisBox.getDTreeChildren()) {
                solveHelper(child, v, u);
            }
        }
    }
    // solve direct
    public double[] solveDirect(double[] u) {
        double[] v = new double[y.size()];
        for (int j=0; j<v.length; j++) {
            for (int i=0; i<x.size(); i++) {
                v[j]+=u[i]* potential.direct(y.elementAt(j).getCoord(), x.elementAt(i).getCoord()).real();
                //numOpsDirect++;
            }
        }
        return v;
    }

    public int getClusterThreshold() {
        return this.q;
    }
    public Box getRoot(){
        return struct.firstElement().firstElement();
    }
    public Vector<Vector<Box>> getStruct(){
        return struct;
    }
    public Box getBox(int level, int index) {
        try {
            return struct.elementAt(level).elementAt(index);
        } catch (Exception e){
            return null;
        }
    }
    public int getNumOfLevels(){
        return struct.size();
    }
    public void print(){
        int l=0;
        for (Vector<Box> level : struct){
            System.out.println("########Level "+(l++)+": ");
            for (Box b:level) {
                System.out.println(b.toString());
                System.out.print("X points: ");
                for (Point xp:b.getX())
                    System.out.print(xp.toString()+" ");
                System.out.println();
                System.out.print("Y points: ");
                for (Point yp:b.getY())
                    System.out.print(yp.toString()+" ");
                System.out.println();
                
                System.out.print("C values: ");
                for (Complex c:b.getC()) {
                    System.out.print(c.toString()+" ");
                }
                System.out.println();
                System.out.print("Dtilde values: ");
                for (Complex c:b.getDtilde()) {
                    System.out.print(c.toString()+" ");
                }
                System.out.println();
                System.out.print("D values: ");
                for (Complex c:b.getD()) {
                    System.out.print(c.toString()+" ");
                }
                System.out.println();
            }
            System.out.println("########End Level "+l+"\n");
        }
    }
    public String toString() {
        String ans = "";
        int l=0;
        for (Vector<Box> level : struct){
            ans+="########Level "+(l++)+": "+"\n";
            for (Box b:level) {
                ans+=b.toString()+"\n";
                ans+="X points: ";
                for (Point xp:b.getX())
                    ans+=xp.toString()+" ";
                ans+="\n";
                ans+="Y points: ";
                for (Point yp:b.getY())
                    ans+=yp.toString()+" ";
                ans+="\n";
                
                ans+="C values: ";
                for (Complex c:b.getC()) {
                    ans+=c.toString()+" ";
                }
                ans+="\n";
                ans+="Dtilde values: ";
                for (Complex c:b.getDtilde()) {
                    ans+=c.toString()+" ";
                }
                ans+="\n";
                ans+="D values: ";
                for (Complex c:b.getD()) {
                    ans+=c.toString()+" ";
                }
                ans+="\n";
            }
            ans+="########End Level "+l+"\n\n";
        }
        return ans;
    }
    public String treeToString(){
        return treeToStringHelper(getRoot(), "  ");
    }
    public String dTreeToString(){
        String ans = dTreeToStringHelper(getRoot(), "  ");
        return ans;
    }
    private String treeToStringHelper(Box root, String prefix){
        //System.out.println("looking at "+root.toString());
        if (root.getChildren().size()==0)
            return prefix + root.toString()+"\n";
        else {
            String ans = prefix+root.toString()+"\n";
            
            ans+=prefix+"begin "+root.toString()+" children\n";
            for (Box b:root.getChildren())
                ans+=treeToStringHelper(b, prefix+prefix);
            ans+=prefix+"end "+root.toString()+" children\n";
            return ans;
        }
    }
    private String dTreeToStringHelper(Box root, String prefix){
        if (root.getDTreeChildren().size()==0)
            return prefix + root.toString()+"\n";
        else {
            String ans = prefix+root.toString()+"\n";
            
            ans+=prefix+"begin "+root.toString()+" children\n";
            for (Box b:root.getDTreeChildren())
                ans+=dTreeToStringHelper(b, prefix+prefix);
            ans+=prefix+"end "+root.toString()+" children\n";
            return ans;
        }
    }
    public void PrintCForest()
    {
        System.out.println("boxes which are not in CTree");
        int minlevel = 2;
        for (Vector<Box> level:struct){
            for (Box b:level){
                if(!b.isInCForest())
                {
                    System.out.print("("+b.level+","+b.index+"),");
                }
            }
        }
        System.out.println();
    }
    public Vector<Point> getX() {
        return x;
    }
    public Vector<Point> getY(){
        return y;
    }
    public long getNumTrans(){
        return numTrans;
    }
    public void setNumTrans(long i){
        this.numTrans=i;
    }
    public long getNumExp(){
        return numExp;
    }
    public void setNumExp(long i){
        this.numExp=i;
    }
    public long getNumDirect(){
        return numDirect;
    }
    public void setNumDirect(long i){
        this.numDirect=i;
    }
    public Potential getPotential(){
        return potential;
    }
    public void setPotential(Potential p){
        this.potential = p;
    }
}

