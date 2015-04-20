/***************************************************************
* file: Project1.java
* author: Jacob Buchowiecki
* class: CS 445 – Computer Graphics
*
* assignment: Program 2
* date last modified: 4/14/2015
*
* purpose: This is an interface used for transformations of various kinds. All 
* transformations must have some way of being applied to the current GL matrix.
****************************************************************/
public interface Transformation {
    //method: applyTransform
    //purpose: Apply this transformation to the current GL matrix.
    public void applyTransform();
}
