/*
 * PanelDialogAboutTitle.java
 *
 * Created on November 16, 2004, 1:01 PM
 */

package edu.umiacs.fmm.gui.dialog;

/**
 *
 * @author  wpwy
 */
public class PanelDialogAboutTitle extends javax.swing.JPanel {
    
    /** Creates new form PanelDialogAboutTitle */
    public PanelDialogAboutTitle() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabelTitle = new javax.swing.JLabel();
        jLabelAuthors = new javax.swing.JLabel();
        jLabelSupervisor = new javax.swing.JLabel();
        jLabelSubtitle = new javax.swing.JLabel();
        jLabelEmpty = new javax.swing.JLabel();
        jLabelEmpty1 = new javax.swing.JLabel();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        jLabelTitle.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabelTitle.setText("<html>Animated Visualization of the Adaptive Fast Multipole Method<html>");
        add(jLabelTitle);

        jLabelAuthors.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabelAuthors.setText("<html>Author: Yang Wang</html>");
        add(jLabelAuthors);

        jLabelSupervisor.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabelSupervisor.setText("<html>Developed under the supervision of Prof. Ramani Duraiswami</html>");
        add(jLabelSupervisor);

        jLabelSubtitle.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabelSubtitle.setText("<html>&copy; 2005, University of Maryland Institute for Advanced Computer Studies</html>");
        add(jLabelSubtitle);

        jLabelEmpty.setText(" ");
        add(jLabelEmpty);

        jLabelEmpty1.setText(" ");
        add(jLabelEmpty1);

    }
    // </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelAuthors;
    private javax.swing.JLabel jLabelEmpty;
    private javax.swing.JLabel jLabelEmpty1;
    private javax.swing.JLabel jLabelSubtitle;
    private javax.swing.JLabel jLabelSupervisor;
    private javax.swing.JLabel jLabelTitle;
    // End of variables declaration//GEN-END:variables
    
}