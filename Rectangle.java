/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment8;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author shash
 */
public class Rectangle extends GeometricObject {
    private double length;
    private double width;

    public Rectangle(double x, double y, Color color, double length, double width, double strokeWidth, Color strokeColor) {
        super(x, y, color, strokeWidth, strokeColor);
        this.length = length;
        this.width = width;
    }
    
    public Rectangle(){
        this(10,10,Color.BLACK,10,10,1,Color.BLACK);
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineWidth(super.getStrokeWidth());
        gc.setStroke(super.getStrokeColor());
        
        gc.strokeRect(super.getX(), super.getY(), length, width);
        gc.setFill(super.getColor());
        
        
        gc.fillRect(super.getX()+(gc.getLineWidth()/2)-0.5, super.getY()+(gc.getLineWidth()/2)-0.5, length - (gc.getLineWidth())+1 , width - (gc.getLineWidth())+1);
    }

    @Override
    public String toString() {
        String s = super.toString();
        return s+"\nRectangle{" + "length=" + length + ", width=" + width + '}';
    }

}
