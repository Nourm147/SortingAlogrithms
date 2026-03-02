package com.Visualizations;

import javafx.scene.shape.Rectangle;

public class Bar extends  Rectangle {
    public Bar(double height, double width, double xpos, double ypos)
    {
        this.setHeight(height);
        this.setWidth(width);
        this.setX(xpos);
        this.setY(ypos);
        
        // TODO: add some styles to the bar
    }
}
