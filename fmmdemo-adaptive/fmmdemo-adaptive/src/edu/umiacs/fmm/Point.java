/*
 * Point.java
 *
 * Created on November 9, 2003, 12:41 PM
 */

package edu.umiacs.fmm;

/**
 *
 * @author  wpwy
 */
public class Point{
    Complex coord;
    /** Creates a new instance of Point */
    public Point(double x, double y){
        coord = new Complex(x, y);
    }
    public Point(Complex c) {
        coord = c;
    }
    public Point(Point p) {
        coord = new Complex(p.coord);
    }
    
    public Complex getCoord(){
        return coord;
    }
    public void setCoord(Complex coord) {
        this.coord = coord;
    }
    
    
    public String toString(){
        return "Point "+coord.toString();
    }
    
    public boolean equals(Object o) {
        Point p = (Point)o;
        return this.coord.equals(p.coord);
    }
    
    public int getBoxIndex(int level) {
        return Util.interleave(
        (int)Math.floor( coord.real()*(Math.pow(2,level))),
        (int)Math.floor( coord.imaginary()*(Math.pow(2,level))),
        level
        );
    }

}
