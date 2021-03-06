/*
 * Grid.java
 *
 * Created on November 18, 2003, 8:18 AM
 */

package edu.umiacs.fmm.gui;
import edu.umiacs.fmm.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
/**
 *
 * @author  wpwy
 */
public class LayerGrid extends javax.swing.JPanel implements java.io.Serializable{
    public static final int SHOW_C_FOREST = 1;
    public static final int SHOW_D_TREE = 2;
    
    int maxLevel = 0;
    edu.umiacs.fmm.Box root;
    int proportion=Constants.ANIM_PROPORTION;
    Color c=Color.BLACK;
    int showWhich = SHOW_C_FOREST;
    
    /** Creates new form Grid */
    public LayerGrid() {
        initComponents();
    }
    public LayerGrid(edu.umiacs.fmm.Box root, int maxLevel){
        this.root = root;
        this.maxLevel = maxLevel;
        initComponents();
    }
    public void setShowWhich(int i){
        showWhich = i;
        this.repaint();
    }
    public void setMaxLevel(int l){
        maxLevel = l;
    }
    public int getMaxLevel(){
        return maxLevel;
    }
    public void setRoot(edu.umiacs.fmm.Box root){
        this.root = root;
    }
    public edu.umiacs.fmm.Box getRoot(){
        return root;
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents

        setLayout(new java.awt.BorderLayout());

        setBackground(java.awt.Color.white);
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(513, 513));
    }//GEN-END:initComponents
    
    
    public int getProportion() {
        return proportion;
    }
    public void setProportion(int p) {
        proportion = p;
        setSize(p, p);
        this.setPreferredSize(new Dimension(p, p));
        repaint();
    }
    public Color getGridColor() {
        return c;
    }
    public void setGridColor(Color c) {
        this.c=c;
    }
    public void paintComponent(Graphics g) {
        //this.setPreferredSize(new Dimension(proportion,proportion));
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawRect(0,0,proportion,proportion);
        paintComponentHelper(g2, root);
        //System.out.println(proportion);
        //g2.clearRect(0, 0, proportion, proportion);
        /*
        int meshSize = (int)(Math.pow(0.5, level)*proportion);
        if (meshSize<1)
            meshSize=1;
        if (c!=null)
            g2.setPaint(c);
        int start = 0;
        while (start<=proportion) {
            int width = 0;
            int height = proportion;
            int index = start/meshSize;
            width = Math.log(a)
            g2.drawRect(start, 0, width, height);
            g2.drawRect(0, start, width, height);
            //g2.drawLine(start, 0, start, proportion);
            //g2.drawLine(0, start, proportion, start);
            start+=meshSize;
        }
         */
    }
    //sleeps for a period of time indicated by the current value of "interval"
    private void sleepInterval(){
        sleepInterval(Constants.ANIM_INTERVAL_PLAY);
    }
    //sleeps for a period of time indicated by the i
    private void sleepInterval(long i){
        if (i>0) {
            try {
                Thread.sleep(i);
            } catch(Exception e){
            }
        }
    }
    private void paintComponentHelper(Graphics2D g2, edu.umiacs.fmm.Box thisRoot){
        if (thisRoot!=null) {
            if (showWhich == SHOW_D_TREE){
                java.awt.Point center = Util.fmmToAwtPoint(thisRoot.getCenter(), proportion);
                int sideLength = (int)(thisRoot.getSize()*proportion);
                if (thisRoot.getDTreeChildren().size()>0) {
                    
                    int lineWeight = maxLevel - thisRoot.getLevel()-1;
                    drawCross(g2, center, sideLength, lineWeight);
                    
                    for (edu.umiacs.fmm.Box child:thisRoot.getDTreeChildren()){
                        paintComponentHelper(g2, child);
                    }
                }
            } else if (showWhich == SHOW_C_FOREST){
                if (thisRoot.isInCForest()) {
                    //draw box;
                    drawBox(g2, thisRoot);
                }
                
                for (edu.umiacs.fmm.Box child:thisRoot.getChildren())
                    paintComponentHelper(g2, child);
            }
        }
    }
    private void drawBox(Graphics2D g2, edu.umiacs.fmm.Box b){
        java.awt.Point center = Util.fmmToAwtPoint(b.getCenter(), proportion);
        int sideLength = (int)(b.getSize()*proportion);
        g2.drawRect((int)center.getX()-sideLength/2, (int)center.getY()-sideLength/2, sideLength, sideLength);
    }
    private void drawCross(Graphics2D g2, java.awt.Point center, int sideLength, int lineWeight){
        g2.fillRect((int)center.getX()-sideLength/2, (int)center.getY()-lineWeight/2,  sideLength, lineWeight);
        g2.fillRect((int)center.getX()-lineWeight/2, (int)center.getY()-sideLength/2,  lineWeight, sideLength);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
