/*
 * Util.java
 *
 * Created on November 13, 2003, 11:33 AM
 */

package edu.umiacs.fmm;

/**
 *
 * @author  wpwy
 */
public class Util {
    
    public static int interleave(int x, int y, int level) {
        if (x==0 && y==0)
            return 0;
        else {
            int ans = 0;
            for (int i=0; i<level; i++) {
                ans = setbit(ans, (level-i)*2-1, getbit(x, level-i-1));
                ans = setbit(ans, (level-i)*2-2, getbit(y, level-i-1));
            }
            return ans;
        }
    }
    public static Complex uninterleave(int n, int L) {
        if (n==0)
            return new Complex(0d, 0d);
        else {
            int xInt = 0;
            int yInt = 0;
            for (int i=0; i<L; i++){
                xInt = setbit(xInt, L-i-1, getbit(n, (L-i-1)*2+1));
                yInt = setbit(yInt, L-i-1, getbit(n, (L-i-1)*2));
            }
            return new Complex(xInt, yInt);
        }
    }
    public static int setbit(int n, int pos, int setto) {
        if (setto ==1)
            return n | (1<<pos);
        else
            return n & ~(1<<pos);
    }
    public static int getbit(int n, int pos) {
        if ((n & (1<<pos)) != 0)
            return 1;
        else
            return 0;
    }
    public static double getError(double[] indirect, double[] direct) {
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
