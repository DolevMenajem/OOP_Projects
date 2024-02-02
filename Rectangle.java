//207323411 Itamar Shapira
//207272220 Dolev Menajem

import java.util.ArrayList;
import java.util.List;

public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;
    private Line[] borders;

    // Create a new rectangle with location and width/height.
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.borders = Rectangle.createBorders(upperLeft, width, height);
    }

    public static Line[] createBorders(Point starPoint, double width, double height) {
        double startX = starPoint.getX(), startY = starPoint.getY();
        Line[] borders = new Line[4];
        borders[0] = new Line(starPoint, new Point(startX + width, startY));
        borders[1] = new Line(startX + width, startY, startX + width, startY + height);
        borders[2] = new Line(startX, startY + height, startX + width, startY);
        borders[3] = new Line(new Point(startX, startY + height), starPoint);
        return borders;
    }
 
    // Return a (possibly empty) List of intersection points
    // with the specified line.
    public java.util.List<Point> intersectionPoints(Line line) {
        List<Point> interPoints = new ArrayList<>();
        for (Line border : this.borders) {
            if(border.isIntersecting(line)) {
                interPoints.add(border.intersectionWith(line));
            }
        }   
        return interPoints;
    }
 
    // Return the width and height of the rectangle
    public double getWidth() {
        return this.width;
    }
    public double getHeight() {
        return this.height;
    }
 
    // Returns the upper-left point of the rectangle.
    public Point getUpperLeft() {
        return this.upperLeft;
    }
 
    public Line[] getBorders() {
        return this.borders;
    }
}