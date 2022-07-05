/*
 * TarWriter.java
 *
 * Created on October 5, 2004, 9:24 AM
 */

package edu.umiacs.fmm.gui;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.Color;
import edu.umiacs.fmm.*;
/**
 *
 * @author  wpwy
 */
public class AnimationWorker {
    private int status = Constants.ANIM_STATUS_STOPPED;
    private int command = Constants.ANIM_COMMAND_NOCOMMAND;
    private long interval = Constants.ANIM_INTERVAL_PLAY;
    private LayerAnimation anim = null;
    
    /**
     * Utility field used by bound properties.
     */
    private java.beans.PropertyChangeSupport propertyChangeSupport =  new java.beans.PropertyChangeSupport(this);
    
    /**
     * Holds value of property statusMessage.
     */
    private String statusMessage;
    
    /** Creates a new instance of TarWriter */
    public AnimationWorker(LayerAnimation anim) {
        this.anim = anim;
    }
    
    public long getInterval() {
        return interval;
    }
    public void setInterval(long interval){
        this.interval= interval;
    }
    public int getCommand(){
        return command;
    }
    public void setCommand(int c){
        this.command=c;
    }
    
    public void go() {
        final SwingWorker worker = new SwingWorker() {
            public Object construct() {
                setStatus(Constants.ANIM_STATUS_STOPPED);
                setStatusMessage("");
                return new ActualTask();
            }
        };
        worker.start();
    }
    
    public void stop() {
        setCommand(Constants.ANIM_COMMAND_STOP);
        //setStatus(Constants.ANIM_STATUS_STOPPED);
        //setStatusMessage("");
    }
    
    private void processCommandandRepaint() throws AnimationException {
        if (command==Constants.ANIM_COMMAND_PAUSE){
            interval = Constants.ANIM_INTERVAL_PLAY;
            while (command==Constants.ANIM_COMMAND_PAUSE){
                sleepInterval();
            }
        }
        if (command==Constants.ANIM_COMMAND_FF){
            interval = Constants.ANIM_INTERVAL_FF4X;
            sleepInterval();
        } else if (command==Constants.ANIM_COMMAND_FF2END){
            interval = 0;
            //sleepInterval();
        } else if (command==Constants.ANIM_COMMAND_STOP || command==Constants.ANIM_COMMAND_RESET){
            throw new AnimationException();
        }
        if (interval!=0)
            anim.repaint();
    }
    
