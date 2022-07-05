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
public class TestXYs {
    
    /** Creates a new instance of PvE */
    public TestXYs() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int N=2000;
        int threshold = 10;
        //int p=15;
        
        Vector<Point> x=new Vector<Point>(N);
        double[] u = new double[N];
        for (int i=0; i<N; i++) {
            x.add(new Point(Math.random(), Math.random()));
            u[i] = Math.random();
        }
        
        for(int M = 40; M <= 4000; M+=40)
        {
            Vector<Point> y=new Vector<Point>(M);
            for (int i=0; i<M; i++) 
                    y.add(new Point(Math.random(), Math.random()));
            for(int p = 2; p <= 200 ; p+=10)
                System.out.println("M="+M+" p="+p+" "+getFmmError(x, y, p, 15, u));
        }
    }
    
    private static double getFmmError(Vector<Point> x, Vector<Point> y, int threshold, int p, double[] u){
        System.gc();
        Calendar start = Calendar.getInstance();
        FmmTree t = FmmTree.build(threshold, x, y, new Potential(p));
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
