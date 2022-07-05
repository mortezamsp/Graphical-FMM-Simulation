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
public class TestNvPvE {
    public static int MAX_P=50;
    /** Creates a new instance of PvE */
    public TestNvPvE() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int N=1000;
        double e = 1E-5;
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
            printFmmP(x, y, N, e, u);
        }
    }
    
    private static void printFmmP(Point[] x, Point[] y, int N, double e, double[] u){
        int level = Math.round((float)(Math.log(N)/Math.log(2)/2));
        if (level<2)
            level=2;
        FmmTree tdirect = FmmTree.build(2, x, y, new Potential(3));
        double[] direct = tdirect.solveDirect(u);
        for (int p=2; p<MAX_P; p++){
            FmmTree t = FmmTree.build(level, x, y, new Potential(p));
            double[] indirect = t.solve(u);
            double error = getError(indirect, direct);
            if (error<e) {
                System.out.println(p+" "+error);
                break;
            }            
        }
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
