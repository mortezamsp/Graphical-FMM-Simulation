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
public class TestPvE {
    
    /** Creates a new instance of PvE */
    public TestPvE() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int N=1000;
        int threshold = 10;
        //int p=15;
        
        Point[] x=new Point[N];
        Point[] y=new Point[N];
        double[] u = new double[N];
        for (int i=0; i<N; i++) {
            x[i] = new Point(Math.random(), Math.random());
            y[i] = new Point(Math.random(), Math.random());
            u[i] = Math.random();
        }
        
        for (int p=2; p<50; p++) {
            System.out.println(p+" "+getFmmError(x, y, N, p, u));
        }
    }
    
    private static double getFmmError(Point[] x, Point[] y, int N, int p, double[] u){
        int level = Math.round((float)(Math.log(N)/Math.log(2)/2));
        if (level<2)
            level=2;
        
        System.gc();
        Calendar start = Calendar.getInstance();
        FmmTree t = FmmTree.build(level, x, y, new Potential(p));
        double[] indirect = t.solve(u);
        Calendar stop = Calendar.getInstance();
        System.out.print(stop.getTimeInMillis()-start.getTimeInMillis()+ " ");
        double[] direct = t.solveDirect(u);
        return getError(indirect, direct);
    }
    
    private static double getError (double[] indirect, double[] direct) {
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
