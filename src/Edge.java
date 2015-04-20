/***************************************************************
* file: Project1.java
* author: Jacob Buchowiecki
* class: CS 445 – Computer Graphics
*
* assignment: Program 2
* date last modified: 4/14/2015
*
* purpose: A wrapper for edges, used in the render method of polygons for filling them.
* An additional field of isActive denotes in particular whether the given edge is
* an active one or not, which is used when re-ordering the active edge list.
****************************************************************/
public class Edge implements Comparable {

    private int minY;
    private int maxY;
    private double xMinY;
    private double inverseSlope;
    private boolean isActive;
    
    //method: constructor
    //purpose: Constructs this edge from the two points given. By default, edges are non-active.
    public Edge(Point a, Point b) {
        minY = a.getY() < b.getY() ? a.getY() : b.getY();
        maxY = a.getY() < b.getY() ? b.getY() : a.getY();
        xMinY = a.getY() < b.getY() ? a.getX() : b.getX();
        double dx = (double) (a.getX() - b.getX());
        double dy = (double) (a.getY() - b.getY());
        if(dy == 0) {
            inverseSlope = Double.POSITIVE_INFINITY;
        } else {
            inverseSlope = dx / dy;
        }
        isActive = false;
    }
    
    //method: getMinY
    //purpose: Returns the minimum y value of this edge.
    public int getMinY() {
        return minY;
    }
    
    //method: getMaxY
    //purpose: Returns the maximum y value of this edge.
    public int getMaxY() {
        return maxY;
    }
    
    //method: getXMinY
    //return: Returns the x value associated with the minimum y value of this edge.
    public double getXMinY() {
        return xMinY;
    }
    
    //method: getSlope
    //purpose: Returns the slope of this edge. 
    public double getSlope() {
        if(inverseSlope == 0) {
            return Double.POSITIVE_INFINITY;
        } else if (inverseSlope == Double.POSITIVE_INFINITY) {
            return 0;
        } else {
            return 1/inverseSlope;
        }
    }
    
    //method: setActive
    //purpose: Sets this edge as active for alternate sorting.
    public void setActive() {
        isActive = true;
    }
    
    //method: update
    //purpose: Updates the minimum x value of this edge according to the formula:
    //xf = xi + inverseSlope
    public void update() {
        xMinY = xMinY + inverseSlope;
    }

    //method: compareTo
    //purpose: Compares two Edge objects and returns where the calling Edge object
    //sits in relation to the argument Edge object.
    //
    //This is dependent on if the edge is active or not.
    //
    //If the calling edge is inactive, then if its y value is less than that of the
    //argument edge, the result will be -1. If it is greater, it will be 1. If it is 
    //equal, then we compare on x values. If the calling edge's x value is smaller than
    //the argument edge's x value, the result will be -1. If it is greater, it will be
    //1. Otherwise, the two edges have the same minimum point, so we return 0.
    //
    //If the calling edge is active, then we do the above, but we skip y value checking.
    //We only compare x values, as stated above.
    @Override
    public int compareTo(Object o) {
        Edge two = (Edge) o;
        //Inactive edges are sorted by smallest y first, then smallest x if equal
        if(!isActive){
            if(this.minY < two.getMinY()) {
                return -1;
            } else if (this.minY > two.getMinY()) {
                return 1;
            } else {
                if(this.xMinY < two.getXMinY()) {
                    return -1;
                } else if (this.xMinY > two.getXMinY()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        //Active edges are sorted by smallest x first
        } else {
            if(this.xMinY < two.getXMinY()) {
                return -1;
            } else if (this.xMinY > two.getXMinY()) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
