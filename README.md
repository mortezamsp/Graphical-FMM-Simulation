# Graphical FMM Simulation
Simulating execution of fast multipole method (FMM) in 2D space. 
The original code is released in Wangs Thesis. download thesis from here:
https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=&cad=rja&uact=8&ved=2ahUKEwizycOZ2uH4AhVfhv0HHXEZC8kQFnoECAwQAQ&url=http%3A%2F%2Fusers.umiacs.umd.edu%2F~ramani%2Fcmsc878R%2Fmainthesis.pdf&usg=AOvVaw26mXKVGjaiQ0_CYvuKGNRc

Tips:
There are some hidden options which could not be manipulated through gui. constants file is located in following address:
fmmdemo-adaptive/fmmdemo-adaptive/src/edu/umiacs/fmm/gui/Constants.java

Following options control simulation speed:          
public static final int ANIM_ARROW_STEPS = 10;
public static final long ANIM_INTERVAL_PLAY = 32; 
public static final long ANIM_INTERVAL_FF4X = 8;

please have a look to my branch. I drew some diagrams of code there.
