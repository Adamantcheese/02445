/***************************************************************
* file: Project1.java
* author: Jacob Buchowiecki
* class: CS 445 – Computer Graphics
*
* assignment: Program 2
* date last modified: 4/14/2015
*
* purpose: This is a wrapper for a rotation transformation.
****************************************************************/
import static org.lwjgl.opengl.GL11.*;

public class Rotation implements Transformation {
    
    private int angle;
    private int pivotX;
    private int pivotY;
    
    //method: constructor
    //purpose: Store the proper values to properly apply a transformation later.
    public Rotation(int a, int x, int y) {
        angle = a;
        pivotX = x;
        pivotY = y;
    }

    //method: applyTransform
    //purpose: Apply this transformation to the current GL matrix.
    @Override
    public void applyTransform() {
        glRotatef((float) angle, (float) pivotX, (float) pivotY, 1f);
    }
}
