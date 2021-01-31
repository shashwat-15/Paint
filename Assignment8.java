package assignment8;


import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This class creates a GUI for the user to draw freehand, erase drawings, draw rectangle and ellipse.
 * It also has an undo function.
 *
 * @author Shashwat Kumar, 000790494
 */
public class Assignment8 extends Application {

    // TODO: Instance Variables for View Components and Model
    //creates object for Line class
    private Line line;
    //creates object for Rectangle class
    private Rectangle rect;
    //creates object for EraserRectangle class
    private EraserRectangle eraserRec;
    private javafx.scene.shape.Rectangle showRect ;
    private javafx.scene.shape.Ellipse showEllipse ;
    //creates object for Ellipse class
    private Ellipse ellipse;
    //creates object for Graphics
    private GraphicsContext gc;
    private Pane root;
    private Canvas canvas;
    //Clickable Image view for free hand drawing
    ImageView img_freeHand;
    //Clickable Image view for eraser drawing
    ImageView img_eraser;
    //Clickable Image view for rectangle drawing
    ImageView img_rect;
    //Clickable Image view for circle drawing
    ImageView img_circle;
    //Label for the text shown at the top of canvas
    private Label lbl_text;
    //Label for outline color
    private Label lbl_outline;
    //Label for fill color
    private Label lbl_fill;
    //Label for stroke width
    private Label lbl_strokeWidth;
    //Label for rectangle length
    private Label lbl_setRectLength;
    //Label for rectangle height
    private Label lbl_setRectHeight;
    //Label for ellipse length
    private Label lbl_setEllipseLength;
    //Label for ellipse height
    private Label lbl_setEllipseHeight;
    //Label for undo
    private Label lbl_undo;
    //ColorPicker for picking ouline color
    private ColorPicker cp_outline;
    //ColorPicker for picking fill color
    private ColorPicker cp_fill;
    //Textfield for setting stroke width
    private TextField txt_strokeWidth;
    //Textfield for setting rectangle length
    private TextField txt_setRectLength;
    //Textfield for setting rectangle height
    private TextField txt_setRectHeight;
    //Textfield for setting ellipse length
    private TextField txt_setEllipseLength;
    //Textfield for setting ellipse height
    private TextField txt_setEllipseHeight;
    //starting x location when the mouse is pressed
    private double startX;
    //starting y location when the mouse is pressed
    private double startY;
    //Arraylist which stores arraylists of type GeometricObject
    private ArrayList<ArrayList<GeometricObject>> objects;
    //Arraylist which stores elements of GeometricObject(Rectangle and Ellipse)
    private ArrayList<GeometricObject> shapes; 
    //Arraylist which stores elements of GeometricObject(Lines)
    private ArrayList<GeometricObject> lines;
    //Arraylist which stores elements of GeometricObject(Eraser Objects)
    private ArrayList<GeometricObject> eraserRect;
    // TODO: Private Event Handlers and Helper Methods
    /**
     * This event handler sets events for creating rectangle on mouse drag and
     * also it adds 2 textfields to root (length and height)
     * @param e 
     */
    private void btn_rectHandler(MouseEvent e){
        root.getChildren().removeAll(lbl_setEllipseLength, lbl_setEllipseHeight, txt_setEllipseLength, txt_setEllipseHeight);
        canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, circlePressHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, circleDragHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, circleReleaseHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, img_freeHandPressHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, img_freeHandDragHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, img_freeHandReleaseHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, img_eraserPressHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, img_eraserDragHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, img_eraserReleaseHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_ENTERED, pencilCursor);
        canvas.removeEventHandler(MouseEvent.MOUSE_ENTERED, eraserCursor);
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, rectPressHandler);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, rectDragHandler);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, rectReleaseHandler);
        canvas.setCursor(Cursor.DEFAULT);
        Image pencil = new Image(getClass().getResourceAsStream("giphy.gif"));
        img_freeHand.setImage(pencil);
        Image eraser = new Image(getClass().getResourceAsStream("eraser.png"));
        img_eraser.setImage(eraser);
        Image circle = new Image(getClass().getResourceAsStream("ellip.png"));
        img_circle.setImage(circle);
        Image rect_gif = new Image(getClass().getResourceAsStream("rectan.gif"));
        img_rect.setImage(rect_gif);
        lbl_setRectLength = new Label("Length");
        lbl_setRectHeight = new Label("Height");
        txt_setRectLength = new TextField("10");
        txt_setRectHeight = new TextField("10");
        txt_setRectLength.setPrefWidth(100);
        txt_setRectHeight.setPrefWidth(100);
        lbl_setRectLength.relocate(800, 300);
        txt_setRectLength.relocate(875, 300);
        lbl_setRectHeight.relocate(800, 375);
        txt_setRectHeight.relocate(875, 375);
        lbl_outline.relocate(775, 480);
        cp_outline.relocate(850, 480);
        lbl_fill.relocate(775, 555);
        cp_fill.relocate(850, 555);
        lbl_strokeWidth.relocate(775, 630);
        txt_strokeWidth.relocate(925, 630);
        lbl_undo.relocate(775, 705);
        root.getChildren().addAll(lbl_setRectLength, lbl_setRectHeight, txt_setRectLength, txt_setRectHeight); 
    }
    
     /**
     * This event handler sets events for creating ellipse on mouse drag and
     * also it adds 2 textfields to root (length and height)
     * @param e 
     */
    private void btn_circleHandler(MouseEvent e){
        root.getChildren().removeAll(lbl_setRectLength, lbl_setRectHeight, txt_setRectLength, txt_setRectHeight);
        canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, rectPressHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, rectDragHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, rectReleaseHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, img_freeHandPressHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, img_freeHandDragHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, img_freeHandReleaseHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, img_eraserPressHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, img_eraserDragHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, img_eraserReleaseHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_ENTERED, pencilCursor);
        canvas.removeEventHandler(MouseEvent.MOUSE_ENTERED, eraserCursor);
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, circlePressHandler);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, circleDragHandler);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, circleReleaseHandler);
        canvas.setCursor(Cursor.DEFAULT);
        Image pencil = new Image(getClass().getResourceAsStream("giphy.gif"));
        img_freeHand.setImage(pencil);
        Image eraser = new Image(getClass().getResourceAsStream("eraser.png"));
        img_eraser.setImage(eraser); 
        Image recta = new Image(getClass().getResourceAsStream("rectan.png"));
        img_rect.setImage(recta);
        Image circle_gif = new Image(getClass().getResourceAsStream("ellip.gif"));
        img_circle.setImage(circle_gif); 
        lbl_setEllipseLength = new Label("Length");
        lbl_setEllipseHeight = new Label("Height");
        txt_setEllipseLength = new TextField("10");
        txt_setEllipseHeight = new TextField("10");
        txt_setEllipseLength.setPrefWidth(100);
        txt_setEllipseHeight.setPrefWidth(100);
        lbl_setEllipseLength.relocate(800, 300);
        txt_setEllipseLength.relocate(875, 300);
        lbl_setEllipseHeight.relocate(800, 375);
        txt_setEllipseHeight.relocate(875, 375);
        lbl_outline.relocate(775, 480);
        cp_outline.relocate(850, 480);
        lbl_fill.relocate(775, 555);
        cp_fill.relocate(850, 555);
        lbl_strokeWidth.relocate(775, 630);
        txt_strokeWidth.relocate(925, 630);
        lbl_undo.relocate(775, 705);
        root.getChildren().addAll(lbl_setEllipseLength, lbl_setEllipseHeight, txt_setEllipseLength, txt_setEllipseHeight); 
        
    }
    
     /**
     * This event handler sets events for free hand drawing on mouse drag
     * @param e 
     */
    private void img_freeHandHandler(MouseEvent e){
        root.getChildren().removeAll(lbl_setRectLength, lbl_setRectHeight, txt_setRectLength, txt_setRectHeight,lbl_setEllipseLength, 
                    lbl_setEllipseHeight, txt_setEllipseLength, txt_setEllipseHeight);
        canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, rectPressHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, rectDragHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, rectReleaseHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, circlePressHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, circleDragHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, circleReleaseHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, img_eraserPressHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, img_eraserDragHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, img_eraserReleaseHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_ENTERED, eraserCursor);
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, img_freeHandPressHandler);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, img_freeHandDragHandler);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, img_freeHandReleaseHandler);
        canvas.addEventHandler(MouseEvent.MOUSE_ENTERED, pencilCursor);
        Image recta = new Image(getClass().getResourceAsStream("rectan.png"));
        img_rect.setImage(recta);
        Image circle= new Image(getClass().getResourceAsStream("ellip.png"));
        img_circle.setImage(circle);        
        Image eraser = new Image(getClass().getResourceAsStream("eraser.png"));
        img_eraser.setImage(eraser);         
        Image pencil_gif = new Image(getClass().getResourceAsStream("a.gif"));
        img_freeHand.setImage(pencil_gif);       
        lbl_outline.relocate(775, 325);
        cp_outline.relocate(850, 325);
        lbl_fill.relocate(775, 400);
        cp_fill.relocate(850, 400);
        lbl_strokeWidth.relocate(775, 475);
        txt_strokeWidth.relocate(925, 475);
        lbl_undo.relocate(775, 550);
    }
    
     /**
     * This event handler sets events for creating rectangles for eraser on mouse drag and
     * @param e 
     */
    private void img_eraserHandler(MouseEvent e){
        root.getChildren().removeAll(lbl_setRectLength, lbl_setRectHeight, txt_setRectLength, txt_setRectHeight,
                    lbl_setEllipseLength, lbl_setEllipseHeight, txt_setEllipseLength, txt_setEllipseHeight);
        canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, rectPressHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, rectDragHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, rectReleaseHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, circlePressHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, circleDragHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, circleReleaseHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, img_freeHandPressHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, img_freeHandDragHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, img_freeHandReleaseHandler);
        canvas.removeEventHandler(MouseEvent.MOUSE_ENTERED, pencilCursor);
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, img_eraserPressHandler);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, img_eraserDragHandler);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, img_eraserReleaseHandler);
        canvas.addEventHandler(MouseEvent.MOUSE_ENTERED, eraserCursor);
        Image recta = new Image(getClass().getResourceAsStream("rectan.png"));
        img_rect.setImage(recta);
        Image circle = new Image(getClass().getResourceAsStream("ellip.png"));
        img_circle.setImage(circle);
        Image pencil = new Image(getClass().getResourceAsStream("giphy.gif"));
        img_freeHand.setImage(pencil);
        Image eraser_gif = new Image(getClass().getResourceAsStream("eraser.gif"));
        img_eraser.setImage(eraser_gif);
        lbl_outline.relocate(775, 325);
        cp_outline.relocate(850, 325);
        lbl_fill.relocate(775, 400);
        cp_fill.relocate(850, 400);
        lbl_strokeWidth.relocate(775, 475);
        txt_strokeWidth.relocate(925, 475);
        lbl_undo.relocate(775, 550);
    }
    
     /**
     * This event handler sets the default cursor image to pencil image
     * @param e 
     */
    EventHandler<MouseEvent> pencilCursor = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent t) {
            Image penCursor = new Image(getClass().getResourceAsStream("pencil-cursor.png"));
            canvas.setCursor(new ImageCursor(penCursor));
        }
        
    };
    
     /**
     * This event handler sets the default cursor image to eraser image
     * @param e 
     */
    EventHandler<MouseEvent> eraserCursor = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent t) {
            Image eraserCursor = new Image(getClass().getResourceAsStream("eraser-cursor.png"));
            canvas.setCursor(new ImageCursor(eraserCursor));
        }
        
    };
    
     /**
     * This event handler sets the x and y coordinates where the mouse is pressed for drawing lines
     * It also has error handling for when stroke width is less than 1  
     */
    EventHandler<MouseEvent> img_freeHandPressHandler = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent t) {
            startX = t.getX();
            startY = t.getY();
            lines = new ArrayList<>();
            line = new Line();
            line.setX(startX);
            line.setY(startY);
            line.setStrokeColor(cp_outline.getValue());
            try{
            line.setStrokeWidth(Double.parseDouble(txt_strokeWidth.getText()));
            }catch(NumberFormatException ex){
                txt_strokeWidth.setText("1.0");
                txt_strokeWidth.requestFocus();
               new Alert(Alert.AlertType.WARNING, "Please, Enter a number").showAndWait();
            }
            catch(IllegalArgumentException ex){
                txt_strokeWidth.setText("1.0");
                txt_strokeWidth.requestFocus();
                new Alert(Alert.AlertType.WARNING, "Stroke width cannot be less than 1").showAndWait();
            }
        } 
    };
    
    /**
     * This event handler is for drawing lines on mouse drag and stores all the lines created on 1 mouse drag in a separate arraylist
     */
    EventHandler<MouseEvent> img_freeHandDragHandler = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent t) {
            double x = t.getX();
            double y = t.getY();           
            line.setCurrentX(x);
            line.setCurrentY(y);
            line.draw(gc);
            lines.add(line);
            line = new Line();
            line.setX(x);
            line.setY(y);
            line.setStrokeColor(cp_outline.getValue());
            line.setStrokeWidth(Double.parseDouble(txt_strokeWidth.getText()));
        }
    };
    
    /**
     * This event handler sets the ending x&y-axis location and stores arraylist of lines in another arraylist upon mouse release 
     */
    EventHandler<MouseEvent> img_freeHandReleaseHandler = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent t) {
            
            line.setCurrentX(t.getX());
            line.setCurrentY(t.getY());
            line.draw(gc);
            objects.add(lines);
            
            //shapes.add(line);
        }
        
    };
    
    /**
     * this event takes the request back to canvas when mouse is entered in the canvas
     */
    EventHandler<MouseEvent> canavasFocus = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent t) {
            canvas.requestFocus();
        
        }
        
    };
    
    /**
     * this event clears the text that appears at the top, once the mouse is pressed anywhere in the canvas
     */
    EventHandler<MouseEvent> lbl_textHandler = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent t) {
            lbl_text.setText("");
        
        }
        
    };
    
    /**
     * This event handler sets the x and y coordinates where the mouse is pressed for drawing rectangles
     * It also has error handling for when stroke width is less than 1 
     */
    EventHandler<MouseEvent> rectPressHandler =new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            startX  = event.getX();
            startY = event.getY();  
            showRect.setStroke(cp_outline.getValue());
            try{
            showRect.setStrokeWidth(Double.parseDouble(txt_strokeWidth.getText()));
            showRect.setVisible(true);
            shapes = new ArrayList<>();
            rect = new Rectangle();
            rect.setX(startX);
            rect.setY(startY);
            rect.setStrokeColor(cp_outline.getValue());
            rect.setStrokeWidth(Double.parseDouble(txt_strokeWidth.getText()));
            }catch(NumberFormatException ex){
                txt_strokeWidth.setText("1.0");
                txt_strokeWidth.requestFocus();
               new Alert(Alert.AlertType.WARNING, "Please, Enter a number").showAndWait();
            }
            catch(IllegalArgumentException ex){
                txt_strokeWidth.setText("1.0");
                txt_strokeWidth.requestFocus();
                new Alert(Alert.AlertType.WARNING, "Stroke width cannot be less than 1").showAndWait();
            }
            
        }
    };
    
    /**
     * this event handler draws rectangle upon mouse drag
     */
    EventHandler<MouseEvent> rectDragHandler =new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
           if(event.getX()>canvas.getWidth()){ 
               double length = canvas.getWidth() - startX;
               double height = event.getY() - startY;
               txt_setRectLength.setText(""+ length);
               txt_setRectHeight.setText(""+ height);
           }    
           else if(event.getY()>canvas.getHeight()){
               double length = event.getX() - startX;
               double height =canvas.getHeight()- startY;
               txt_setRectLength.setText(""+ length);
               txt_setRectHeight.setText(""+ height);
           }
           else {
               double length = event.getX() - startX;
               double height = event.getY() - startY;
               txt_setRectLength.setText(""+ length);
               txt_setRectHeight.setText(""+ height);     
           }
           if(showRect.isVisible() && event.getX()<canvas.getWidth()&& event.getY()<canvas.getHeight()&& Double.parseDouble(txt_setRectLength.getText())>=0 && Double.parseDouble(txt_setRectHeight.getText())>=0){
                showRect.setTranslateX(startX-0.5);
                showRect.setTranslateY(startY-0.5);
                showRect.setWidth(event.getX() - startX+1);
                showRect.setHeight(event.getY() - startY+1);
                showRect.setFill(cp_fill.getValue());
            } 
           else if(showRect.isVisible() && event.getX()<canvas.getWidth()&& event.getY()<canvas.getHeight() && Double.parseDouble(txt_setRectLength.getText())<0 && Double.parseDouble(txt_setRectHeight.getText())<0){ 
                showRect.setTranslateX( event.getX()-0.5);
                showRect.setTranslateY(event.getY()-0.5); 
                showRect.setWidth(- (event.getX() - startX)+1);
                showRect.setHeight(- (event.getY() - startY)+1);
                showRect.setFill(cp_fill.getValue());   
            }
           else if(showRect.isVisible() && event.getX()<canvas.getWidth()&& event.getY()<canvas.getHeight() &&  Double.parseDouble(txt_setRectLength.getText())<0 && Double.parseDouble(txt_setRectHeight.getText())>=0){
                showRect.setTranslateX(event.getX()-0.5 );
                showRect.setTranslateY(startY-0.5);      
                showRect.setWidth(- (event.getX() - startX)+1);
                showRect.setHeight((event.getY() - startY)+1);
                showRect.setFill(cp_fill.getValue());   
            }
           else if(showRect.isVisible() && event.getX()<canvas.getWidth()&& event.getY()<canvas.getHeight() &&  Double.parseDouble(txt_setRectLength.getText())>=0 && Double.parseDouble(txt_setRectHeight.getText())<0){
                showRect.setTranslateX(startX-0.5);
                showRect.setTranslateY(event.getY()-0.5);
                showRect.setWidth((event.getX() - startX)+1);
                showRect.setHeight(- (event.getY() - startY)+1);
                showRect.setFill(cp_fill.getValue());
            }
          
        }
    };
    
    /**
     * this event handler sets the length and height of rectangle and adds the rectangle to an arraylist
     */
    EventHandler<MouseEvent> rectReleaseHandler =new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            showRect.setVisible(false);
            showRect.setWidth(0);
            showRect.setHeight(0);
            try{
                if(Double.parseDouble(txt_setRectLength.getText())<0 && Double.parseDouble(txt_setRectHeight.getText())<0){
                    rect.setX(event.getX());
                    rect.setY(event.getY());
                    rect.setLength(- (event.getX() - startX));
                    rect.setWidth(- (event.getY() - startY));
                    rect.setColor(cp_fill.getValue());
                    rect.draw(gc);
                    rect.draw(gc);
                    shapes.add(rect);
                    objects.add(shapes);
                }            
                else if(Double.parseDouble(txt_setRectLength.getText())<0 && Double.parseDouble(txt_setRectHeight.getText())>=0){
                    rect.setX(event.getX());
                    rect.setY(startY);
                    rect.setLength(- (event.getX() - startX));
                    rect.setWidth((event.getY() - startY));
                    rect.setColor(cp_fill.getValue());
                    rect.draw(gc);
                    rect.draw(gc);
                    shapes.add(rect);
                    objects.add(shapes);
                }
                else if(Double.parseDouble(txt_setRectLength.getText())>=0 && Double.parseDouble(txt_setRectHeight.getText())<0){
                    rect.setX(startX);
                    rect.setY(event.getY());
                    rect.setLength((event.getX() - startX));
                    if(event.getX()>canvas.getWidth()){
                        rect.setLength((canvas.getWidth() - startX-5));
                        if(rect.getStrokeWidth()>5){
                            double hSpacing = rect.getStrokeWidth() - 5;
                            rect.setLength(canvas.getWidth() - startX-5-hSpacing);
                        }
                    }
                    rect.setWidth(- (event.getY() - startY));
                    rect.setColor(cp_fill.getValue());
                    rect.draw(gc);
                    rect.draw(gc);
                    shapes.add(rect);
                    objects.add(shapes);
                }
                else{
                    if(event.getX()>canvas.getWidth()){ 
                        rect.setStrokeColor(cp_outline.getValue());
                        rect.setLength(Double.parseDouble(txt_setRectLength.getText())-5);
                        if(rect.getStrokeWidth()>5){
                            double hSpacing = rect.getStrokeWidth() - 5;
                            rect.setLength(Double.parseDouble(txt_setRectLength.getText())-5-hSpacing);
                        }
                        rect.setWidth(Double.parseDouble(txt_setRectHeight.getText()));
                        rect.setColor(cp_fill.getValue());
                        rect.draw(gc);
                        rect.draw(gc);
                        shapes.add(rect);
                        objects.add(shapes);
                    }       
                    else if(event.getY()>canvas.getHeight()){
                        rect.setStrokeColor(cp_outline.getValue());
                        rect.setLength(Double.parseDouble(txt_setRectLength.getText()));
                        rect.setWidth(Double.parseDouble(txt_setRectHeight.getText())-5);
                        if(rect.getStrokeWidth()>5){
                            double vSpacing = rect.getStrokeWidth() - 5;
                            rect.setWidth(Double.parseDouble(txt_setRectHeight.getText())-5-vSpacing);
                        }
                        rect.setColor(cp_fill.getValue());
                        rect.draw(gc);
                        rect.draw(gc);
                        shapes.add(rect);
                        objects.add(shapes);
                    }      
                    else{
                        rect.setLength(Math.abs(Double.parseDouble(txt_setRectLength.getText())) );
                        rect.setWidth(Math.abs(Double.parseDouble(txt_setRectHeight.getText())));
                        rect.setColor(cp_fill.getValue());
                        rect.draw(gc);
                        rect.draw(gc);
                        shapes.add(rect);  
                        objects.add(shapes);
                    }
                }
           }catch(NumberFormatException ex){
               txt_setRectLength.setText("10");
               txt_setRectHeight.setText("10");
               new Alert(Alert.AlertType.WARNING, "Please, Enter a number").showAndWait();
            }
        }
    };
    
    /**
     * This event handler sets the x and y coordinates where the mouse is pressed for drawing ellipses
     * It also has error handling for when stroke width is less than 1 
     */
    EventHandler<MouseEvent> circlePressHandler =new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            startX  = event.getX();
            startY = event.getY();
            showEllipse.setTranslateX(-1);
            showEllipse.setTranslateY(-1);
            showEllipse.setStroke(cp_outline.getValue());
            try{
            showEllipse.setStrokeWidth(Double.parseDouble(txt_strokeWidth.getText()));
            showEllipse.setVisible(true);
            shapes = new ArrayList<GeometricObject>();
            ellipse = new Ellipse();
            ellipse.setX(startX);
            ellipse.setY(startY);
            ellipse.setStrokeColor(cp_outline.getValue());   
            
            ellipse.setStrokeWidth(Double.parseDouble(txt_strokeWidth.getText())); 
            }catch(NumberFormatException ex){
                txt_strokeWidth.setText("1.0");
                txt_strokeWidth.requestFocus();
               new Alert(Alert.AlertType.WARNING, "Please, Enter a number").showAndWait();
            }
            catch(IllegalArgumentException ex){
                txt_strokeWidth.setText("1.0");
                txt_strokeWidth.requestFocus();
                new Alert(Alert.AlertType.WARNING, "Stroke width cannot be less than 1").showAndWait();
            }
        }
    };
   
     /**
     * this event handler draws ellipse upon mouse drag
     */
    EventHandler<MouseEvent> circleDragHandler =new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if(event.getX()>canvas.getWidth()){ 
               double length = canvas.getWidth() - startX;
               double height = event.getY() - startY;
               txt_setEllipseLength.setText(""+ length);
               txt_setEllipseHeight.setText(""+ height);
           }
           else if(event.getY()>canvas.getHeight()){
               double length = event.getX() - startX;
               double height =canvas.getHeight()- startY;
               txt_setEllipseLength.setText(""+ length);
               txt_setEllipseHeight.setText(""+ height);
           }
           else {
               double length = event.getX() - startX;
               double height = event.getY() - startY;
               txt_setEllipseLength.setText(""+ length);
               txt_setEllipseHeight.setText(""+ height);
           }
           if(showEllipse.isVisible() && event.getX()<canvas.getWidth()&& event.getY()<canvas.getHeight()&& Double.parseDouble(txt_setEllipseLength.getText())>=0 && Double.parseDouble(txt_setEllipseHeight.getText())>=0){
                showEllipse.setTranslateX((startX-0.5));
                showEllipse.setTranslateY(startY-0.5);
                showEllipse.setRadiusX((event.getX() - startX+1));
                showEllipse.setRadiusY((event.getY() - startY+1));
                showEllipse.setFill(cp_fill.getValue());
            }
           else if(showEllipse.isVisible() && event.getX()<canvas.getWidth()&& event.getY()<canvas.getHeight()&& Double.parseDouble(txt_setEllipseLength.getText())<0 && Double.parseDouble(txt_setEllipseHeight.getText())<0){
                showEllipse.setTranslateX( event.getX()-0.5 );
                showEllipse.setTranslateY(event.getY()-0.5);           
                showEllipse.setRadiusX(- (event.getX() - startX)+1);
                showEllipse.setRadiusY(- (event.getY() - startY)+1);           
                showEllipse.setFill(cp_fill.getValue()); 
            }
           else if(showEllipse.isVisible() && event.getX()<canvas.getWidth()&& event.getY()<canvas.getHeight()&& Double.parseDouble(txt_setEllipseLength.getText())<0 && Double.parseDouble(txt_setEllipseHeight.getText())>=0){
                showEllipse.setTranslateX(event.getX()-0.5 );
                showEllipse.setTranslateY(startY-0.5);
                showEllipse.setRadiusX(- (event.getX() - startX)+1);
                showEllipse.setRadiusY((event.getY() - startY)+1);
                showEllipse.setFill(cp_fill.getValue());    
            }
           else if(showEllipse.isVisible() && event.getX()<canvas.getWidth()&& event.getY()<canvas.getHeight()&& Double.parseDouble(txt_setEllipseLength.getText())>=0 && Double.parseDouble(txt_setEllipseHeight.getText())<0){
                showEllipse.setTranslateX(startX-0.5);
                showEllipse.setTranslateY(event.getY()-0.5);
                showEllipse.setRadiusX((event.getX() - startX)+1);
                showEllipse.setRadiusY(- (event.getY() - startY)+1);
                showEllipse.setFill(cp_fill.getValue());  
            }
            
        }
    };
    
    /**
     * this event handler sets the length and height of ellipse and adds the ellipse to an arraylist
     */
    EventHandler<MouseEvent> circleReleaseHandler =new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            showEllipse.setVisible(false);
            showEllipse.setRadiusX(0);
            showEllipse.setRadiusY(0);
            try{            
                if(Double.parseDouble(txt_setEllipseLength.getText())<0 && Double.parseDouble(txt_setEllipseHeight.getText())<0){
                    ellipse.setLength(- ((event.getX() - startX)*2));
                    ellipse.setWidth(- ((event.getY() - startY)*2));
                    ellipse.setX(event.getX()-(ellipse.getLength()/2));
                    ellipse.setY(event.getY()-(ellipse.getWidth()/2));
                    ellipse.setColor(cp_fill.getValue());
                    ellipse.draw(gc);
                    ellipse.draw(gc);
                    ellipse.draw(gc);
                    ellipse.draw(gc);
                    shapes.add(ellipse);
                    objects.add(shapes);
                }
                else if(Double.parseDouble(txt_setEllipseLength.getText())<0 && Double.parseDouble(txt_setEllipseHeight.getText())>=0){
                    ellipse.setLength(- ((event.getX() - startX)*2));
                    ellipse.setWidth((event.getY() - startY)*2);
                    ellipse.setX(event.getX()-(ellipse.getLength()/2));
                    ellipse.setY(startY-(ellipse.getWidth()/2));
                    ellipse.setColor(cp_fill.getValue());
                    ellipse.draw(gc);
                    ellipse.draw(gc);
                    ellipse.draw(gc);
                    ellipse.draw(gc);
                    shapes.add(ellipse);
                    objects.add(shapes);
                }
                else if(Double.parseDouble(txt_setEllipseLength.getText())>=0 && Double.parseDouble(txt_setEllipseHeight.getText())<0){
                    ellipse.setLength((event.getX() - startX)*2);
                    ellipse.setWidth(- ((event.getY() - startY)*2));
                    ellipse.setX(startX-(ellipse.getLength()/2));
                    if(event.getX()>canvas.getWidth()){ 
                        double extraX = event.getX()-canvas.getWidth();
                        ellipse.setX(startX-(ellipse.getLength()/2)-extraX-5);
                        if(ellipse.getStrokeWidth()>5){
                            double vSpacing = ellipse.getStrokeWidth() - 5;
                            ellipse.setX(startX-(ellipse.getLength()/2)-extraX-5-vSpacing);
                        }
                    }
                    ellipse.setY(event.getY()-(ellipse.getWidth()/2));
                    ellipse.setColor(cp_fill.getValue());
                    ellipse.draw(gc);
                    ellipse.draw(gc);
                    ellipse.draw(gc);
                    ellipse.draw(gc);
                    shapes.add(ellipse);
                    objects.add(shapes);
                }
                else{  
                    if(event.getX()>canvas.getWidth()){ 
                        ellipse.setStrokeColor(cp_outline.getValue());
                        ellipse.setLength((Double.parseDouble(txt_setEllipseLength.getText())-5)*2);
                        if(ellipse.getStrokeWidth()>5){
                            double hSpacing = ellipse.getStrokeWidth() - 5;
                            ellipse.setLength((Double.parseDouble(txt_setEllipseLength.getText())-5-hSpacing)*2);
                        }
                        ellipse.setX(startX-(ellipse.getLength()/2));
                        ellipse.setY(startY-(ellipse.getWidth()/2));
                        ellipse.setWidth(Double.parseDouble(txt_setEllipseHeight.getText())*2);
                        ellipse.setColor(cp_fill.getValue());
                        ellipse.draw(gc);
                        ellipse.draw(gc);
                        ellipse.draw(gc);
                        ellipse.draw(gc);
                        shapes.add(ellipse);
                        objects.add(shapes);
                    }

                    else if(event.getY()>canvas.getHeight()){
                        ellipse.setStrokeColor(cp_outline.getValue());
                        ellipse.setLength(Double.parseDouble(txt_setEllipseLength.getText())*2);
                        ellipse.setWidth((Double.parseDouble(txt_setEllipseHeight.getText())-5)*2);
                        if(ellipse.getStrokeWidth()>5){
                            double vSpacing = ellipse.getStrokeWidth() - 5;
                            ellipse.setWidth((Double.parseDouble(txt_setEllipseHeight.getText())-5-vSpacing)*2);
                        }
                        ellipse.setX(startX-(ellipse.getLength()/2));
                        ellipse.setY(startY-(ellipse.getWidth()/2));
                        ellipse.setColor(cp_fill.getValue());
                        ellipse.draw(gc);
                        ellipse.draw(gc);
                        ellipse.draw(gc);
                        ellipse.draw(gc);
                        shapes.add(ellipse);
                        objects.add(shapes);
                    }
                    else{
                        ellipse.setLength(Math.abs(Double.parseDouble(txt_setEllipseLength.getText()))*2);
                        ellipse.setWidth(Math.abs(Double.parseDouble(txt_setEllipseHeight.getText()))*2);
                        ellipse.setX(startX-(ellipse.getLength()/2));
                        ellipse.setY(startY-(ellipse.getWidth()/2));
                        ellipse.setColor(cp_fill.getValue());
                        ellipse.draw(gc);
                        ellipse.draw(gc);
                        ellipse.draw(gc);
                        ellipse.draw(gc);
                        shapes.add(ellipse);
                        objects.add(shapes);
                   }
                }
            }catch(NumberFormatException ex){
                txt_setEllipseLength.setText("10");
                txt_setEllipseHeight.setText("10");
               new Alert(Alert.AlertType.WARNING, "Please, Enter a number").showAndWait();
           }
        }
    };
    
    /**
     * this event handler creates a new arraylist to store EraserRectangles when mouse is pressed
     */
    EventHandler<MouseEvent> img_eraserPressHandler = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent t) {
            eraserRect = new ArrayList<>();
        }    
    };
    
    /**
     * this event handler draws multiple rectangles of fixed length and height(20px)upon mouse drag
     * and adds the rectangles to an arraylist 
     */
    EventHandler<MouseEvent> img_eraserDragHandler =new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            eraserRec = new EraserRectangle();
            eraserRec.setColor(Color.IVORY);
            eraserRec.setX(event.getX());
            eraserRec.setY(event.getY());
            eraserRec.setLength(20);
            eraserRec.setWidth(20);
            eraserRec.draw(gc);
            eraserRect.add(eraserRec);            
        }
     };
    
    /**
     * this event handler adds the arraylist containing eraser rectangles to another arraylist when mouse is released
     */
    EventHandler<MouseEvent> img_eraserReleaseHandler = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent t) {
            objects.add(eraserRect);
        }
    };
    
    /**
     * this event handler is used for undo function. It removes the last object from the array list
     * then clears the whole canvas and then redraws all the elements in the arraylist
     */
    EventHandler<KeyEvent> undo = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if(event.isControlDown()){
                if (event.getCode() == KeyCode.Z) {
                    try{
                    objects.remove(objects.size()-1);
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    gc.setFill(Color.IVORY);
                    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    for(int j = 0; j<objects.size();j++){
                        ArrayList<GeometricObject> a = objects.get(j);
                        if(a.get(0)instanceof Rectangle){
                            a.get(0).draw(gc);
                            a.get(0).draw(gc);
                        }
                        else if(a.get(0)instanceof Ellipse){
                            a.get(0).draw(gc);
                            a.get(0).draw(gc);
                            a.get(0).draw(gc);
                            a.get(0).draw(gc);
                        }
                        else if(a.get(0)instanceof EraserRectangle){
                            for(int k = 0; k<a.size();k++){
                                a.get(k).draw(gc);
                            }
                        }
                        else{
                            for(int k = 0; k<a.size();k++){
                                a.get(k).draw(gc);
                            }
                        }      
                    }
                }catch(ArrayIndexOutOfBoundsException ex){
                    new Alert(Alert.AlertType.WARNING, "There are no more items to undo").showAndWait();
                  }
                }
            }
        }
        
    };
    
    /**
     * This is where you create your components and the model and add event
     * handlers.
     *
     * @param stage The main stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        root = new Pane();
        Scene scene = new Scene(root, 1100, 800); // set the size here
        stage.setTitle("Assignment 8"); // set the window title here
        stage.setScene(scene);
      
        // TODO: Add your GUI-building code here

        // 1. Create the model
        line = new Line();
        rect = new Rectangle();
        eraserRec = new EraserRectangle();
        showRect = new javafx.scene.shape.Rectangle(0, 0, 0, 0);
        showEllipse = new javafx.scene.shape.Ellipse(0,0,0,0);
        ellipse = new Ellipse();
        objects = new ArrayList<>();
        shapes = new ArrayList<>();
        lines = new ArrayList<>();
        eraserRect = new ArrayList<>();
        // 2. Create the GUI components
        canvas = new Canvas(700, 800);
        Image recta = new Image(getClass().getResourceAsStream("rectan.png"));
        img_rect = new ImageView(recta);
        Image circle = new Image(getClass().getResourceAsStream("ellip.png"));
        img_circle = new ImageView(circle);
        Image pencil = new Image(getClass().getResourceAsStream("giphy.gif"));
        img_freeHand = new ImageView(pencil);
        Image eraser = new Image(getClass().getResourceAsStream("eraser.png"));
        img_eraser = new ImageView(eraser);
        lbl_text = new Label("Click or Drag to Draw");
        lbl_text.setStyle("-fx-font-family: 'Old English Text MT'; -fx-font-size: 24px;");
        lbl_outline = new Label("Outline");
        lbl_fill = new Label("Fill");
        lbl_strokeWidth = new Label ("Set Stroke Width");
        lbl_undo = new Label("Press \"ctrl + z\" to Undo");
        lbl_undo.setStyle("-fx-border-color:black; -fx-font-size:24px;");
        cp_outline = new ColorPicker();
        cp_outline.setValue(Color.BLACK);
        cp_fill = new ColorPicker();
        txt_strokeWidth = new TextField("1.0");
        // 3. Add components to the root
        root.getChildren().addAll(canvas,img_freeHand,img_eraser,img_rect,img_circle,lbl_text,lbl_outline,lbl_fill,lbl_strokeWidth,lbl_undo,
                cp_outline,cp_fill,txt_strokeWidth, showRect, showEllipse);
        // 4. Configure the components (colors, fonts, size, location)
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.IVORY);
        gc.fillRect(0, 0, 700, 800);
        txt_strokeWidth.setPrefWidth(50);
        img_rect.relocate(725, 175);
        img_circle.relocate(925, 175);
        img_freeHand.relocate(725, 20);
        img_eraser.relocate(925, 20);
        lbl_text.relocate(240, 30);
        lbl_outline.relocate(775, 325);
        cp_outline.relocate(850, 325);
        lbl_fill.relocate(775, 400);
        cp_fill.relocate(850, 400);
        lbl_strokeWidth.relocate(775, 475);
        txt_strokeWidth.relocate(925, 475);
        lbl_undo.relocate(775, 550);
        // 5. Add Event Handlers and do final setup
        img_rect.setOnMouseClicked(this::btn_rectHandler);
        img_circle.setOnMouseClicked(this::btn_circleHandler);
        img_freeHand.setOnMouseClicked(this::img_freeHandHandler);
        img_eraser.setOnMouseClicked(this::img_eraserHandler);
        root.setOnKeyPressed(undo);
        canvas.setOnMouseEntered(canavasFocus);
        canvas.setOnMousePressed(lbl_textHandler);
        // 6. Show the stage
        stage.show();
    }

    /**
     * Make no changes here.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        launch(args);
    }
}