    //sleeps for a period of time indicated by the current value of "interval"
    private void sleepInterval(){
        sleepInterval(interval);
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
    private void nextStep(){
        //anim.repaint();
        anim.resetHighlightBoxes();
        anim.resetLines();
        if (interval==0) { //FF2END called in this last step
            interval = Constants.ANIM_INTERVAL_PLAY;
            command= Constants.ANIM_COMMAND_PAUSE;
        }
        sleepInterval();
        if (interval!=0)
            anim.repaint();
    }
    
    
    private void animateLines(Vector<edu.umiacs.fmm.Point> from, Vector<edu.umiacs.fmm.Point> to, Color c, int el) throws AnimationException{
        anim.resetLines();
        int numOfLevels = anim.getFmmTree().getStruct().size();
        int lineWidth = numOfLevels-el;
        if (from==null||to==null ||from.size()==0||to.size()==0)
            return;
        for (int k=0; k<=Constants.ANIM_ARROW_STEPS; k++){
            
            anim.resetLines();
            for (edu.umiacs.fmm.Point fromPoint:from){
                for (edu.umiacs.fmm.Point toPoint:to) {
                    double fromX = fromPoint.getCoord().real();
                    double fromY = fromPoint.getCoord().imaginary();
                    double toX = toPoint.getCoord().real();
                    double toY = toPoint.getCoord().imaginary();
                    //int fromXint = (int)(fromX*anim.getProportion());
                    //int fromYint = (int)(fromY*anim.getProportion());
                    //int toXint = (int)((fromX+ (toX-fromX)*k/Constants.ANIM_ARROW_STEPS)*anim.getProportion());
                    //int toYint = (int)((fromY+ (toY-fromY)*k/Constants.ANIM_ARROW_STEPS)*anim.getProportion());
                    double toXIntermediate = fromX+ (toX-fromX)*k/Constants.ANIM_ARROW_STEPS;
                    double toYIntermediate = fromY+ (toY-fromY)*k/Constants.ANIM_ARROW_STEPS;
                    anim.addLine(new AnimLine(fromX, fromY, toXIntermediate, toYIntermediate, c, lineWidth));
                }
            }
            sleepInterval();
            processCommandandRepaint();
        }
    }
    private void animateLines(Vector from, edu.umiacs.fmm.Box to, Color c, int el)throws AnimationException{
        if (from!=null&&from.size()>0) {
            if (from.elementAt(0).getClass().toString().indexOf("Point")>0)
                animateLinesPoint(from, to, c, el);
            else animateLinesBox(from, to, c, el);
        }
    }
    private void animateLinesPoint(Vector<edu.umiacs.fmm.Point> from, edu.umiacs.fmm.Box to, Color c, int el)throws AnimationException{
        Vector<edu.umiacs.fmm.Point> toArr = new Vector<edu.umiacs.fmm.Point>(1);
        toArr.addElement(to.getCenter());
        animateLines(from, toArr,c, el);
    }
    private void animateLinesBox(Vector<edu.umiacs.fmm.Box> from, edu.umiacs.fmm.Box to, Color c, int el)throws AnimationException{
        Vector<edu.umiacs.fmm.Point> toArr = new Vector<edu.umiacs.fmm.Point>(1);
        toArr.addElement(to.getCenter());
        Vector<edu.umiacs.fmm.Point> fromArr= new Vector<edu.umiacs.fmm.Point>(from.size());
        for (Box fromB:from)
            fromArr.addElement(fromB.getCenter());
        animateLines(fromArr, toArr, c, el);
    }
    private void animateLines(edu.umiacs.fmm.Box from, Vector to, Color c, int el) throws AnimationException{
        if (to!=null&&to.size()>0) {
            if (to.elementAt(0).getClass().toString().indexOf("Point")>0)
                animateLinesPoint(from, to, c, el);
            else animateLinesBox(from, to, c, el);
        }
    }
    private void animateLinesPoint(edu.umiacs.fmm.Box from, Vector<edu.umiacs.fmm.Point> to, Color c, int el)throws AnimationException {
        Vector<edu.umiacs.fmm.Point> fromArr = new Vector<edu.umiacs.fmm.Point>(1);
        fromArr.addElement(from.getCenter());
        animateLines(fromArr, to,c, el);
    }
    private void animateLinesBox(edu.umiacs.fmm.Box from, Vector<edu.umiacs.fmm.Box> to, Color c, int el)throws AnimationException{
        Vector<edu.umiacs.fmm.Point> fromArr = new Vector<edu.umiacs.fmm.Point>(1);
        fromArr.addElement(from.getCenter());
        Vector<edu.umiacs.fmm.Point> toArr= new Vector<edu.umiacs.fmm.Point>(to.size());
        for(Box toB:to)
            toArr.addElement(toB.getCenter());
        animateLines(fromArr, toArr, c, el);
    }
    
    private void addCircle(edu.umiacs.fmm.Box b, Color c, int el){
        
        int numOfLevels = anim.getFmmTree().getStruct().size();
        int r = numOfLevels-el;
        int rr = 0;
        if (c.equals(Constants.ANIM_COLOR_S)) {
            r+=4;
            rr=r-2;
        } else if (c.equals(Constants.ANIM_COLOR_SS)) {
            r+=4;
            rr=r-2;
        } else if (c.equals(Constants.ANIM_COLOR_SR)) {
            rr=0;
        } else if (c.equals(Constants.ANIM_COLOR_RR)) {
            r+=2;
            rr=r-2; 
        }
        Complex center = b.getCenter().getCoord();
        anim.addCircle(new AnimCircle(center.real(),center.imaginary(), r,rr, c));
    }
    private void addCircles(Vector<edu.umiacs.fmm.Box> bv, Color c, int el){
        for (edu.umiacs.fmm.Box b:bv)
            addCircle(b, c, el);
    }
    
    private void runAnimationUpwardPass() throws AnimationException{
        setStatusMessage(Constants.ANIM_STATUS_MSG_SEPARATOR);
        processCommandandRepaint();
        setStatusMessage("Performing upward pass.\n");
        
        java.util.Vector<java.util.Vector<Box>> struct = anim.getFmmTree().getStruct();
        int numOfLevels = struct.size();
        for (int l=struct.size()-1; l>=2; l--){
            setStatusMessage("Performing upward pass level "+l+".\n");
            for (Box b:struct.elementAt(l)){
                if (b.isCForestLeaf()){ //black node
                    anim.resetHighlightBoxes();
                    
                    setStatusMessage("          "+b.toString()+" - C Forest leaf node\n");
                    anim.addHighlightBox(b);
                    processCommandandRepaint();
                    sleepInterval();
                    
                    animateLines(b.getXRecursive(), b, Constants.ANIM_COLOR_S, numOfLevels-1);
                    addCircle(b, Constants.ANIM_COLOR_SS, numOfLevels-1);
                    processCommandandRepaint();
                    sleepInterval(interval*Constants.ANIM_ARROW_STEPS*2);
                } else if (b.hasCForestChildren()) {//gray node
                    //System.out.println(thisBox.toString());
                    anim.resetHighlightBoxes();
                    Vector<Point> fromVec = new Vector<Point>();
                    for (Box childB:b.getCForestChildren()) {
                        if (childB.isInCForest()) {
                            fromVec.addElement(childB.getCenter());
                        } else {//white node... direct expansion
                            fromVec.addAll(childB.getXRecursive());
                        }
                    }
                    animateLines(fromVec, b, Constants.ANIM_COLOR_S, numOfLevels-1);
                    addCircle(b, Constants.ANIM_COLOR_SS, numOfLevels-1);
                    
                    
                    sleepInterval(interval*Constants.ANIM_ARROW_STEPS*2);
                }
                //else white node.  do nothing.
            }
        }
        nextStep();
    }
    
    private void runAnimationSExpantion()throws AnimationException{
        //anim.getStatusPanel().setCurrLevel(numOfLevels-1);
        //anim.getStatusPanel().setStep(StatusPanel.STEP_S);
        setStatusMessage(Constants.ANIM_STATUS_MSG_SEPARATOR);
        processCommandandRepaint();
        setStatusMessage("Performing multipole expansion.\n");
        
        
        java.util.Vector<java.util.Vector<Box>> struct = anim.getFmmTree().getStruct();
        int numOfLevels = struct.size();
        for (edu.umiacs.fmm.Box thisBox:struct.lastElement()){
            anim.resetHighlightBoxes();
            
            setStatusMessage("          "+thisBox.toString()+"\n");
            anim.addHighlightBox(thisBox);
            processCommandandRepaint();
            sleepInterval();
            
            animateLines(thisBox.getX(), thisBox, Constants.ANIM_COLOR_S, numOfLevels-1);
            addCircle(thisBox, Constants.ANIM_COLOR_SS, numOfLevels-1);
            
            processCommandandRepaint();
            sleepInterval(interval*Constants.ANIM_ARROW_STEPS*2);
        }
        nextStep();
    }
    private void runAnimationSSTranslation()throws AnimationException{
        //anim.getStatusPanel().setStep(StatusPanel.STEP_SS);
        //now do SS translation
        setStatusMessage(Constants.ANIM_STATUS_MSG_SEPARATOR);
        processCommandandRepaint();
        setStatusMessage("Performing multipole-to-multipole translation.\n");
        Vector<Vector<edu.umiacs.fmm.Box>> struct = anim.getFmmTree().getStruct();
        int numOfLevels = struct.size();
        
        for (int el = numOfLevels-2; el>1; el--) {
            //System.out.println("Upward pass level "+el);
            //anim.getStatusPanel().setCurrLevel(el);
            setStatusMessage(".....Level "+el+"\n");
            for (edu.umiacs.fmm.Box thisBox:struct.elementAt(el)){
                anim.resetHighlightBoxes();
                setStatusMessage("          "+thisBox.toString()+"\n");
                processCommandandRepaint();
                sleepInterval();
                
                Complex to = thisBox.getCenter().getCoord();
                Vector<edu.umiacs.fmm.Box> children = thisBox.getChildren();
                anim.addHighlightBoxes(children);
                animateLines(children, thisBox, Constants.ANIM_COLOR_SS, el);
                addCircle(thisBox, Constants.ANIM_COLOR_SS, el);
                processCommandandRepaint();
                sleepInterval(interval*Constants.ANIM_ARROW_STEPS*2);
            }
            nextStep();
        }
    }
    
    private void runAnimationDownwardRecursive()throws AnimationException{
        processCommandandRepaint();
        //downward pass recursive
        Vector<Vector<edu.umiacs.fmm.Box>> struct = anim.getFmmTree().getStruct();
        int numOfLevels = struct.size();
        
        for (int el = 2; el<numOfLevels-1; el++) {
            
            setStatusMessage(Constants.ANIM_STATUS_MSG_SEPARATOR);
            setStatusMessage("Performing local-to-local translation.\n");
            setStatusMessage(".....Level: "+el+"\n");
            for (edu.umiacs.fmm.Box thisBox:struct.elementAt(el)) {
                if (thisBox.isInDTree() && thisBox.hasDTreeChildren()){
                    anim.resetHighlightBoxes();
                    setStatusMessage("          "+thisBox.toString()+"\n");
                    Vector<edu.umiacs.fmm.Box> children = thisBox.getDTreeChildren();
                    anim.addHighlightBoxes(children);
                    Vector<edu.umiacs.fmm.Box> nonEmptyChildren = new Vector<edu.umiacs.fmm.Box>();
                    for (edu.umiacs.fmm.Box child:children){
                        if (child.getYRecursive().size()>0)
                            nonEmptyChildren.addElement(child);
                    }
                    animateLines(thisBox, nonEmptyChildren, Constants.ANIM_COLOR_RR, el);
                    addCircles(nonEmptyChildren, Constants.ANIM_COLOR_RR, el+1);
                    processCommandandRepaint();
                    sleepInterval(interval*Constants.ANIM_ARROW_STEPS*2);
                    
                    anim.resetHighlightBoxes();
                    anim.resetLines();
                }
            }
            
            nextStep();
            processCommandandRepaint();
            
            setStatusMessage(Constants.ANIM_STATUS_MSG_SEPARATOR);
            setStatusMessage("Performing multipole-to-local translation.\n");
            setStatusMessage(".....Level: "+(el+1)+"\n");
            for (edu.umiacs.fmm.Box thisBox:struct.elementAt(el+1)) {
                if (thisBox.isInDTree()&& thisBox.getYRecursive().size()>0){
                    anim.resetHighlightBoxes();
                    setStatusMessage("          "+thisBox.toString()+"\n");
                    Complex to = thisBox.getCenter().getCoord();
                    Vector<edu.umiacs.fmm.Box> e4nei = thisBox.getNeighborsE4(anim.getFmmTree());
                    Vector<edu.umiacs.fmm.Box> e4neidTree  = new Vector<edu.umiacs.fmm.Box>();
                    Vector<edu.umiacs.fmm.Box> e4neidTreeNonEmpty = new Vector<edu.umiacs.fmm.Box>();
                    anim.addHighlightBox(thisBox);
                    for (edu.umiacs.fmm.Box nei4:e4nei) {
                        if (nei4.isInDTree()) {
                            e4neidTree.addElement(nei4);
                            if (nei4.getXRecursive().size()>0)
                                e4neidTreeNonEmpty.addElement(nei4);
                        }
                    }
                    anim.addHighlightBoxes(e4neidTree);
                    processCommandandRepaint();
                    sleepInterval();
                    
                    animateLines(e4neidTreeNonEmpty, thisBox, Constants.ANIM_COLOR_SR, el+1);
                    addCircle(thisBox, Constants.ANIM_COLOR_SR, el+1);
                    processCommandandRepaint();
                    sleepInterval(interval*Constants.ANIM_ARROW_STEPS*2);
                    
                    anim.resetHighlightBoxes();
                    anim.resetLines();
                }
            }
            nextStep();
        }
    }
    
    private void runAnimationSRTranslation()throws AnimationException{
        setStatusMessage(Constants.ANIM_STATUS_MSG_SEPARATOR);
        processCommandandRepaint();
        setStatusMessage("Performing multipole-to-local translation.\n");
        Vector<Vector<edu.umiacs.fmm.Box>> struct = anim.getFmmTree().getStruct();
        int numOfLevels = struct.size();
        setStatusMessage(".....Level "+2+"\n");
        for (edu.umiacs.fmm.Box thisBox:struct.elementAt(2)) {
            if (thisBox.isInDTree() && thisBox.getYRecursive().size()>0){
                anim.resetHighlightBoxes();
                setStatusMessage("          "+thisBox.toString()+"\n");
                Complex to = thisBox.getCenter().getCoord();
                Vector<edu.umiacs.fmm.Box> e4nei = thisBox.getNeighborsE4(anim.getFmmTree());
                anim.addHighlightBox(thisBox);
                anim.addHighlightBoxes(e4nei);
                processCommandandRepaint();
                sleepInterval();
                
                Vector<edu.umiacs.fmm.Box> e4neiNonEmpty = new Vector<edu.umiacs.fmm.Box>();
                for (edu.umiacs.fmm.Box b:e4nei){
                    if (b.getXRecursive().size()>0)
                        e4neiNonEmpty.addElement(b);
                }
                animateLines(e4neiNonEmpty, thisBox, Constants.ANIM_COLOR_SR, 2);
                addCircle(thisBox, Constants.ANIM_COLOR_SR, 2); processCommandandRepaint();
                sleepInterval(interval*Constants.ANIM_ARROW_STEPS*2);
                anim.resetHighlightBoxes();
                anim.resetLines();
            }
        }
        nextStep();
    }
    
    
    public void runAnimationRExpansion(Box thisBox)throws AnimationException{
            anim.resetHighlightBoxes();
            anim.addHighlightBox(thisBox);
            processCommandandRepaint();
            sleepInterval();
            
            Vector<edu.umiacs.fmm.Point> targets = thisBox.getYRecursive();
            animateLines(thisBox, targets, Constants.ANIM_COLOR_R, thisBox.getLevel());
            processCommandandRepaint();
            sleepInterval(interval*Constants.ANIM_ARROW_STEPS*2);
            anim.resetLines();
    }
 
    
    public void runAnimationFinalSummation() throws AnimationException {
        
        setStatusMessage(Constants.ANIM_STATUS_MSG_SEPARATOR);
        processCommandandRepaint();
        setStatusMessage("Performing final summation.\n");
        
        Vector<Vector<edu.umiacs.fmm.Box>> struct = anim.getFmmTree().getStruct();
        int numOfLevels = struct.size();
        
        //for (int i=0; i<struct[numOfLevels-1].length; i++) {
        for (int i=struct.size()-1; i>=2; i--) {
            
            for (edu.umiacs.fmm.Box thisBox:struct.elementAt(i)){
                if (thisBox.isDTreeLeaf() && thisBox.getYRecursive().size()>0) {
                    setStatusMessage("          "+thisBox.toString()+"\n");
                    anim.resetHighlightBoxes();
                    
                    runAnimationRExpansion(thisBox);
                    //edu.umiacs.fmm.Box thisBox = struct[numOfLevels-1][i];
                    anim.addHighlightBox(thisBox);                    
                    processCommandandRepaint();
                    sleepInterval(interval*Constants.ANIM_ARROW_STEPS);
                    
                    Vector<edu.umiacs.fmm.Box> neighbors = thisBox.getNeighbors(anim.getFmmTree());
                    anim.addHighlightBoxes(neighbors);
                    processCommandandRepaint();
                    sleepInterval(interval*Constants.ANIM_ARROW_STEPS*2);
                }
            }
        }
        nextStep();
    }
    public void runAnimationPotentialIntensity() throws AnimationException
    {
        //get fmm output
        FmmTree fmmTree = anim.getFmmTree();
        fmmTree.solve(anim.getU());
        
        for(int i = 0; i < fmmTree.getStruct().size(); i++)
        {
            for(Box lastlevel:fmmTree.getStruct().elementAt(i))
            {
                if(lastlevel.isDTreeLeaf() && lastlevel.normalized_v != -1)
                {
                    anim.addBlueBox(lastlevel);
                }
                if(lastlevel.isCForestLeaf()&& lastlevel.normalized_u != -1)
                {
                    anim.addRedBox(lastlevel);
                }
            }
        }
        
        processCommandandRepaint();
    }
    public void runAnimation() throws AnimationException{
        setStatusMessage(Constants.ANIM_STATUS_MSG_SEPARATOR);
        setStatusMessage("FMM algorithm started.\n");
        setStatus(Constants.ANIM_STATUS_RUNNING);
        sleepInterval();
        runAnimationUpwardPass();
        propertyChangeSupport.firePropertyChange("pass", "up", "down");
        runAnimationSRTranslation();
        runAnimationDownwardRecursive();
        runAnimationFinalSummation();
        //added by dash morteza
        runAnimationPotentialIntensity();
        setStatusMessage(Constants.ANIM_STATUS_MSG_SEPARATOR);
        setStatusMessage("FMM algorithm completed\n");
        setStatus(Constants.ANIM_STATUS_COMPLETED);
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
     * Getter for property statusMessage.
     * @return Value of property statusMessage.
     */
    public String getStatusMessage() {
        
        return this.statusMessage;
    }
    
    /**
     * Setter for property statusMessage.
     * @param statusMessage New value of property statusMessage.
     */
    public void setStatusMessage(String statusMessage) {
        
        String oldStatusMessage = this.statusMessage;
        this.statusMessage = statusMessage;
        //System.out.println("AnimationWorker: setting status message to "+statusMessage);
        propertyChangeSupport.firePropertyChange("statusMessage", oldStatusMessage, statusMessage);
    }
    
    /**
     * Getter for property status.
     * @return Value of property status.
     */
    public int getStatus() {
        
        return this.status;
    }
    
    /**
     * Setter for property status.
     * @param status New value of property status.
     */
    public void setStatus(int status) {
        
        int oldStatus = this.status;
        this.status = status;
        //System.out.println("AnimationWorker: setting status to "+status);
        propertyChangeSupport.firePropertyChange("status", new Integer(oldStatus), new Integer(status));
    }
    
    /**
     * The actual long running task.  This runs in a SwingWorker thread.
     */
    class ActualTask {
        ActualTask() {
            try{
                runAnimation();
            } catch (AnimationException e){
                //System.out.println("AnimationException CAUGHT");
                setStatus(Constants.ANIM_STATUS_STOPPED);
                setCommand(Constants.ANIM_COMMAND_NOCOMMAND);
            }
        }
        
    }
}
