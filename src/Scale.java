/***************************************************************
* file: Project1.java
* author: Jacob Buchowiecki
* class: CS 445 – Computer Graphics
*
* assignment: Program 2
* date last modified: 4/15/2015
*
* purpose: This is a wrapper for a scale transformation.
****************************************************************/
import static org.lwjgl.opengl.GL11.*;

public class Scale implements Transformation {
    
    private double scaleX;
    private double scaleY;
    private int pivotX;
    private int pivotY;
    
    //method: constructor
    //purpose: Store the proper values to properly apply a transformation later.
    public Scale(double sx, double sy, int x, int y) {
        scaleX = sx;
        scaleY = sy;
        pivotX = x;
        pivotY = y;
    }

    //method: applyTransform
    //purpose: Apply this transformation to the current GL matrix.
    @Override
    public void applyTransform() {
        glTranslatef((float) pivotX, (float) pivotY, 0f);
        glScalef((float) scaleX, (float) scaleY, 0f);
        glTranslatef((float) -pivotX, (float) -pivotY, 0f);
    }
}
