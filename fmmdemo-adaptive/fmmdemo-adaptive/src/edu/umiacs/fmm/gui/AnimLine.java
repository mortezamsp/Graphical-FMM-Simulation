/*
 * AnimLine.java
 *
 * Created on November 18, 2003, 2:32 PM
 */

package edu.umiacs.fmm.gui;
import java.awt.Color;
/**
 *
 * @author  wpwy
 */
public class AnimLine {
    double fromX;
    double fromY;
    double toX;
    double toY;
    Color c;
    int width;
    /** Creates a new instance of AnimLine */
    public AnimLine(double fromX, double fromY, double toX, double toY, Color c, int width) {
        this.fromX=fromX;
        this.fromY=fromY;
        this.toX=toX;
        this.toY=toY;
        this.c=c;
        this.width = width;
    }
    public double getFromX(){return fromX;}
    public double getFromY(){return fromY;}
    public double getToX(){return toX;}
    public double getToY(){return toY;}
    public int getFromXScaled(int proportion){
        return (int)(fromX*proportion);
    }
    public int getFromYScaled(int proportion){
        return (int)((1-fromY)*proportion);
    }
    public int getToXScaled(int proportion){
        return (int)(toX*proportion);
    }
    public int getToYScaled(int proportion){
        return (int)((1-toY)*proportion);
    }
    public Color getColor(){return c;}
    public int getWidth(){return width;}
    
    
}
