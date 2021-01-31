/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment8;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**This class creates a line and is a sub class of GeometricObject
 *
 * @author Shashwat Kumar, 000790494
 */
public class Line extends GeometricObject {
    //ending x point for the line
    private double currentX;
    //ending y poinyt for theline
    private double currentY;
    
    /**
     * This constructor sets the values of its instance variables, and also
     * sets the value for its parent class instance variables by calling GeometricObject's constructor
     * @param x start x-axis location
     * @param y start y-axis location
     * @param currentX ending x-axis location
     * @param currentY ending y-axis location
     * @param color fill color
     * @param strokeWidth stroke width
     * @param strokeColor outline color
     */
    public Line(double x, double y, double currentX, double currentY, Color color, double strokeWidth, Color strokeColor) {
        super(x, y, color, strokeWidth, strokeColor);
        this.currentX = currentX;
        this.currentY = currentY;
    }
    
    /**
     * this constructor acts as the default constructor which calls the other constructor of this class
     */
    public Line(){
        this(10,10,20,20,Color.BLACK,1,Color.BLACK);
    }
   
    /**
     * getter for currentX variable
     * @return ending x-axis location of line
     */
    public double getCurrentX() {
        return currentX;
    }
    
    /**
     * setter for currentX variable
     * @param currentX ending x-axis location
     */
    public void setCurrentX(double currentX) {
        this.currentX = currentX;
    }
    
    /**
     * getter for currentY variable
     * @return ending y-axis location of line
     */
    public double getCurrentY() {
        return currentY;
    }
    
    /**
     * setter for currentY variable
     * @param currentY ending y-axis location
     */
    public void setCurrentY(double currentY) {
        this.currentY = currentY;
    }
    
    /**
     * sets the line width, stroke color, and draws the line using GraphicsContext
     * @param gc 
     */
     @Override
    public void draw(GraphicsContext gc) {
        gc.setLineWidth(super.getStrokeWidth());
        gc.setStroke(super.getStrokeColor());
        gc.strokeLine(super.getX(), super.getY(), currentX, currentY);
    }

    @Override
    public String toString() {
        String s = super.toString();
        return s+"\nLine{" + "currentX=" + currentX + ", currentY=" + currentY + '}';
    }

    
}
