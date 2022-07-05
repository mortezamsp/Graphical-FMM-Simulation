/*
 * Main.java
 *
 * Created on November 9, 2003, 12:45 PM
 */

package edu.umiacs.fmm;
import java.util.*;
import java.io.*;
/**
 *
 * @author  wpwy
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        int N=500;
        int threshold = 36;
        int p=15;
        
        Vector<Point> x=new Vector<Point>(N);
        Vector<Point> y=new Vector<Point>(N);
        double[] u = new double[N];
        
        for (int i=0; i<N; i++) {
            x.addElement(new Point(Math.random(), Math.random()));
            y.addElement(new Point(Math.random(), Math.random()));
            u[i] = Math.random();
        }
        //loadInput(x, y, u);
        
        FmmTree t = FmmTree.build(threshold, x, y, new Potential(p));
        FmmTreeNonAdaptive tt = FmmTreeNonAdaptive.build(t.getNumOfLevels(), x.toArray(new Point[0]), y.toArray(new Point[0]), new Potential(p));
        double[] indirect = t.solve(u);
        double[] indirectNA = tt.solve(u);
        /*
        System.out.println("V");
        for(double d:indirect){
            System.out.println(d);
        }
         */
        
        
        double[] direct = t.solveDirect(u);
        System.out.println("Error: "+Util.getError(direct, indirect));
        System.out.println("Adaptive: "+t.getNumTrans() +" translations, "+t.getNumExp()+" expansions and "+t.getNumDirect()+" direct evaluations with level "+t.getNumOfLevels());
        System.out.println("NonAdaptive: "+tt.getNumTrans() +" translations, "+tt.getNumExp()+" expansions and "+tt.getNumDirect()+" direct evaluations with level "+tt.getNumOfLevels());
        //System.out.println(t.toString());
        //t.print();
        //System.out.println(t.dTreeToString());
        
         t.PrintCForest();

    }
    private static void loadInput(Vector<Point> x, Vector<Point> y, double[] u) throws Exception{
        String filename = "D:\\My Documents\\My Programming Projects\\fmmdemo-adaptive\\input\\input.txt";
        java.io.BufferedReader fr = new java.io.BufferedReader(new java.io.FileReader(filename));
        String line = fr.readLine();//skip the "X"
        line = fr.readLine();
        while (line!=null && !line.equals("Y")){
            Point p = parsePoint(line);
            x.addElement(p);
            line = fr.readLine();
        }
        line=fr.readLine();//skip the "Y"
        while (line!=null&&!line.equals("U")){
            Point p = parsePoint(line);
            y.addElement(p);
            line = fr.readLine();
        }
        line = fr.readLine();
        int count = 0;
        while (line!=null&&!line.equals("END")){
            u[count++]=Double.parseDouble(line);
            line = fr.readLine();
        }
    }
    private static Point parsePoint(String line){
        StringTokenizer ss = new StringTokenizer(line, " ");
        Point p = new Point(Double.parseDouble(ss.nextToken()), Double.parseDouble(ss.nextToken()));
        return p;
    }
    
}
