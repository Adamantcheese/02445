/***************************************************************
* file: Project1.java
* author: Jacob Buchowiecki
* class: CS 445 – Computer Graphics
*
* assignment: Program 2
* date last modified: 4/15/2015
*
* purpose: This is the driver program for the project. It takes an input file path
* from the standard input and displays the objects in that file in a new window.
****************************************************************/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Project2 {
    //method: main
    //purpose: Drives this program.
    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("coordinates.txt");
        Scanner sc = new Scanner(f);
        
        ArrayList<Polygon> renderList = new ArrayList<Polygon>();
        
        boolean isPolygonMode = false;
        ArrayList<Point> tempPoints = new ArrayList<Point>();
        ArrayList<Transformation> tempTransforms = new ArrayList<Transformation>();
        Polygon tempPolygon = null;
       
        while(sc.hasNextLine()) {
            String input = sc.nextLine();
            switch(input.charAt(0)) {
                case 'P':
                    if(tempPolygon != null) {
                        tempPolygon.addVertexList(tempPoints);
                        tempPolygon.addTransformList(tempTransforms);
                        
                        tempPoints = new ArrayList<Point>();
                        tempTransforms = new ArrayList<Transformation>();
                        
                        renderList.add(tempPolygon);
                    }
                    isPolygonMode = true;
                    
                    input = input.substring(2);
                    float red = Float.parseFloat(input.substring(0, input.indexOf(' ')));
                    input = input.substring(input.indexOf(' ') + 1);
                    float green = Float.parseFloat(input.substring(0, input.indexOf(' ')));
                    input = input.substring(input.indexOf(' ') + 1);
                    float blue = Float.parseFloat(input);
                    
                    tempPolygon = new Polygon(red, green, blue);
                    break;
                case 'T':
                    if(isPolygonMode) {
                        isPolygonMode = false;
                    } else {
                        return;
                    }
                    break;
                case 't':
                    if(isPolygonMode) {
                        return;
                    }
                    input = input.substring(2);
                    int offsetX = Integer.parseInt(input.substring(0, input.indexOf(' ')));
                    input = input.substring(input.indexOf(' ') + 1);
                    int offsetY = Integer.parseInt(input);
                    
                    tempTransforms.add(new Translate(offsetX, offsetY));
                    break;
                case 'r':
                    if(isPolygonMode) {
                        return;
                    }
                    input = input.substring(2);
                    int angle = Integer.parseInt(input.substring(0, input.indexOf(' ')));
                    input = input.substring(input.indexOf(' ') + 1);
                    int pivotRotX = Integer.parseInt(input.substring(0, input.indexOf(' ')));
                    input = input.substring(input.indexOf(' ') + 1);
                    int pivotRotY = Integer.parseInt(input);
                    
                    tempTransforms.add(new Rotation(angle, pivotRotX, pivotRotY));
                    break;
                case 's':
                    if(isPolygonMode) {
                        return;
                    }
                    input = input.substring(2);
                    double scaleX = Double.parseDouble(input.substring(0, input.indexOf(' ')));
                    input = input.substring(input.indexOf(' ') + 1);
                    double scaleY = Double.parseDouble(input.substring(0, input.indexOf(' ')));
                    input = input.substring(input.indexOf(' ') + 1);
                    int pivotScaleX = Integer.parseInt(input.substring(0, input.indexOf(' ')));
                    input = input.substring(input.indexOf(' ') + 1);
                    int pivotScaleY = Integer.parseInt(input);
                    
                    tempTransforms.add(new Scale(scaleX, scaleY, pivotScaleX, pivotScaleY));
                    break;
                default:
                    if(!isPolygonMode) {
                        return;
                    }
                    int x = Integer.parseInt(input.substring(0, input.indexOf(' ')));
                    int y = Integer.parseInt(input.substring(input.indexOf(' ') + 1));
                    tempPoints.add(new Point(x, y));
                    break;
            }
        }
        
        if(tempPolygon != null) {
            tempPolygon.addVertexList(tempPoints);
            tempPolygon.addTransformList(tempTransforms);
            renderList.add(tempPolygon);
        }
        
        sc.close();
       
        WindowContainer window = new WindowContainer(renderList);
    }
}
