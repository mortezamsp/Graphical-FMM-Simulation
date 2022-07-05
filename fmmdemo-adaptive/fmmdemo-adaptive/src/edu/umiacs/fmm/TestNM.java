/*
 * TestNM.java
 *
 * Created on November 16, 2004, 11:48 AM
 */

package edu.umiacs.fmm;

/**
 *
 * @author  wpwy
 */
public class TestNM {
    
    /** Creates a new instance of TestNM */
    public TestNM() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      int N=1000;
      int M=500;
        int threshold = 10;
        //int p=15;
        
        Point[] x=new Point[N];
        double[] u = new double[N];
        for (int i=0; i<N; i++) {
            x[i] = new Point(Math.random(), Math.random());
            u[i] = Math.random();
        }

        for(int ii = 5; ii < 50000; ii*=2)
        {
            M = ii;
            Point[] y=new Point[M];
            for (int i=0; i<M; i++){
                y[i] = new Point(Math.random(), Math.random());
            }
            int p=10;
            System.out.println(p+" "+getFmmError(x, y, N, p, u));
        }
    }
    
    private static double getFmmError(Point[] x, Point[] y, int N, int p, double[] u){
        int level = Math.round((float)(Math.log(N)/Math.log(2)/2));
        if (level<2)
            level=2;
        
        System.gc();
        FmmTree t = FmmTree.build(level, x, y, new Potential(p));
        double[] indirect = t.solve(u);
        double[] direct = t.solveDirect(u);
        System.out.println(indirect.length+" "+ direct.length);
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
