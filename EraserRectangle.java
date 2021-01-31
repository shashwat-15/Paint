/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment8;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**This class creates a rectangle only to be used as eraser
 * The only difference between this and Rectangle class is its draw method 
 * which draws only fillRect and not strokeRect and also it has its fixed color fill
 * which is the background color of canvas
 *
 * @author Shashwat Kumar, 000790494
 */
public class EraserRectangle extends GeometricObject {
    //length of the rectangle
    private double length;
    //height of the rectangle
    private double width;
    
    /**
     * This constructor sets the values of its instance variables, and also
     * sets the value for its parent class instance variables by calling GeometricObject's constructor
     * @param x start x-axis location
     * @param y start y-axis location
     * @param length length of the rectangle
     * @param width height of the rectangle
     * @param color fill color
     * @param strokeWidth stroke width
     * @param strokeColor outline color
     */
    public EraserRectangle(double x, double y, Color color, double length, double width, double strokeWidth, Color strokeColor) {
        super(x, y, color, strokeWidth, strokeColor);
        this.length = length;
        this.width = width;
    }
    
    /**
     * this constructor acts as the default constructor which calls the other constructor of this class
     */
    public EraserRectangle(){
        this(10,10,Color.WHITE,10,10,1,Color.WHITE);
    }
    
    /**
     * getter for instance variable, length
     * @return length of the rectangle
     */
    public double getLength() {
        return length;
    }
    
    /**
     * setter for instance variable length
     * @param length length of the rectangle
     */
    public void setLength(double length) {
        this.length = length;
    }
    
    /**
     * getter for instance variable, width
     * @return width of the rectangle
     */
    public double getWidth() {
        return width;
    }
    
    /**
     * setter for instance variable width
     * @param width width of the rectangle
     */
    public void setWidth(double width) {
        this.width = width;
    }
    
    /**
     * just sets color fill and fills a rectangle
     * @param gc 
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(super.getColor());
        gc.fillRect(super.getX()+(gc.getLineWidth()/2)-0.5, super.getY()+(gc.getLineWidth()/2)-0.5, length - (gc.getLineWidth())+1 , width - (gc.getLineWidth())+1);
    }

    @Override
    public String toString() {
        String s = super.toString();
        return s+"\nRectangle{" + "length=" + length + ", width=" + width + '}';
    }

}
