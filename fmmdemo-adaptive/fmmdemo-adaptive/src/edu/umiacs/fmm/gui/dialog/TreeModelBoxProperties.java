/*
 * JTreeBoxProperties.java
 *
 * Created on November 12, 2004, 12:58 PM
 */

package edu.umiacs.fmm.gui.dialog;
import javax.swing.tree.*;
import edu.umiacs.fmm.*;
import java.util.*;
/**
 *
 * @author  wpwy
 */
public class TreeModelBoxProperties extends DefaultTreeModel{
    
    /** Creates a new instance of JTreeBoxProperties */
    public TreeModelBoxProperties(TreeNode root) {
        super(root);
    }
    
    public TreeModelBoxProperties(Box b, FmmTree t) {
        super(null);
        if (b!=null && t!=null){
            //String rootLabel = "Box "+b.getLabel();
            
            DefaultMutableTreeNode myRoot = new DefaultMutableTreeNode(b);
            this.setRoot(myRoot);
            
            DefaultMutableTreeNode childrenNode = new DefaultMutableTreeNode("Children");
            myRoot.add(childrenNode);
            Vector<Box> childrenBoxes = b.getChildren();
            if (childrenBoxes==null)
                childrenNode.add(new DefaultMutableTreeNode("None"));
            else {
                for (Box bb:childrenBoxes)
                    childrenNode.add(new DefaultMutableTreeNode(bb));
            }
            
            DefaultMutableTreeNode neighborsNode = new DefaultMutableTreeNode("Neighbors");
            myRoot.add(neighborsNode);
            Vector<Box> neighborBoxes = b.getNeighbors(t);
            if (neighborBoxes==null)
                neighborsNode.add(new DefaultMutableTreeNode("None"));
            else {
                for (Box bb:neighborBoxes)
                    neighborsNode.add(new DefaultMutableTreeNode(bb));
            }
            
            DefaultMutableTreeNode e4neighborsNode = new DefaultMutableTreeNode("E4 Neighborhood");
            myRoot.add(e4neighborsNode);
            Vector<Box> e4neighborBoxes = b.getNeighborsE4(t);
            if (e4neighborBoxes==null)
                e4neighborsNode.add(new DefaultMutableTreeNode("None"));
            else {
                for (Box bb:e4neighborBoxes)
                    e4neighborsNode.add(new DefaultMutableTreeNode(bb));
            }
            
            DefaultMutableTreeNode sourceNode = new DefaultMutableTreeNode("Source Pts (X)");
            myRoot.add(sourceNode);
            Vector<Point> x = b.getXRecursive();
            if (x.size()==0)
                sourceNode.add(new DefaultMutableTreeNode("None"));
            else {
                for (Point p:x)
                    sourceNode.add(new DefaultMutableTreeNode(p));
            }
            
            DefaultMutableTreeNode targetNode = new DefaultMutableTreeNode("Target Pts (Y)");
            myRoot.add(targetNode);
            Vector<Point> y = b.getYRecursive();
            if (y.size()==0)
                targetNode.add(new DefaultMutableTreeNode("None"));
            else {
                for (Point p:y)
                    targetNode.add(new DefaultMutableTreeNode(p));
            }
            targetNode.add(new DefaultMutableTreeNode("v = "+b.v));
            targetNode.add(new DefaultMutableTreeNode(
                    b.isDTreeLeaf()?"is dtree leaf":
                    b.isCForestLeaf()?"is CFoest leaf":
                            "is not leaf"));
        }
    }
    
}
