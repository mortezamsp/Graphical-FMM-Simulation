/*
 * ComputationThread.java
 *
 * Created on November 20, 2003, 12:52 PM
 */

package edu.umiacs.fmm.gui;
import edu.umiacs.fmm.*;
/**
 *
 * @author  wpwy
 */
public class ComputationThread extends Thread {
    private FmmTree fmmTree = null;
    private FmmTreeNonAdaptive fmmTreeNonAdaptive = null;
    private double error = 0;
    private long numTrans = 0;
    private long numTransNonAdaptive = 0;
    //AnimationThread animt;
    
    /**
     * Utility field used by event firing mechanism.
     */
    private javax.swing.event.EventListenerList listenerList =  null;
    
    /**
     * Utility field used by bound properties.
     */
    private java.beans.PropertyChangeSupport propertyChangeSupport =  new java.beans.PropertyChangeSupport(this);
    
    /**
     * Holds value of property u.
     */
    private double[] u = null;
    /** Creates a new instance of ComputationThread */
    public ComputationThread() {
    }
    public ComputationThread(FmmTree fmmtree){
        this.fmmTree=fmmtree;
        this.fmmTreeNonAdaptive = new FmmTreeNonAdaptive(fmmTree.getNumOfLevels(), fmmTree.getX(), fmmTree.getY(), fmmTree.getPotential()); 
    }
    
    public void setFmmTree(FmmTree fmmTree){
        this.fmmTree = fmmTree;
        this.fmmTreeNonAdaptive = new FmmTreeNonAdaptive(fmmTree.getNumOfLevels(), fmmTree.getX(), fmmTree.getY(), fmmTree.getPotential()); 
    }
    public void genU(){
            u = new double[fmmTree.getX().size()];
            for (int i=0; i<u.length; i++) {
                u[i] = Math.random();
            }
    }
    public void run() {
        double runningError = 0;
        //FmmTree tree = animt.getAnim().getTree();
        if (u==null)
            genU();
        double[] indirect=fmmTree.solve(u);
        double[] nonadapt = fmmTreeNonAdaptive.solve(u);
        double[] direct=fmmTree.solveDirect(u);
        this.setError(edu.umiacs.fmm.Util.getError(indirect,direct));
        this.setNumTrans(fmmTree.getNumTrans());
        this.setNumDirect(fmmTree.getNumDirect());
        this.setNumExp(fmmTree.getNumExp());
        this.setNumTransNonAdaptive(fmmTreeNonAdaptive.getNumTrans());
        this.setNumDirectNonAdaptive(fmmTreeNonAdaptive.getNumDirect());
        this.setNumExpNonAdaptive(fmmTreeNonAdaptive.getNumExp());
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
     * Getter for property error.
     * @return Value of property error.
     */
    public double getError() {
        
        return this.error;
    }
    
    /**
     * Setter for property error.
     * @param error New value of property error.
     */
    public void setError(double error) {
        
        double oldError = this.error;
        this.error = error;
        propertyChangeSupport.firePropertyChange("error", new Double(oldError), new Double(error));
    }
    
  
    
    /**
     * Getter for property opsIndirect.
     * @return Value of property opsIndirect.
     */
    public long getNumTrans() {
        
        return this.numTrans;
    }
    
    /**
     * Setter for property opsIndirect.
     * @param opsIndirect New value of property opsIndirect.
     */
    public void setNumTrans(long n) {
        
        long oldNumTrans = this.numTrans;
        this.numTrans = n;
        propertyChangeSupport.firePropertyChange("numTransAdaptive", new Long(oldNumTrans), new Long(n));
    }
    
    /**
     * Getter for property opsIndirect.
     * @return Value of property opsIndirect.
     */
    public long getNumTransNonAdaptive() {
        
        return this.numTransNonAdaptive;
    }
    
    /**
     * Setter for property opsIndirect.
     * @param opsIndirect New value of property opsIndirect.
     */
    public void setNumTransNonAdaptive(long n) {
        
        long oldNumTransAdaptive = this.numTransNonAdaptive;
        this.numTransNonAdaptive = n;
        propertyChangeSupport.firePropertyChange("numTransNonAdaptive", new Long(oldNumTransAdaptive), new Long(n));
    }
    
    /**
     * Getter for property u.
     * @return Value of property u.
     */
    public double[] getU() {
        
        return this.u;
    }
    
    /**
     * Setter for property u.
     * @param u New value of property u.
     */
    public void setU(double[] u) {
        
        this.u = u;
    }

    /**
     * Holds value of property numExp.
     */
    private long numExp;

    /**
     * Getter for property numExp.
     * @return Value of property numExp.
     */
    public long getNumExp() {

        return this.numExp;
    }

    /**
     * Setter for property numExp.
     * @param numExp New value of property numExp.
     */
    public void setNumExp(long numExp) {

        long oldNumExp = this.numExp;
        this.numExp = numExp;
        propertyChangeSupport.firePropertyChange ("numExpAdaptive", new Long (oldNumExp), new Long (numExp));
    }

    /**
     * Holds value of property numDirect.
     */
    private long numDirect;

    /**
     * Getter for property numDirect.
     * @return Value of property numDirect.
     */
    public long getNumDirect() {

        return this.numDirect;
    }

    /**
     * Setter for property numDirect.
     * @param numDirect New value of property numDirect.
     */
    public void setNumDirect(long numDirect) {

        long oldNumDirect = this.numDirect;
        this.numDirect = numDirect;
        propertyChangeSupport.firePropertyChange ("numDirectAdaptive", new Long (oldNumDirect), new Long (numDirect));
    }

    /**
     * Holds value of property numDirectNonAdaptive.
     */
    private long numDirectNonAdaptive;

    /**
     * Getter for property numDirectNonAdaptive.
     * @return Value of property numDirectNonAdaptive.
     */
    public long getNumDirectNonAdaptive() {

        return this.numDirectNonAdaptive;
    }

    /**
     * Setter for property numDirectNonAdaptive.
     * @param numDirectNonAdaptive New value of property numDirectNonAdaptive.
     */
    public void setNumDirectNonAdaptive(long numDirectNonAdaptive) {

        long oldNumDirectNonAdaptive = this.numDirectNonAdaptive;
        this.numDirectNonAdaptive = numDirectNonAdaptive;
        propertyChangeSupport.firePropertyChange ("numDirectNonAdaptive", new Long(oldNumDirectNonAdaptive), new Long(numDirectNonAdaptive));
    }

    /**
     * Holds value of property numExpNonAdaptive.
     */
    private long numExpNonAdaptive;

    /**
     * Getter for property numExpNonAdaptive.
     * @return Value of property numExpNonAdaptive.
     */
    public long getNumExpNonAdaptive() {

        return this.numExpNonAdaptive;
    }

    /**
     * Setter for property numExpNonAdaptive.
     * @param numExpNonAdaptive New value of property numExpNonAdaptive.
     */
    public void setNumExpNonAdaptive(long numExpNonAdaptive) {

        long oldNumExpNonAdaptive = this.numExpNonAdaptive;
        this.numExpNonAdaptive = numExpNonAdaptive;
        propertyChangeSupport.firePropertyChange ("numExpNonAdaptive", new Long (oldNumExpNonAdaptive), new Long (numExpNonAdaptive));
    }
    
    
    
}
