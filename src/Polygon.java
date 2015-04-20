/***************************************************************
* file: Project1.java
* author: Jacob Buchowiecki
* class: CS 445 – Computer Graphics
*
* assignment: Program 2
* date last modified: 4/17/2015
*
* purpose: A model of a polygon, consisting of vertices, an RGB color, and any
* transformations that need to be applied when rendering this polygon.s
****************************************************************/
import java.util.ArrayList;
import java.util.Collections;
import org.lwjgl.input.Keyboard;
import static org.lwjgl.opengl.GL11.*;

public class Polygon {
    
    private ArrayList<Point> vertices;
    private ArrayList<Transformation> transforms;
    private float red;
    private float green;
    private float blue;
    
    //method: constructor
    //purpose: Builds this polygon, with no vertices and no transforms, but an RGB color.
    public Polygon(float r, float g, float b) {
        red = r;
        green = g;
        blue = b;
        vertices = new ArrayList<Point>();
        transforms = new ArrayList<Transformation>();
    }
    
    //method: addVertexList
    //purpose: Sets the vertex list of this polygon to be the argument list.
    public void addVertexList(ArrayList<Point> points) {
        vertices = points;
    }
    
    //method: addTransformList
    //purpose: Sets the transform list of this polygon to be the argument list.
    public void addTransformList(ArrayList<Transformation> ts) {
        transforms = ts;
        Collections.reverse(transforms);
    }
    
    //method: render
    //purpose: Renders this object as a filled polygon with the given transformations applied.
    //If the user presses and holds "T", then polygon transformations will not be applied before rendering.
    public void render() {
        glColor3f(red, green, blue);
        
        //Apply transformations first
        if(!transforms.isEmpty()) {
            if(!Keyboard.isKeyDown(Keyboard.KEY_T)) {
                for(Transformation t : transforms) {
                    t.applyTransform();
                }
            }
        }
        
        //If the polygon is a single point, we only need to draw that and nothing else
        if(vertices.size() < 2) {
            if(!vertices.isEmpty()) {
                glBegin(GL_POINT);
                    glVertex2f((float) vertices.get(0).getX(), (float) vertices.get(0).getY());
                glEnd();
            }
            return;
        }
        
        //Otherwise we need to draw a filled polygon
        //Initialize the allEdges list
        ArrayList<Edge> allEdges = new ArrayList<Edge>();
        if(vertices.size() > 2) {
            for(int i = 0; i < vertices.size() - 1; i++) {
                allEdges.add(new Edge(vertices.get(i), vertices.get(i + 1)));
            }
        }
        allEdges.add(new Edge(vertices.get(vertices.size() - 1), vertices.get(0)));
        
        //Initialize the globalEdges list and sort it properly
        ArrayList<Edge> globalEdges = new ArrayList<Edge>();
        for(Edge e : allEdges) {
            if(e.getSlope() != 0) {
                globalEdges.add(e);
            }
        }
        Collections.sort(globalEdges);

        //Initialize the scanline
        int scanline = globalEdges.get(0).getMinY();
        
        //Initialize the activeEdges list
        ArrayList<Edge> activeEdges = new ArrayList<Edge>();
        ArrayList<Edge> removeList = new ArrayList<Edge>();
        for(Edge e : globalEdges) {
            if(e.getMinY() == scanline) {
                e.setActive();
                activeEdges.add(e);
                removeList.add(e);
            } else {
                break;
            }
        }
        for(Edge e : removeList) {
            globalEdges.remove(e);
        }
        
        //Fill in the polygon
        while(!activeEdges.isEmpty()) {
            //We always start with even parity
            int parity = 0;
            //Draw the fill lines for this current scanline
            glBegin(GL_LINES);
                for(int i = 0; i < activeEdges.size() - 1; i++) {
                    //Swap parity when needed
                    if(parity == 0) {
                        parity = 1;
                    } else {
                        parity = 0;
                    }
                    //Draw only on odd parity
                    if(parity == 1) {
                        glVertex2f((float) activeEdges.get(i).getXMinY(), scanline);
                        glVertex2f((float) activeEdges.get(i + 1).getXMinY(), scanline);
                    }
                }
            glEnd();
            
            //Increment out scanLine
            scanline++;
            
            //Remove edges from the activeEdge list if we are at the top scanline they need
            removeList = new ArrayList<Edge>();
            for(Edge e : activeEdges) {
                if(e.getMaxY() == scanline) {
                    removeList.add(e);
                }
            }
            for(Edge e : removeList) {
                activeEdges.remove(e);
            }
            
            //Update all the other active edges in the list for the next iteration
            for(Edge e : activeEdges) {
                e.update();
            }
            
            //Add edges from the globalEdges list if it is time to start drawing them
            removeList = new ArrayList<Edge>();
            for(Edge e : globalEdges) {
                if(e.getMinY() == scanline) {
                    activeEdges.add(e);
                    removeList.add(e);
                }
            }
            for(Edge e: removeList) {
                globalEdges.remove(e);
            }
            
            //Sort the active edge list to draw lines correctly
            Collections.sort(activeEdges);
        }
    }
}
