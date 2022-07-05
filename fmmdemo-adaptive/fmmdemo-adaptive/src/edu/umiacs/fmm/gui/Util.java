/*
 * Util.java
 *
 * Created on November 21, 2003, 9:55 AM
 */

package edu.umiacs.fmm.gui;
import javax.swing.*;
import java.net.*;
/**
 *
 * @author  wpwy
 */
public class Util {
    public static int ICON_GENERATE=0;
    public static int ICON_SETTING=1;
    public static int ICON_START=2;
    public static int ICON_PAUSE=3;
    public static int ICON_STOP=4;
    public static int ICON_LBL_SRC_PTS=5;
    public static int ICON_LBL_TGT_PTS=6;
    public static int ICON_LBL_S_COEFF=7;
    public static int ICON_LBL_R_COEFF_P=8;
    public static int ICON_LBL_R_COEFF_F=9;
    public static int ICON_LBL_R_EXP=10;
    public static int ICON_LBL_S_EXP=11;
    public static int ICON_LBL_RR_TRANS=12;
    public static int ICON_LBL_SR_TRANS=13;
    public static int ICON_LBL_SS_TRANS=14;
    public static String[] filenames={
        "environment16.gif",
        "Preferences16.gif",
        "execute16.gif",
        "Pause16.gif",
        "Stop16.gif",
        "lblSourcePts.gif",
        "lblTgtPts.gif",
        "lblSCoeff.gif",
        "lblRCoeffPartial.gif",
        "lblRCoeffFull.gif",
        "lblRExp.gif",
        "lblSExp.gif",
        "lblRRTrans.gif",
        "lblSRTrans.gif",
        "lblSSTrans.gif"
    };
    /** Creates a new instance of Util */
    public Util() {
    }
    
    public static ImageIcon getIcon(int which){
        try{
            Util tmp = new Util();
            URL ans = tmp.getClass().getResource("/edu/umiacs/fmm/gui/resources/"+filenames[which]);
            //System.out.println(":::"+ans.toString());
            return new ImageIcon(ans);}
        catch(Exception e){
            //e.printStackTrace();
            return null;
        }
    }
    
    public static edu.umiacs.fmm.Point awtToFmmPoint(java.awt.Point awtP, int proportion){
        double x = awtP.getX()/proportion;
        double y = 1-awtP.getY()/proportion;
        return new edu.umiacs.fmm.Point(x, y);
    }
    
    public static java.awt.Point fmmToAwtPoint(edu.umiacs.fmm.Point fmmP, int proportion){
        return new java.awt.Point((int)(proportion*fmmP.getCoord().real()), (int)(proportion*(1-fmmP.getCoord().imaginary())));
    }
    public static boolean pointLocationInBox(edu.umiacs.fmm.Point fmmP, edu.umiacs.fmm.Box b){
        double px = fmmP.getCoord().real();
        double py = fmmP.getCoord().imaginary();
        double lx = b.getCenter().getCoord().real() - b.getSize()/2;
        double hx = b.getCenter().getCoord().real() + b.getSize()/2;
        double ly = b.getCenter().getCoord().imaginary() - b.getSize()/2;
        double hy = b.getCenter().getCoord().imaginary() + b.getSize()/2;
        //System.out.println("is "+fmmP+ " in Box: ("+lx+", "+hx+", "+ly+", "+hy);
        //System.out.println(px>=lx && px<hx && py>=ly && py<hy);
        return (px>=lx && px<hx && py>=ly && py<hy);
    }
    public static java.awt.Point getBoxNECorner(edu.umiacs.fmm.Box b, int proportion){
        int x=(int)((b.getCenter().getCoord().real()-b.getSize()/2)*proportion);
        int width = (int)(b.getSize()*proportion);
        int y=proportion-width-(int)((b.getCenter().getCoord().imaginary()-b.getSize()/2)*proportion);
        return new java.awt.Point(x,y);
    }
}
