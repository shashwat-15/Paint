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
public abstract class GeometricObject {
    private double x;
    private double y;
    private Color color;
    private double strokeWidth;
    private Color strokeColor;

    public GeometricObject(double x, double y, Color color, double strokeWidth, Color strokeColor) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.strokeColor = strokeColor;
    }

    

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
     public double getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(double strokeWidth) {
        if(strokeWidth < 1){
            throw new IllegalArgumentException();
        }
        this.strokeWidth = strokeWidth;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }
    
    public abstract void draw(GraphicsContext gc);

    @Override
    public String toString() {
        return "GeometricObject{" + "x=" + x + ", y=" + y + ", color=" + color + '}';
    }

   

    
        
}
