/*
 * FmmDemo.java
 *
 * Created on August 26, 2004, 6:58 PM
 */

package edu.umiacs.fmm.gui;
import edu.umiacs.fmm.*;
import java.util.*;

/**
 *
 * @author  wpwy
 */
public class FmmDemo extends javax.swing.JFrame {
    
    /** Creates new form FmmDemo */
    public FmmDemo() {
        initComponents();
        //initFmmTree();
        this.panelStatus1.appendStatus(Constants.ANIM_STATUS_MSG_SEPARATOR);
        this.panelStatus1.appendStatus("Application initialized.\nPlease generate source and target points to proceed.\n");
        dialogSettings1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dialogSettingsActionPerformed(evt);
            }
        });
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        computationThread1 = new edu.umiacs.fmm.gui.ComputationThread();
        dialogAbout1 = new edu.umiacs.fmm.gui.dialog.DialogAbout(this, true);
        dialogSettings1 = new edu.umiacs.fmm.gui.dialog.DialogSettings(this, true);
        dialogInput1 = new edu.umiacs.fmm.gui.dialog.DialogInput();
        dialogClickPoints1 = new edu.umiacs.fmm.gui.dialog.DialogClickPoints();
        lblStatus = new javax.swing.JLabel();
        panelToolbar1 = new edu.umiacs.fmm.gui.PanelToolbar();
        jPanel1 = new javax.swing.JPanel();
        panelMain1 = new edu.umiacs.fmm.gui.PanelMain();
        panelStatus1 = new edu.umiacs.fmm.gui.PanelStatus();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemRegen = new javax.swing.JMenuItem();
        jMenuItemImport = new javax.swing.JMenuItem();
        jMenuItemSettings = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuItemExit = new javax.swing.JMenuItem();
        jMenuHelp = new javax.swing.JMenu();
        jMenuItemAbout = new javax.swing.JMenuItem();

        computationThread1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                computationThread1PropertyChange(evt);
            }
        });

        dialogAbout1.setLocationRelativeTo(this);
        dialogSettings1.setLocationRelativeTo(this);
        dialogInput1.setLocationRelativeTo(this);
        dialogInput1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dialogInput1ActionPerformed(evt);
            }
        });

        dialogClickPoints1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dialogClickPoints1ActionPerformed(evt);
            }
        });

        getContentPane().setLayout(new java.awt.BorderLayout(5, 0));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Animated Visualization of the Adaptive Fast Multipole Method");
        setIconImage(frameIconImage);
        lblStatus.setFont(new java.awt.Font("Dialog", 0, 10));
        lblStatus.setText("Click on \"Generate\" to start");
        lblStatus.setBorder(new javax.swing.border.EtchedBorder());
        getContentPane().add(lblStatus, java.awt.BorderLayout.SOUTH);

        panelToolbar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                panelToolbar1ActionPerformed(evt);
            }
        });

        getContentPane().add(panelToolbar1, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.X_AXIS));

        jPanel1.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 5, 5, 5)));
        panelMain1.setPreferredSize(new java.awt.Dimension(513, 514));
        panelMain1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                panelMain1PropertyChange(evt);
            }
        });

        jPanel1.add(panelMain1);

        panelStatus1.setPreferredSize(new java.awt.Dimension(400, 512));
        jPanel1.add(panelStatus1);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenuFile.setText("File");
        jMenuFile.setFont(new java.awt.Font("Dialog", 0, 12));
        jMenuItemRegen.setFont(new java.awt.Font("Dialog", 0, 12));
        jMenuItemRegen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/umiacs/fmm/gui/resources/environment16.gif")));
        jMenuItemRegen.setText("Generate Points");
        jMenuItemRegen.setActionCommand("generate");
        jMenuItemRegen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRegenActionPerformed(evt);
            }
        });

        jMenuFile.add(jMenuItemRegen);

        jMenuItemImport.setFont(new java.awt.Font("Dialog", 0, 12));
        jMenuItemImport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/umiacs/fmm/gui/resources/Import16.gif")));
        jMenuItemImport.setText("Import ...");
        jMenuItemImport.setActionCommand("import");
        jMenuItemImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemImportActionPerformed(evt);
            }
        });

        jMenuFile.add(jMenuItemImport);

        jMenuItemSettings.setFont(new java.awt.Font("Dialog", 0, 12));
        jMenuItemSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/umiacs/fmm/gui/resources/Preferences16.gif")));
        jMenuItemSettings.setMnemonic('S');
        jMenuItemSettings.setText("Settings");
        jMenuItemSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSettingsActionPerformed(evt);
            }
        });

        jMenuFile.add(jMenuItemSettings);

        jMenuFile.add(jSeparator1);

        jMenuItemExit.setFont(new java.awt.Font("Dialog", 0, 12));
        jMenuItemExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/umiacs/fmm/gui/resources/null16x16.gif")));
        jMenuItemExit.setMnemonic('E');
        jMenuItemExit.setText("Exit");
        jMenuItemExit.setToolTipText("Exit Program");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });

        jMenuFile.add(jMenuItemExit);

        jMenuBar1.add(jMenuFile);

        jMenuHelp.setText("Help");
        jMenuHelp.setFont(new java.awt.Font("Dialog", 0, 12));
        jMenuItemAbout.setFont(new java.awt.Font("Dialog", 0, 12));
        jMenuItemAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/umiacs/fmm/gui/resources/logo16x16.gif")));
        jMenuItemAbout.setText("About");
        jMenuItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAboutActionPerformed(evt);
            }
        });

        jMenuHelp.add(jMenuItemAbout);

        jMenuBar1.add(jMenuHelp);

        setJMenuBar(jMenuBar1);

        pack();
    }
    // </editor-fold>//GEN-END:initComponents
    
    private void dialogClickPoints1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dialogClickPoints1ActionPerformed
