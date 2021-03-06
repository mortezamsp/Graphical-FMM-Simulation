/*
 * Animation.java
 *
 * Created on November 18, 2003, 12:36 PM
 */

package edu.umiacs.fmm.gui;
import edu.umiacs.fmm.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.beans.*;
import java.util.*;
/**
 *
 * @author  wpwy
 */
public class LayerAnimation extends javax.swing.JPanel implements java.io.Serializable{
    
    int proportion=Constants.ANIM_PROPORTION;
    Color c = Constants.ANIM_COLOR_HIGHLIGHT;
    Vector<edu.umiacs.fmm.Box> highlightBoxes;
    Vector<AnimLine> lines;
    Vector<AnimCircle> circles;
    int direction;
    
    int currLevel;
    FmmTree fmmTree;
    AnimationWorker aw;
    /**
     * Holds value of property state.
     */
    private int status;
    
    /** Creates new form Animation */
    public LayerAnimation(){
        fmmTree=null;
        initComponents();
        initFields();
    }
    public LayerAnimation(FmmTree fmmTree) {
        this.fmmTree = fmmTree;
        initComponents();
        initFields();
    }
    
    public void initFields() {
        lines = new Vector<AnimLine>();
        highlightBoxes=new Vector<edu.umiacs.fmm.Box>();
        circles = new Vector<AnimCircle>();
        
        aw = new AnimationWorker(this);
        aw.addPropertyChangeListener(new PropertyChangeListener(){
            public void propertyChange(PropertyChangeEvent evt){
                //simply pass it along to whoever cares to listen
                //System.out.println("Animation: "+evt.getPropertyName()+" "+evt.getNewValue());
                propertyChangeSupport.firePropertyChange(evt);
            }
        });
    }
    
