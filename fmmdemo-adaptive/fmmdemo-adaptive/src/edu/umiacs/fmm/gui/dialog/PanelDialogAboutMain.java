/*
 * PanelDialogAboutMain.java
 *
 * Created on November 16, 2004, 1:41 PM
 */

package edu.umiacs.fmm.gui.dialog;

import edu.umiacs.fmm.gui.*;

/**
 *
 * @author  wpwy
 */
public class PanelDialogAboutMain extends javax.swing.JPanel {
    
    /** Creates new form PanelDialogAboutMain */
    public PanelDialogAboutMain() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        panelDialogAboutTitle1 = new edu.umiacs.fmm.gui.dialog.PanelDialogAboutTitle();
        jLabel1 = new javax.swing.JLabel();
        panelDialogAboutSysInfo1 = new edu.umiacs.fmm.gui.dialog.PanelDialogAboutSysInfo();

        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        setPreferredSize(new java.awt.Dimension(424, 256));
        panelDialogAboutTitle1.setPreferredSize(new java.awt.Dimension(430, 77));
        add(panelDialogAboutTitle1);

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel1.setText("<html>Adaptive algorithm based on <i>Fast Multipole Methods for the Helmholtz Equation in Three Dimensions</i>, pp. 265-283, Nail A. Gumerov and Ramani Duraiswami, Elsevier Science, Oxford, 2005</html>");
        jLabel1.setPreferredSize(new java.awt.Dimension(430, 50));
        add(jLabel1);

        add(panelDialogAboutSysInfo1);

    }
    // </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private edu.umiacs.fmm.gui.dialog.PanelDialogAboutSysInfo panelDialogAboutSysInfo1;
    private edu.umiacs.fmm.gui.dialog.PanelDialogAboutTitle panelDialogAboutTitle1;
    // End of variables declaration//GEN-END:variables
    
}
