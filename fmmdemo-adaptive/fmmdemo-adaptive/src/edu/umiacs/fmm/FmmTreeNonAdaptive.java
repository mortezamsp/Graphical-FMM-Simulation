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
public class FmmTreeNonAdaptive{
    public static final int MAX_NUM_LEVEL=8;
    final int dimension = 2;
    int numOfLevels;
    Point[] x;
    Point[] y;
    Potential potential;
    Box[][] struct;
    public static final int DEFAULT_NUM_LEVEL = 3;
    int currLevel;
    
    long numTrans;
    long numExp;
    long numDirect;
    
    /** Creates a new instance of Tree */
    public FmmTreeNonAdaptive() {
        numOfLevels = DEFAULT_NUM_LEVEL;
        currLevel = numOfLevels-1;
        x = null;
        y = null;
        struct = null;
        potential = new Potential();
    }
    public FmmTreeNonAdaptive(int level, Potential potential){
        numOfLevels = level;
        currLevel = numOfLevels-1;
        struct = new Box[numOfLevels][];
        for (int i=0; i<numOfLevels; i++) {
            struct[i] = new Box[(int)Math.pow(4,i)];
            for (int j=0; j<struct[i].length; j++)
                struct[i][j] = new Box(i, j, potential.p);
        }
        this.potential = potential;
    }
    
    public FmmTreeNonAdaptive(int level, Point[] x, Point[] y, Potential potential){
        //building data structure here
        //level must be >=3;
        numOfLevels = level;
        currLevel = numOfLevels-1;
        struct = new Box[numOfLevels][];
        this.x = x;
        this.y = y;
        this.potential = potential;
        initStruct();
    }
    public FmmTreeNonAdaptive(int level, Vector<Point> x, Vector<Point> y, Potential potential){
        //building data structure here
        //level must be >=3;
        numOfLevels = level;
        currLevel = numOfLevels-1;
        struct = new Box[numOfLevels][];
        this.x = vecToArr(x);
        this.y = vecToArr(y);
        this.potential = potential;
        initStruct();
    }
    public FmmTreeNonAdaptive(Point[] x, Point[] y, Potential potential){
        int N = x.length;
        numOfLevels = Math.round((float)(Math.log(N)/Math.log(2)/2));
        if (numOfLevels<DEFAULT_NUM_LEVEL)
            numOfLevels=DEFAULT_NUM_LEVEL;
        struct = new Box[numOfLevels][];
        this.x = x;
        this.y = y;
        this.potential = potential;
        initStruct();
    }
    private void initStruct() {
        for (int i=0; i<numOfLevels; i++) {
            struct[i] = new Box[(int)Math.pow(4,i)];
            for (int j=0; j<struct[i].length; j++) {
                struct[i][j] = new Box(i, j, potential.getP());
                Box parent = getBox(i-1, j >> 2);
                struct[i][j].setParent(parent);
                if (parent!=null)
                    parent.addChild(struct[i][j]);
            }
        }
        for (int i=0; i<x.length; i++)
            struct[numOfLevels-1][x[i].getBoxIndex(numOfLevels-1)].addX(x[i]);
        for (int i=0; i<y.length; i++)
            struct[numOfLevels-1][y[i].getBoxIndex(numOfLevels-1)].addY(y[i]);
    }
    
    public static FmmTreeNonAdaptive build(int maxClusterThreshhold, Point[] x, Point[] y, Potential potential) {
        FmmTreeNonAdaptive ans = null;
        for (int i=DEFAULT_NUM_LEVEL; i<MAX_NUM_LEVEL; i++) {
            ans = new FmmTreeNonAdaptive(i, x, y, potential);
            //System.out.println("trying numOfLevel: "+ans.numOfLevels);
            if (ans.getClusterThreshold()<=maxClusterThreshhold)
                return ans;
        }
        return ans;
    }
    
    private int getIndex(Point[] z, Point p) {
        int ans = -1;
        for (int i=0; i<z.length; i++)
            if (z[i].equals(p))
                return i;
        return ans;
    }
    
    private void upwardPass(double[] u){
        for (int i=0; i<struct[numOfLevels-1].length; i++) {
            Box thisBox = struct[numOfLevels-1][i];
            for (Point thisX:thisBox.getX()){
                Complex[] B = potential.getSCoeff(thisX.getCoord(), thisBox.getCenter().getCoord());
                this.numExp++;
                double thisU = u[getIndex(x, thisX)];
                for (int k=0; k<B.length; k++) {
                    B[k]=B[k].multiply(thisU);
                }
                thisBox.addC(B);
            }
        }
        
        for (int el = numOfLevels-1; el>2; el--) {
            //System.out.println("Upward pass level "+el);
            for (int k=0; k<struct[el].length; k++) {
                Box thisBox = struct[el][k];
                if (thisBox.getXRecursive().size()>0){
                    //System.out.println(thisBox.toString());
                    Box parentBox = thisBox.getParent();
                    Complex from = thisBox.getCenter().getCoord();
                    Complex to = parentBox.getCenter().getCoord();
                    parentBox.addC(potential.getSS(from, to, thisBox.getC()));
                    numTrans++;
                }
            }
        }
    }
    
    private void downwardPass1(){
        
        for (int el=2; el<numOfLevels; el++) {
            for (int k=0; k<struct[el].length; k++) {
                Box thisBox = struct[el][k];
                if (thisBox.getYRecursive().size()>0){
                    for (Box thisE4Box:thisBox.getNeighborsE4(this)) {
                        if (thisE4Box.getXRecursive().size()>0){
                            Complex from = thisE4Box.getCenter().getCoord();
                            Complex to = thisBox.getCenter().getCoord();
                            //Complex[] tmp = thisE4Box.getC();
                            //for (int kk=0; kk<tmp.length; kk++)
                            //    System.out.println(tmp[kk].toString());
                            thisBox.addDtilde( potential.getSR(from, to, thisE4Box.getC()));
                            numTrans++;
                        }
                    }
                }
            }
        }
        
    }
    