    public void setAnimationWorkerCommand(int command){
        this.aw.setCommand(command);
    }
    public int getDirection() {
        return direction;
    }
    public void setDirection(int d){
        this.direction=d;
    }
    public int getCurrLevel(){
        return currLevel;
    }
    public void setCurrLevel(int l) {
        this.currLevel=l;
    }
    public Vector getHighlightBoxes(){
        return highlightBoxes;
    }
    public void setHighlightBoxes(Vector<edu.umiacs.fmm.Box> highlightBoxes) {
        this.highlightBoxes=highlightBoxes;
    }
    public void resetHighlightBoxes(){
        this.highlightBoxes=new Vector<edu.umiacs.fmm.Box>();
    }
    public void addHighlightBox(edu.umiacs.fmm.Box b){
        this.highlightBoxes.addElement(b);
    }
    public void addHighlightBoxes(Vector<edu.umiacs.fmm.Box> b){ 
        this.highlightBoxes.addAll(b);
    }
    public Vector getLines() {
        return lines;
    }
    public void setLines(Vector<AnimLine> lines){
        this.lines=lines;
    }
    public void resetLines(){
        lines = new Vector<AnimLine>();
    }
    public void addLine(AnimLine l){
        lines.addElement(l);
    }
    public Vector getCircles(){
        return circles;
    }
    public void setCircles(Vector<AnimCircle> circles){
        this.circles=circles;
    }
    public void resetCircles(){
        circles=new Vector<AnimCircle>();
    }
    public void addCircle(AnimCircle c){
        circles.addElement(c);
    }
    public int getProportion() {
        return proportion;
    }
    public void setProportion(int p) {
        proportion = p;
        setSize(p, p);
        this.setPreferredSize(new Dimension(p, p));
        repaint();
    }
    public Color getHighlightColor() {
        return c;
    }
    public void setHighlightColor(Color c) {
        this.c=c;
    }
    public FmmTree getFmmTree(){
        return fmmTree;
    }
    public void setFmmTree(FmmTree fmmTree){
        this.fmmTree = fmmTree;
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(512, 512));
    }
    // </editor-fold>//GEN-END:initComponents
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(c);
        //g2.clearRect(0,0,550,550);
        for (int i=0; i<highlightBoxes.size(); i++){
            edu.umiacs.fmm.Box b = (edu.umiacs.fmm.Box)highlightBoxes.elementAt(i);
            java.awt.Point corner = edu.umiacs.fmm.gui.Util.getBoxNECorner(b, proportion);
            int width = (int)(b.getSize()*proportion);
            g2.fillRect(corner.x, corner.y-1, width+1, width+1);
            
        }
        
        //System.out.println("painting "+lines.size()+" lines.");
        for (int i=0; i<lines.size(); i++){
            AnimLine line = (AnimLine)lines.elementAt(i);
            g2.setPaint(line.getColor());
            g2.setStroke(new BasicStroke(line.getWidth()));
            //g2.drawLine(line.getFromX(),proportion-line.getFromY(),line.getToX(),proportion-line.getToY());
            drawArrow(g2, line, 0.05f);
        }
        
        for (int i=0; i<circles.size();i++){
            AnimCircle circle = (AnimCircle)circles.elementAt(i);
            drawCircle(g2, circle);
        }
        
    }
    
    private void drawCircle(Graphics2D g2, AnimCircle circle){
        g2.setPaint(circle.getColor());
        g2.fillOval(circle.getOuterCornerXScaled(proportion), circle.getOuterCornerYScaled(proportion), 2*circle.getR(), 2*circle.getR());
        if (!circle.isFilled()){
            g2.setPaint(Color.WHITE);
            g2.fillOval(circle.getInnerCornerXScaled(proportion), circle.getInnerCornerYScaled(proportion), 2*circle.getRR(), 2*circle.getRR());
        }
    }
    
    private void drawArrow(Graphics2D g2d, AnimLine line, float stroke) {
        double fromX = line.getFromX();
        double toX = line.getToX();
        double fromY = line.getFromY();
        double toY = line.getToY();
        int fromXScaled = line.getFromXScaled(proportion);
        int toXScaled = line.getToXScaled(proportion);
        int fromYScaled = line.getFromYScaled(proportion);
        int toYScaled = line.getToYScaled(proportion);
        
        double aDir=Math.atan2(fromX-toX,-fromY+toY);
        g2d.drawLine(fromXScaled,fromYScaled,toXScaled,toYScaled);
        
        //draw arrow only if two points are more than 1 pixel apart.
        if (fromXScaled!=toXScaled || fromYScaled!=toYScaled){
            g2d.setStroke(new BasicStroke(1f));
            // make the arrow head solid even if dash pattern has been specified
            Polygon tmpPoly=new Polygon();
            int i1=12+(int)(stroke*2);
            int i2=6+(int)stroke;
            // make the arrow head the same size regardless of the length length
            tmpPoly.addPoint(toXScaled,toYScaled);
            // arrow tip
            tmpPoly.addPoint(toXScaled+xCor(i1,aDir+.2),toYScaled+yCor(i1,aDir+.2));
            tmpPoly.addPoint(toXScaled+xCor(i2,aDir),toYScaled+yCor(i2,aDir));
            tmpPoly.addPoint(toXScaled+xCor(i1,aDir-.2),toYScaled+yCor(i1,aDir-.2));
            tmpPoly.addPoint(toXScaled,toYScaled);
            // arrow tip
            g2d.drawPolygon(tmpPoly);
            g2d.fillPolygon(tmpPoly);
            // remove this line to leave arrow head unpainted
        }
    }
    private static int yCor(int len, double dir) {return (int)(len * Math.cos(dir));}
    private static int xCor(int len, double dir) {return (int)(len * Math.sin(dir));}
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    public void run(){
        this.aw.go();
    }
    public void stop(){
        this.aw.stop();
        initFields();
    }
    public void setInterval(long in) {
        this.aw.setInterval(in);
    }
    public long getInterval(){
        return this.aw.getInterval();
    }
    
    /**
     * Adds a PropertyChangeListener to the listener list.
     * @param l The listener to add.
     */
    public void addPropertyChangeListener(java.beans.PropertyChangeListener l) {
        
        propertyChangeSupport.addPropertyChangeListener(l);
    }
    
    /**
     * Removes a PropertyChangeListener from the listener list.
     * @param l The listener to remove.
     */
    public void removePropertyChangeListener(java.beans.PropertyChangeListener l) {
        
        propertyChangeSupport.removePropertyChangeListener(l);
    }
    /**
     * Utility field used by bound properties.
     */
    private java.beans.PropertyChangeSupport propertyChangeSupport =  new java.beans.PropertyChangeSupport(this);
    
    
    
}
