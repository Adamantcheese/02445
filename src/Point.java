/***************************************************************
* file: Project1.java
* author: Jacob Buchowiecki
* class: CS 445 – Computer Graphics
*
* assignment: Program 2
* date last modified: 4/14/2015
*
* purpose: A simple implementation of a Cartesian coordinate.
****************************************************************/
public class Point {
    private int x;
    private int y;
    
    //method: constructor
    //purpose: Makes this object with the given coordinates.
    public Point(int px, int py) {
        x = px;
        y = py;
    }
    
    //method: getX
    //purpose: Gets the x coordinate of this point.
    public int getX() {
        return x;
    }
    
    //method: getY
    //purpose: Gets the y coordinate of this point.
    public int getY() {
        return y;
    }
}