    private void downwardPass2(){
        for (int i=0; i<struct[2].length; i++){
            struct[2][i].addD(struct[2][i].getDtilde());
        }
        for (int el=2; el<numOfLevels-1; el++) {
            for (int m=0; m<struct[el].length; m++) {
                Box thisBox = struct[el][m];
                if (thisBox.getYRecursive().size()>0){
                    Complex from = thisBox.getCenter().getCoord();
                    for (Box child:thisBox.getChildren()){
                        Complex to = child.getCenter().getCoord();
                        
                        child.addD( potential.getRR(from, to, thisBox.getD()));
                        //numOpsIndirect+=Math.pow(potential.getP(),2);
                        child.addD( child.getDtilde());
                        //numOpsIndirect+=potential.getP()*2;
                        numTrans++;
                     
                    }
                }
            }
        }
    }
    
    public double[] solve(double[] u) {
        Vector<Point> xv = arrToVec(x);
        upwardPass(u);
        //System.out.println(this.toString());
        downwardPass1();
        downwardPass2();
        
        double[] v = new double[y.length];
        for (int i=0; i<struct[numOfLevels-1].length; i++) {
            Box thisBox = struct[numOfLevels-1][i];
            for (Point thisY:thisBox.getY()){
                double regPart = 0d;
                Complex[] d = thisBox.getD();
                Complex[] rVec = potential.getRVector(thisY.getCoord(), thisBox.getCenter().getCoord());
                //numOpsIndirect+=potential.getP();
                for (int k=0; k<d.length; k++){
                    regPart += d[k].multiply(rVec[k]).real();
                    //numOpsIndirect++;
                }
                numExp++;
                
                double sinPart = 0d;
                Vector<Box> nei = thisBox.getNeighbors(this);
                Vector<Box> neiandme = new Vector<Box>(nei);
                neiandme.addElement(thisBox);
                
                for (Box neibox:neiandme){
                    for (Point thisX:  neibox.getXRecursive()) {
                        double thisU = u[xv.indexOf(thisX)];
                        double inc = potential.direct(thisY.getCoord(), thisX.getCoord()).multiply(thisU).real();
                        sinPart+=inc;
                        numDirect++;
                    }
                }
                
                
                v[getIndex(y, thisY)] = sinPart+regPart;
                
            }
        }
        //reinitialize struct();
        //initStruct();
        return v;
    }
    
    public double[] solveDirect(double[] u) {
        double[] v = new double[y.length];
        for (int j=0; j<v.length; j++) {
            for (int i=0; i<x.length; i++) {
                v[j]+=u[i]* potential.direct(y[j].getCoord(), x[i].getCoord()).real();
                //numOpsDirect++;
            }
        }
        return v;
    }
    
    public int getClusterThreshold() {
        int ans = 0;
        for (int i=0; i<struct[numOfLevels-1].length; i++) {
            int xlength = struct[numOfLevels-1][i].getX().size();
            int ylength = struct[numOfLevels-1][i].getY().size();
            if (xlength>ans)
                ans = xlength;
            if (ylength>ans)
                ans = ylength;
        }
        return ans;
    }
    
    public Box[][] getStruct(){
        return struct;
    }
    public Box getBox(int level, int index) {
        try {
            return struct[level][index];
        } catch (Exception e){
            return null;
        }
    }
    public int getNumOfLevels(){
        return this.numOfLevels;
    }
    public String toString() {
        String ans = "";
        for (int i=0; i<numOfLevels; i++) {
            ans+="########Level "+i+": "+"\n";
            for (int j=0; j<struct[i].length; j++){
                ans+=struct[i][j].toString()+"\n";
                ans+="X points: ";
                for (int k=0; k<struct[i][j].getX().size(); k++) {
                    ans+=struct[i][j].getX().elementAt(k).toString()+" ";
                }
                ans+="\n";
                ans+="Y Points: ";
                for (int k=0; k<struct[i][j].getY().size(); k++) {
                    ans+=struct[i][j].getY().elementAt(k).toString()+" ";
                }
                ans+="\n";
                ans+="C values: ";
                for (int k=0; k<struct[i][j].getC().length; k++) {
                    ans+=struct[i][j].getC()[k].toString()+" ";
                }
                ans+="\n";
                ans+="Dtilde values: ";
                for (int k=0; k<struct[i][j].getC().length; k++) {
                    ans+=struct[i][j].getDtilde()[k].toString()+" ";
                }
                ans+="\n";
                ans+="D values: ";
                for (int k=0; k<struct[i][j].getC().length; k++) {
                    ans+=struct[i][j].getD()[k].toString()+" ";
                }
                ans+="\n";
            }
            ans+="########End Level "+i+"\n\n";
        }
        return ans;
    }
    
    private Vector<Point> arrToVec(Point[] arr){
        Vector<Point> ans = new Vector<Point>(arr.length);
        for (Point p:arr){
            ans.addElement(p);
        }
        return ans;
    }
    private Point[] vecToArr(Vector<Point> vec){
        return vec.toArray(new Point[0]);
    }
    public Point[] getX() {
        return x;
    }
    public Point[] getY(){
        return y;
    }
    public long getNumTrans(){
        return this.numTrans;
    }
    public void setNumTrans(long l){
        numTrans=l;
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
}

