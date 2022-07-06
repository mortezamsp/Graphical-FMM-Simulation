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




UPDATES:
-- show incoming and outgoing fields by colors, on cForest and Dtree blocks
-- split problem size into 2 parts : size of sources and size of targets
-- show total output fields, on status dialogue
-- add options to constants-file , for log scaling of output fields (useful for showing intensity colors better) and initial fields to be the same or random (in-order to study effect of distance and intensity).
-- approve several bugs, so you can change settings before running.
-- you can keep control key down and move mouse to draw points faster, in edit dialogue
-- faster animation (to see output faster)


have a look at diagrams too.
there ae 2 diagrams, one of theme is about all paths from which the "build" function of FmmTree object is called. the another one is about all entities which affect on FmmTree object. i uloaded diagrams for further improvements.
