/***************************************************************
* file: Project1.java
* author: Jacob Buchowiecki
* class: CS 445 – Computer Graphics
*
* assignment: Program 2
* date last modified: 4/14/2015
*
* purpose: This is a wrapper for a translate transformation.
****************************************************************/
import static org.lwjgl.opengl.GL11.*;

public class Translate implements Transformation {
    
    private int offsetX;
    private int offsetY;
    
    //method: constructor
    //purpose: Store the proper values to properly apply a transformation later.
    public Translate(int offX, int offY) {
        offsetX = offX;
        offsetY = offY;
    }

    //method: applyTransform
    //purpose: Apply this transformation to the current GL matrix.
    @Override
    public void applyTransform() {
        glTranslatef((float) offsetX, (float) offsetY, 0f);
    }
}
