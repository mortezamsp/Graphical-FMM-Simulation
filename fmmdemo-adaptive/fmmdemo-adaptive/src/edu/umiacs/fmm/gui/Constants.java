/*
 * Constants.java
 *
 * Created on November 5, 2004, 8:42 PM
 */

package edu.umiacs.fmm.gui;
import java.awt.Color;
/**
 *
 * @author  wpwy
 */
public class Constants {
    
    /** Creates a new instance of Constants */
    public Constants() {
    }
    public static final String APP_VERSION="1.2.8";
    
    
    public static final String ANIM_STATUS_MSG_SEPARATOR = "====================\n";
    
    public static java.awt.Color ANIM_COLOR_HIGHLIGHT = java.awt.Color.LIGHT_GRAY;
    
    public static final int ANIM_STATUS_STOPPED = 100;
    public static final int ANIM_STATUS_RUNNING = 200;
    public static final int ANIM_STATUS_PAUSED = 300;
    public static final int ANIM_STATUS_COMPLETED = 400;
    
    public static final int ANIM_COMMAND_START = 10;
    public static final int ANIM_COMMAND_PAUSE = 20;
    public static final int ANIM_COMMAND_RESUME = 25;
    public static final int ANIM_COMMAND_FF = 30;
    public static final int ANIM_COMMAND_FF2END = 40;
    public static final int ANIM_COMMAND_STOP = 50;
    public static final int ANIM_COMMAND_RESET = 60;
    public static final int ANIM_COMMAND_NOCOMMAND = 90;
    
    public static final Color ANIM_COLOR_S = Color.red;
    public static final Color ANIM_COLOR_SS = new Color(255,204,0);
    public static final Color ANIM_COLOR_SR = new Color(153,0,153);
    public static final Color ANIM_COLOR_RR = new Color(0,153,204);
    public static final Color ANIM_COLOR_R = Color.blue;
    
    public static final int ANIM_DIRECTION_UP=0;
    public static final int ANIM_DIRECTION_DOWN=1;
    
    public static final int ANIM_PROPORTION = 512;
    public static final int ANIM_MAX_COLOR_STEPSIZE = 50;
    
    //use how many steps to animate a growing arrow.
    public static final int ANIM_ARROW_STEPS = 0;
    //millis between repaints.
    public static final long ANIM_INTERVAL_PLAY = 4;
    public static final long ANIM_INTERVAL_FF4X = 1;
    
    public static final long ANIM_SHOW_CIRCLES = 0;
    
    public static final int DEFAULT_N=100;
    public static final int DEFAULT_TRUNCATION_NUMBER=15;
    public static final int DEFAULT_CLUSTER_NUMBER = 7;
    
    
    public static final double DEFAULT_NORMALIZED_FILEDS_THRESHOLD = 0.001;
    
    public static final javax.swing.ImageIcon ICON_PLAY = new javax.swing.ImageIcon(Constants.class.getResource("/edu/umiacs/fmm/gui/resources/execute16.gif"));
    
        
}
