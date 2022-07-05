/*
 * PotentialFunction.java
 *
 * Created on November 11, 2003, 11:36 AM
 */

package edu.umiacs.fmm;

/**
 *
 * @author  wpwy
 */
public strictfp class Potential {
    
    protected int p;
    public static int DEFAULT_P = 12;
     
    public Potential(){
        this.p=DEFAULT_P;
        //System.out.println("p = "+p);
    }
    
    public Potential(int p) {
        this.p=p;
    }
    
    public Complex direct(Complex yj, Complex xi) {
        return yj.subtract(xi).log();
    }
    
    public int getP(){
        return p;
    }
    public void setP(int p){
        this.p=p;
    }
    
    public Complex[] getSR(Complex from, Complex to, Complex[] sCoeff) {
        Complex t = to.subtract(from);
        Complex[][] sr = new Complex[p][p];
        sr[0][0] = t.log();
        sr[1][0] = new Complex(1.0d).divide(t);
        
        for (int i=2; i<p; i++)
            sr[i][0] = sr[i-1][0].multiply(i-1).divide(t.negate().multiply(i));
        for (int j=1; j<p; j++)
            sr[0][j] = new Complex(1.0d).divide(t.pow(j));
        for (int i=1; i<p; i++)
            for (int j=1; j<p; j++)
                sr[i][j] = sr[i-1][j].multiply(i+j-1).divide(t.negate()).divide(i);
        
        Complex[] ans = new Complex[p];
        for (int i=0; i<p; i++) {
            ans[i]=new Complex(0d);
            for (int j=0; j<p; j++) {
                ans[i] = ans[i].add(sCoeff[j].multiply(sr[i][j]));
            }
        }
        return ans;
    }
    
    public Complex[] getSS(Complex from, Complex to, Complex[] sCoeff) {
        Complex t = to.subtract(from);
        Complex[][] ss = new Complex[p][p];
        for (int i=0; i<p; i++)
            ss[i][i] = new Complex(1.0d);
        ss[1][0] = t;
        for (int i=2; i<p; i++)
            ss[i][0] = ss[i-1][0].multiply(i-1).multiply(t.negate()).divide(i);
        for (int i=1; i<p; i++)
            for (int j=i-1; j>=1; j--)
                ss[i][j]=ss[i][j+1].multiply(t.negate()).multiply((double)j/(i-j));    
        for (int i=0; i<p; i++)
            for (int j=i+1; j<p; j++)
                ss[i][j]=new Complex(0d);
 
        Complex[] ans = new Complex[p];
        for (int i=0; i<p; i++) {
            ans[i]=new Complex(0d);
            for (int j=0; j<p; j++) {
                ans[i] = ans[i].add(sCoeff[j].multiply(ss[i][j]));
            }
        }
        return ans;
    }
    
    public Complex[] getRR(Complex from, Complex to, Complex[] rCoeff) {
        Complex t = to.subtract(from);
        Complex[][] ss = new Complex[p][p];
        for (int i=0; i<p; i++)
            ss[i][i] = new Complex(1.0d);
        for (int j=1; j<p; j++)
            ss[0][j] = ss[0][j-1].multiply(t);
        for (int i=1; i<p; i++)
            for (int j=i+1; j<p; j++)
                ss[i][j]=ss[i-1][j].multiply(j-i+1).divide(t).divide(i);
        for (int i=1; i<p; i++)
            for (int j=0; j<i; j++)
                ss[i][j]=new Complex(0d);
 
        Complex[] ans = new Complex[p];
        for (int i=0; i<p; i++) {
            ans[i]=new Complex(0d);
            for (int j=0; j<p; j++) {
                ans[i] = ans[i].add(rCoeff[j].multiply(ss[i][j]));
            }
        }
        return ans;
    }
    
    
    public Complex[] getRCoeff(Complex xi, Complex xstar) {
        Complex[] ans = new Complex[p];
        ans[0] = xstar.subtract(xi).log();
        for (int i=1; i<p; i++) {
            ans[i] = xi.subtract(xstar).pow(-i).divide(-i);
        }
        return ans;
    }
    
    public Complex[] getRVector (Complex y, Complex xstar){
        Complex[] rVec = new Complex[p];
        for (int i=0; i<p; i++)
            rVec[i] = y.subtract(xstar).pow(i);
        return rVec;
    }
    
           
    public Complex[] getSCoeff(Complex xi, Complex xstar) {
        Complex[] ans = new Complex[p];
        ans[0] = new Complex(1d);
        for (int i=1; i<p; i++) {
            ans[i] = xi.subtract(xstar).pow(i).divide(i).negate();
        }
        return ans;
    }
    
    public Complex[] getSVector (Complex y, Complex xstar){
        Complex[] sVec = new Complex[p];
        sVec[0] = y.subtract(xstar).log();
        for (int i=1; i<p; i++)
            sVec[i] = y.subtract(xstar).pow(-i);
        return sVec;
    }
}