// TODO add your handling code here:
        reinit(this.dialogClickPoints1.getSources(), this.dialogClickPoints1.getTargets(), null, dialogClickPoints1.getS());
    }//GEN-LAST:event_dialogClickPoints1ActionPerformed
    
    private void reinit(Vector<edu.umiacs.fmm.Point> source, Vector<edu.umiacs.fmm.Point> target, Vector<Double> u, int clusteringNumber){
        if (source==null && target==null && clusteringNumber < 0)
            initFmmTree();
        //whn clustering number is updated
        else if (source==null && target==null && clusteringNumber > 0)
        {
            if(tree != null)
                initFmmTree(tree.getX(), tree.getY(), clusteringNumber);
            else
                return;
       }
        else
            initFmmTree(source, target, clusteringNumber);
        if (u!=null && u.size()==source.size())
        {
            //valid u, set it;
            this.computationThread1.setU(doubleVecToDoubleArr(u));
            this.panelMain1.setU(doubleVecToDoubleArr(u));
        }
        
        this.panelMain1.doCommand("generate");
        this.panelToolbar1.getBtnStart().setEnabled(true);
        this.panelToolbar1.getBtnStart().setIcon(Constants.ICON_PLAY);
        this.panelToolbar1.getBtnStart().setToolTipText("Start");
        this.panelToolbar1.getBtnStart().setActionCommand("start");
        this.panelToolbar1.getBtnStop().setEnabled(false);
        this.panelToolbar1.getBtnCForest().setEnabled(false);
        this.panelToolbar1.getBtnDtree().setEnabled(true);
        this.panelMain1.doCommand("c-forest");
        //
        this.dialogInput1.setSource(tree.getX());
        this.dialogInput1.setTarget(tree.getY());
        this.dialogInput1.setU(this.computationThread1.getU());
    }
    
    private void dialogInput1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dialogInput1ActionPerformed
        // TODO add your handling code here:
        //dialogInput1ActionPerformedHelper();
        reinit(this.dialogInput1.getSource(), dialogInput1.getTarget(), dialogInput1.getU(), this.dialogSettings1.getClusterNumber());
    }//GEN-LAST:event_dialogInput1ActionPerformed
    
    /*private void dialogInput1ActionPerformedHelper(){
        initFmmTree(this.dialogInput1.getSource(), dialogInput1.getTarget());
        this.computationThread1.setU(doubleVecToDoubleArr(dialogInput1.getU()));
        this.panelMain1.doCommand("generate");
        this.panelToolbar1.getBtnStart().setEnabled(true);
    }
     */
    private double[] doubleVecToDoubleArr(Vector<Double> u){
        double[] ans = new double[u.size()];
        int i=0;
        for (double d:u){
            ans[i++]=d;
        }
        return ans;
    }
    
    private void jMenuItemImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemImportActionPerformed
        // TODO add your handling code here:
        this.dialogInput1.setVisible(true);
    }//GEN-LAST:event_jMenuItemImportActionPerformed
    
    private void jMenuItemRegenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRegenActionPerformed
        // TODO add your handling code here:
        initFmmTree();
        this.panelMain1.doCommand(evt.getActionCommand());
        this.panelToolbar1.getBtnStart().setEnabled(true);
    }//GEN-LAST:event_jMenuItemRegenActionPerformed
    
    private void jMenuItemSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSettingsActionPerformed
        // TODO add your handling code here:
        this.dialogSettings1.setVisible(true);
    }//GEN-LAST:event_jMenuItemSettingsActionPerformed
    private void dialogSettingsActionPerformed(java.awt.event.ActionEvent evt) {
        //
        reinit(null, null, null, dialogSettings1.getClusterNumber());
    }
    private void jMenuItemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAboutActionPerformed
        // TODO add your handling code here:
        jMenuItemAboutActionPerformedHelper();
    }//GEN-LAST:event_jMenuItemAboutActionPerformed
    private void jMenuItemAboutActionPerformedHelper(){
        this.dialogAbout1.setVisible(true);
    }
    private void computationThread1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_computationThread1PropertyChange
        // TODO add your handling code here:
        computationThread1PropertyChangeHelper(evt);
    }//GEN-LAST:event_computationThread1PropertyChange
    private void computationThread1PropertyChangeHelper(java.beans.PropertyChangeEvent evt) {
        // TODO add your handling code here:
        if (evt.getPropertyName().equals("error")){
            if (animationCompleted){
                this.panelStatus1.appendStatus("Computed error: "+evt.getNewValue().toString()+"\n");
            } else //if animation not yet done, save it for later.
                this.computedError = (Double)evt.getNewValue();
        } else if (evt.getPropertyName().equals("numTransAdaptive")){
            this.numTransAdaptive = (Long)evt.getNewValue();
        } else if (evt.getPropertyName().equals("numTransNonAdaptive")){
            this.numTransNonAdaptive = (Long)evt.getNewValue();
        } else if (evt.getPropertyName().equals("numExpAdaptive")){
            this.numExpAdaptive = (Long)evt.getNewValue();
        } else if (evt.getPropertyName().equals("numExpNonAdaptive")){
            this.numExpNonAdaptive = (Long)evt.getNewValue();
        } else if (evt.getPropertyName().equals("numDirectAdaptive")){
            this.numDirectAdaptive = (Long)evt.getNewValue();
        } else if (evt.getPropertyName().equals("numDirectNonAdaptive")){
            this.numDirectNonAdaptive = (Long)evt.getNewValue();
        }
    }
    private void panelMain1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_panelMain1PropertyChange
        // TODO add your handling code here:
        panelMain1PropertyChangeHelper(evt);
    }//GEN-LAST:event_panelMain1PropertyChange
    private void panelMain1PropertyChangeHelper(java.beans.PropertyChangeEvent evt) {
        //System.out.println("FmmDemo: "+evt.getPropertyName()+" "+evt.getNewValue());
        if (evt.getSource().getClass().equals(AnimationWorker.class)){
            if (evt.getPropertyName().equals("status")){
                int newStatus = (Integer)evt.getNewValue();
                if (newStatus == Constants.ANIM_STATUS_COMPLETED){
                    this.panelToolbar1.getBtnStart().setIcon(panelToolbar1.getPlayIcon());
                    this.panelToolbar1.getBtnStart().setEnabled(false);
                    this.panelToolbar1.getBtnStop().setEnabled(false);
                    this.panelToolbar1.getBtnFF().setEnabled(false);
                    this.panelToolbar1.getBtnFFEnd().setEnabled(false);
                    if (computedError>=0){
                        int p = this.tree.getPotential().getP();
                        long opsAdapt = numExpAdaptive*p+numTransAdaptive*p*p+numDirectAdaptive;
                        long opsReg = numExpNonAdaptive*p+numTransNonAdaptive*p*p+numDirectNonAdaptive;
                        this.panelStatus1.appendStatus(Constants.ANIM_STATUS_MSG_SEPARATOR);
                        this.panelStatus1.appendStatus("Computed error: \n"+computedError+"\n");
                        this.panelStatus1.appendStatus("Num of operations: "+opsAdapt+" ("+numExpAdaptive+" exp, "+numTransAdaptive+" trans, "+numDirectAdaptive+" direct)\n");
                        this.panelStatus1.appendStatus("If not adaptive: "+opsReg+" ("+numExpNonAdaptive+" exp, "+numTransNonAdaptive+" trans, "+numDirectNonAdaptive+" direct)\n");
                        this.panelStatus1.appendStatus(Constants.ANIM_STATUS_MSG_SEPARATOR);
                        this.panelStatus1.appendStatus("To run the algorithm again, please regenerate source and target points first.\n\n\n");
                    }
                } else if (newStatus == Constants.ANIM_STATUS_STOPPED) {
                    this.panelToolbar1.getBtnStart().setIcon(panelToolbar1.getPlayIcon());
                    this.panelToolbar1.getBtnStart().setEnabled(false);
                    this.panelToolbar1.getBtnStop().setEnabled(false);
                    this.panelToolbar1.getBtnFF().setEnabled(false);
                    this.panelToolbar1.getBtnFFEnd().setEnabled(false);
                    this.panelStatus1.appendStatus(Constants.ANIM_STATUS_MSG_SEPARATOR);
                    this.panelStatus1.appendStatus("FMM algorithm stopped by user.\nTo run the algorithm again, please regenerate source and target points first.\n");
                }
            } else if (evt.getPropertyName().equals("statusMessage")){
                this.panelStatus1.appendStatus(evt.getNewValue().toString());
            } else if (evt.getPropertyName().equals("pass")) {
                //System.out.println("PASS");
                this.panelToolbar1.setBtnCForestEnabled(true);
                this.panelToolbar1.setBtnDTreeEnabled(false);
                this.panelMain1.doCommand("d-tree");
            }
            //else
            //    System.out.println(evt.getPropertyName().equals("pass"));
        }
        
    }
    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItemExitActionPerformed
    
    private void panelToolbar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_panelToolbar1ActionPerformed
        // TODO add your handling code here:
        String actionCommand = evt.getActionCommand();
        if (actionCommand.equals("generate")){
            reinit(null, null, null, -1);
            //this.panelMain1.doCommand("c-forest");
        } else {
            if (actionCommand.equals("properties")){
                //this.pnlSide.setSelectedComponent(this.pnlSettings);
                this.dialogSettings1.setVisible(true);
            } else if (actionCommand.equals("start")){
                computationThread1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                    public void propertyChange(java.beans.PropertyChangeEvent evt) {
                        computationThread1PropertyChange(evt);
                    }
                });
                computationThread1.start();
            } else if (actionCommand.equals("pause")){
            } else if (actionCommand.equals("stop")){
            } else if (actionCommand.equals("import")){
                this.dialogInput1.setVisible(true);
            } else if (actionCommand.equals("click"))
                this.dialogClickPoints1.setVisible(true);
            else if (actionCommand.equals("c-forest")){
                
            } else if (actionCommand.equals("d-tree")){
                
            }
            this.panelMain1.doCommand(actionCommand);
        }
    }//GEN-LAST:event_panelToolbar1ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FmmDemo().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private edu.umiacs.fmm.gui.ComputationThread computationThread1;
    private edu.umiacs.fmm.gui.dialog.DialogAbout dialogAbout1;
    private edu.umiacs.fmm.gui.dialog.DialogClickPoints dialogClickPoints1;
    private edu.umiacs.fmm.gui.dialog.DialogInput dialogInput1;
    private edu.umiacs.fmm.gui.dialog.DialogSettings dialogSettings1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenu jMenuHelp;
    private javax.swing.JMenuItem jMenuItemAbout;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JMenuItem jMenuItemImport;
    private javax.swing.JMenuItem jMenuItemRegen;
    private javax.swing.JMenuItem jMenuItemSettings;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblStatus;
    private edu.umiacs.fmm.gui.PanelMain panelMain1;
    private edu.umiacs.fmm.gui.PanelStatus panelStatus1;
    private edu.umiacs.fmm.gui.PanelToolbar panelToolbar1;
    // End of variables declaration//GEN-END:variables
    
    private void initFmmTree() {
        Point[] x=new Point[this.dialogSettings1.getN()];
        Point[] y=new Point[this.dialogSettings1.getM()];
        //double[] u = new double[N];
        for (int i=0; i<dialogSettings1.getN(); i++) {
            x[i] = new Point(Math.random(), Math.random());
        }
        for (int i=0; i<dialogSettings1.getM(); i++) {
            y[i] = new Point(Math.random(), Math.random());
            //u[i] = Math.random();
        }
        initFmmTree(x, y);
    }
    private void initFmmTree(Point[] x, Point[] y){
        initFmmTree(x, y, dialogSettings1.getClusterNumber());
    }
    private void initFmmTree(Point[] x, Point[] y, int q){
        //System.out.println(x.length+" "+y.length);
        Potential potential = new Potential(Constants.DEFAULT_TRUNCATION_NUMBER);
//        q = this.dialogSettings1.getClusterNumber();
//        if(q < 1)
//        {
//            q = this.tree.q;
//        }
        //tree = FmmTree.build(5, x, y, potential);
        tree = FmmTree.build(q, x, y, potential);
        this.U = new double[x.length];
        for(int i = 0; i < x.length; i++)
            this.U[i] = Math.random();
        //System.out.println(tree.getNumOfLevels());
        this.panelMain1.setFmmTree(tree);
        this.panelMain1.setU(U);
        
        this.panelStatus1.appendStatus(Constants.ANIM_STATUS_MSG_SEPARATOR);
        this.panelStatus1.appendStatus("Source and target points generated. (N="+x.length+", p="+dialogSettings1.getTruncationNumber()+")\n");
        this.panelStatus1.appendStatus("Using clustering parameter q: "+
                (q>0?(q+""):(tree.getQ()+" (auto-generated)"))+"\n");
        computationThread1 = new ComputationThread(tree);
        //computationThread1.genU();
        computationThread1.setU(U);
    }
    private void initFmmTree(Vector<Point> x, Vector<Point> y){
        initFmmTree(x, y, this.dialogSettings1.getClusterNumber());
    }
    private void initFmmTree(Vector<Point> x, Vector<Point> y, int s){
        initFmmTree(pointVecToArray(x), pointVecToArray(y), s);
    }
    private Point[] pointVecToArray(Vector<Point> vec){
        return vec.toArray(new Point[0]);
    }
    
    FmmTree tree;
    double[] U;
    boolean animationCompleted = false;
    double computedError = -1;
    long numTransAdaptive = 0;
    long numTransNonAdaptive = 0;
    
    long numExpAdaptive = 0;
    long numExpNonAdaptive = 0;
    
    long numDirectAdaptive = 0;
    long numDirectNonAdaptive = 0;
    
    java.awt.Image frameIconImage = java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/edu/umiacs/fmm/gui/resources/logo16x16.gif"));
}
