/*
 * PanelMain.java
 *
 * Created on November 5, 2004, 6:00 PM
 */

package edu.umiacs.fmm.gui;
import edu.umiacs.fmm.*;
/**
 *
 * @author  wpwy
 */
public class PanelMain extends javax.swing.JPanel {
    
    /** Creates new form PanelMain */
    public PanelMain() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        pnlMain = new javax.swing.JLayeredPane();
        pointsX = new edu.umiacs.fmm.gui.LayerPoints();
        pointsY = new edu.umiacs.fmm.gui.LayerPoints();
        grid = new edu.umiacs.fmm.gui.LayerGrid();
        anim = new edu.umiacs.fmm.gui.LayerAnimation();
        layerInteraction1 = new edu.umiacs.fmm.gui.LayerInteraction();

        setLayout(new java.awt.BorderLayout());

        setBorder(new javax.swing.border.EtchedBorder());
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        pnlMain.setBackground(new java.awt.Color(255, 255, 255));
        pnlMain.setOpaque(true);
        pointsX.setColor(java.awt.Color.red);
        pointsX.setBounds(0, 0, -1, -1);
        pnlMain.add(pointsX, javax.swing.JLayeredPane.DEFAULT_LAYER);

        pointsY.setColor(java.awt.Color.blue);
        pointsY.setBounds(0, 0, -1, -1);
        pnlMain.add(pointsY, javax.swing.JLayeredPane.DEFAULT_LAYER);

        grid.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
        grid.setBounds(0, 0, 530, -1);
        pnlMain.add(grid, javax.swing.JLayeredPane.DEFAULT_LAYER);

        anim.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                animPropertyChange(evt);
            }
        });

        anim.setBounds(0, 0, -1, -1);
        pnlMain.add(anim, javax.swing.JLayeredPane.DEFAULT_LAYER);

        layerInteraction1.setBounds(0, 0, -1, -1);
        pnlMain.add(layerInteraction1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        add(pnlMain, java.awt.BorderLayout.CENTER);

    }
    // </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // TODO add your handling code here:
        formComponentResizedHelper(evt);
    }//GEN-LAST:event_formComponentResized
    
    private void formComponentResizedHelper(java.awt.event.ComponentEvent evt){
        int p = (int)Math.min(evt.getComponent().getSize().getHeight(), evt.getComponent().getSize().getWidth());
        this.layerInteraction1.setProportion(p);
        this.grid.setProportion(p);
        this.anim.setProportion(p);
        this.pointsX.setProportion(p);
        this.pointsY.setProportion(p);
    }
    
    private void animPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_animPropertyChange
        animPropertyChangeHelper(evt);
    }//GEN-LAST:event_animPropertyChange
    
    private void animPropertyChangeHelper(java.beans.PropertyChangeEvent evt) {
        if (evt.getSource().getClass().equals(AnimationWorker.class)){
            if (evt.getPropertyName().equals("status")){
                int newStatus = (Integer)evt.getNewValue();
                if (newStatus == Constants.ANIM_STATUS_COMPLETED){
                    //enable the interaction layer
                    this.layerInteraction1.setEnabled(true);
                }
            }
        }
        
        //then send one level up.
        propertyChangeSupport.firePropertyChange(evt);
    }
    
    public void setFmmTree(FmmTree fmmTree){
        this.fmmTree = fmmTree;
    }
    
    public void doCommand(String actionCommand){
        if (actionCommand.equals("generate")){
            doReset();
            this.layerInteraction1.setEnabled(true);
        }
        else if (actionCommand.equals("start")){
            this.anim.run();
            this.layerInteraction1.setEnabled(false);
        }
        else if (actionCommand.equals("stop")){
            this.anim.stop();
            this.layerInteraction1.setEnabled(true);
        }
        else if (actionCommand.equals("pause")){
            this.anim.setAnimationWorkerCommand(Constants.ANIM_COMMAND_PAUSE);
            this.layerInteraction1.setEnabled(true);
        }
        else if (actionCommand.equals("resume")){
            this.anim.setAnimationWorkerCommand(Constants.ANIM_COMMAND_RESUME); 
            this.layerInteraction1.setEnabled(false);
        }
        else if (actionCommand.equals("ff")){
            this.anim.setAnimationWorkerCommand(Constants.ANIM_COMMAND_FF);
            this.layerInteraction1.setEnabled(false);
        }
        else if (actionCommand.equals("ff2end")){
            this.anim.setAnimationWorkerCommand(Constants.ANIM_COMMAND_FF2END);
            this.layerInteraction1.setEnabled(false);
        }
        else if (actionCommand.equals("d-tree")){
            this.grid.setShowWhich(LayerGrid.SHOW_D_TREE);
        }
        else if (actionCommand.equals("c-forest")){
            this.grid.setShowWhich(LayerGrid.SHOW_C_FOREST);
        }
    }
    
    
    private void doReset() {
        anim.stop();
        anim.resetCircles();
        anim.resetHighlightBoxes();
        anim.resetLines();
        anim.setFmmTree(fmmTree);
        pointsX.setPoints(fmmTree.getX());
        pointsY.setPoints(fmmTree.getY());
        this.pointsX.repaint();
        this.pointsY.repaint();
        grid.setRoot(fmmTree.getRoot());
        //System.out.println(fmmTree.getStruct().size());
        //System.out.println(fmmTree.getNumOfLevels());
        grid.setMaxLevel(fmmTree.getNumOfLevels());
        this.grid.repaint();
        
        this.layerInteraction1.setFmmTree(fmmTree);
        /*
        if (pointsX.getPoints()!=null && pointsY.getPoints()!=null)
            this.panelToolbar1.getBtnStart().setEnabled(true);
         */
        
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
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private edu.umiacs.fmm.gui.LayerAnimation anim;
    private edu.umiacs.fmm.gui.LayerGrid grid;
    private edu.umiacs.fmm.gui.LayerInteraction layerInteraction1;
    private javax.swing.JLayeredPane pnlMain;
    private edu.umiacs.fmm.gui.LayerPoints pointsX;
    private edu.umiacs.fmm.gui.LayerPoints pointsY;
    // End of variables declaration//GEN-END:variables
    
    FmmTree fmmTree;
    
    
    /**
     * Utility field used by bound properties.
     */
    private java.beans.PropertyChangeSupport propertyChangeSupport =  new java.beans.PropertyChangeSupport(this);
}
