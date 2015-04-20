/***************************************************************
* file: WindowContainer.java
* author: Jacob Buchowiecki
* class: CS 445 – Computer Graphics
*
* assignment: Program 2
* date last modified: 4/15/2015
*
* purpose: This class contains all of the methods required to construct and show
* an OpenGL window with the given objects and runtime code.
****************************************************************/
import java.util.ArrayList;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.input.Keyboard;
import static org.lwjgl.opengl.GL11.*;

public class WindowContainer {
    
    private ArrayList<Polygon> renderList;
    
    //method: constructor
    //purpose: Builds this WindowContainer with a list of polygons to render once ready.
    public WindowContainer(ArrayList<Polygon> polygons) {
        renderList = polygons;
        start();
    }
    
    //method: start
    //purpose: Initializes the display window, OpenGL, and begins rendering.
    private void start() {
        try {
            initUI();
            initGL();
            render();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //method: initUI
    //purpose: Initializes the display window.
    private void initUI() throws Exception {
        Display.setFullscreen(false);
        Display.setDisplayMode(new DisplayMode(640, 480));
        Display.setTitle("Project 2");
        Display.create();
    }
    
    //method: initGL
    //purpose: Initializes the OpenGL rendering suite used.
    private void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        
        glOrtho(-320, 320, -240, 240, 1, -1);
        
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }
    
    //method: render
    //purpose: Renders the objects that were specified when this WindowContainer was made.
    //If the user presses "Esc" or closes the window, the display is destroyed.
    //If the user presses and holds "A", then the x and y axes will be displayed.
    //If the user presses and holds "T", then polygon transformations will not be applied before rendering.
    private void render() {
        while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            try {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                
                for(Polygon p : renderList) {
                    p.render();
                    glLoadIdentity();
                }
                
                if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
                    glColor3f(1f, 1f, 1f);
                    glBegin(GL_LINES);
                        glVertex2f(-320f, 0f);
                        glVertex2f(320f, 0f);
                        glVertex2f(0f, -240f);
                        glVertex2f(0f, 240f);
                    glEnd();
                }
                
                Display.update();
                Display.sync(60);
            } catch (Exception e) {
                
            }
        }
        Display.destroy();
    }
}
