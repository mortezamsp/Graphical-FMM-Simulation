/*
 * PvE.java
 *
 * Created on October 26, 2004, 11:16 AM
 */

package edu.umiacs.fmm;
import java.util.*;
/**
 *
 * @author  wpwy
 */
public class TestNvTime {
    
    /** Creates a new instance of PvE */
    public TestNvTime() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int N=1000;
        int threshold = 10;
        int p=10;
        
        
        
        for (int j=4; j<=80; j++) {
            N = j*50;
            Point[] x=new Point[N];
            Point[] y=new Point[N];
            double[] u = new double[N];
            for (int i=0; i<N; i++) {
                x[i] = new Point(Math.random(), Math.random());
                y[i] = new Point(Math.random(), Math.random());
                u[i] = Math.random();
            }
            System.out.print(N+" ");
            System.gc();
            printFmmTime(x, y, N, p, u);
        }
    }
    
    private static void printFmmTime(Point[] x, Point[] y, int N, int p, double[] u){
        int level = Math.round((float)(Math.log(N)/Math.log(2)/2));
        if (level<2)
            level=2;
        System.gc();
        Calendar indirectStart = Calendar.getInstance();
        FmmTree t = FmmTree.build(level, x, y, new Potential(p));
        double[] indirect = t.solve(u);
        Calendar indirectEnd = Calendar.getInstance();
        
        long indirectT = indirectEnd.getTimeInMillis()-indirectStart.getTimeInMillis();
        System.out.print(indirectT+" "); 
        
        System.gc();
        Calendar directStart = Calendar.getInstance();
        double[] direct = t.solveDirect(u);
        Calendar directEnd = Calendar.getInstance();
        
        long directT = directEnd.getTimeInMillis()-directStart.getTimeInMillis();        
        System.out.println(directT);
    }
    
    private static double getError(double[] indirect, double[] direct) {
        double error = 0;
        for (int i=0; i<direct.length; i++) {
            //System.out.println(direct[i]+" "+indirect[i]);
            double tmp = Math.abs(direct[i]-indirect[i]);
            if (tmp>error)
                error=tmp;
        }
        return error;
    }
}
