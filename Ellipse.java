/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment8;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**This class creates an ellipse and is a sub class of GeometricObject
 *
 * @author Shashwat Kumar, 000790494
 */
public class Ellipse extends GeometricObject {
    //length of the ellipse
    private double length;
    //height of the ellipse
    private double width;
    
    /**
     * This constructor sets the values of its instance variables, and also
     * sets the value for its parent class instance variables by calling GeometricObject's constructor
     * @param x start x-axis location
     * @param y start y-axis location
     * @param length length of the ellipse
     * @param width height of the ellipse
     * @param color fill color
     * @param strokeWidth stroke width
     * @param strokeColor outline color
     */
    public Ellipse(double x, double y, Color color, double length, double width, double strokeWidth, Color strokeColor) {
        super(x, y, color, strokeWidth, strokeColor);
        this.length = length;
        this.width = width;
    }
    
    /**
     * this constructor acts as the default constructor which calls the other constructor of this class
     */
    public Ellipse(){
        this(10,10,Color.BLACK,10,10,1,Color.BLACK);
    }
    
     /**
     * getter for instance variable, length
     * @return length of the ellipse
     */
    public double getLength() {
        return length;
    }
    
    /**
     * setter for instance variable length
     * @param length length of the ellipse
     */
    public void setLength(double length) {
        this.length = length;
    }
    
    /**
     * getter for instance variable, width
     * @return width of the ellipse
     */
    public double getWidth() {
        return width;
    }
    
    /**
     * setter for instance variable width
     * @param width width of the ellipse
     */
    public void setWidth(double width) {
        this.width = width;
    }
    
    /**
     * sets the line width, stroke color, fill color, and draws the ellipse using GraphicsContext
     * @param gc 
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineWidth(super.getStrokeWidth());
        gc.setStroke(super.getStrokeColor());
        gc.strokeOval(super.getX() , super.getY() , length, width);
        gc.setFill(super.getColor());
        gc.fillOval(super.getX()+(gc.getLineWidth()/2)-0.5, super.getY()+(gc.getLineWidth()/2)-0.5, length - (gc.getLineWidth())+1 , width - (gc.getLineWidth())+1);
    }

    @Override
    public String toString() {
        String s = super.toString();
        return s+"\nEllipse{" + "length=" + length + ", width=" + width + '}';
    }

}
