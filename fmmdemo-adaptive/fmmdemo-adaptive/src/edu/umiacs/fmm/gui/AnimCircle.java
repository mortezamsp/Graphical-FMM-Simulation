/*
 * AnimCircle.java
 *
 * Created on November 18, 2003, 3:18 PM
 */

package edu.umiacs.fmm.gui;
import java.awt.Color;
/**
 *
 * @author  wpwy
 */
public class AnimCircle {
    double x;
    double y;
    int r;
    Color c;
    int rr;
    
    
    /** Creates a new instance of AnimCircle */
    public AnimCircle(double x, double y, int r, int rr, Color c) {
        this.x=x;
        this.y=y;
        this.r=r;
        this.c=c;
        this.rr=rr;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public int getR() {
        return r;
    }
    public Color getColor(){
        return c;
    }
    public int getRR(){
        return rr;
    }
    public int getOuterCornerXScaled(int proportion){
        return (int)(x*proportion-r);
    }
    public int getOuterCornerYScaled(int proportion){
        return (int)((1-y)*proportion-r);
    }
    public int getInnerCornerXScaled(int proportion){
        return (int)(x*proportion-rr);
    }
    public int getInnerCornerYScaled(int proportion){
        return (int)((1-y)*proportion-rr);
    }
    public boolean isFilled(){
        return rr==0;
    }
    
}
