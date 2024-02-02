//207323411 Itamar Shapira
//207272220 Dolev Menajem

import java.util.Random;
import java.util.List;

/**
 * The Line class represents a line segment in a 2D space defined by two endpoints.
 * It provides methods for calculating the length, middle point, start and end points,
 * checking intersection with other lines, finding intersection points, equality comparison,
 * and generating random lines within specified coordinate space.
 */
public class Line {
    private Point start;
    private Point end;
    private double gradient;
    private double b;
    private boolean vertical;

    /**
     * Constructs a Line with two specified points as endpoints.
     * The constructor automatically determines which point is the start and which is the end.
     * It also calculates line characteristics such as gradient and y-intercept (if applicable).
     *
     * @param start the starting point of the line.
     * @param end the ending point of the line.
     */
    public Line(Point start, Point end) {
        // Ensure start is the leftmost point
        if (start.getX() < end.getX()) {
            this.start = start;
            this.end = end;
        } else {
            this.start = end;
            this.end = start;
        }

        // Check if the line is vertical
        if (start.getX() != end.getX()) {
            this.vertical = false;
            this.gradient = ((end.getY() - start.getY()) / (end.getX() - start.getX()));
            this.b = (-this.gradient * start.getX()) + start.getY();
        } else {
            // Handle vertical line
            this.vertical = true;
            this.gradient = 0;
            this.b = 0;
        }
    }

    /**
     * Constructs a Line with specified coordinates for the start and end points.
     * The constructor automatically determines which point is the start and which is the end.
     * It also calculates line characteristics such as gradient and y-intercept (if applicable).
     *
     * @param x1 the x-coordinate of the starting point.
     * @param y1 the y-coordinate of the starting point.
     * @param x2 the x-coordinate of the ending point.
     * @param y2 the y-coordinate of the ending point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        // Create Point objects for start and end points
        Point p1 = new Point(x1, y1);
        Point p2 = new Point(x2, y2);

        // Handle vertical line
        if (x1 == x2) {
            this.vertical = true;
            this.gradient = 0;
            this.b = 0;

            // Determine start and end points for vertical line
            if (y1 < y2) {
                this.start = p1;
                this.end = p2;
            } else {
                this.start = p2;
                this.end = p1;
            }
            return;
        }

        // Ensure start is the leftmost point
        if (x1 < x2) {
            this.start = p1;
            this.end = p2;
        } else {
            this.start = p2;
            this.end = p1;
        }

        // Calculate line characteristics for non-vertical line
        this.vertical = false;
        this.gradient = ((end.getY() - start.getY()) / (end.getX() - start.getX()));
        this.b = (-this.gradient * start.getX()) + start.getY();
}

    /**
     * Returns the length of the line segment.
     *
     * @return the length of the line.
     */
    public double length() {
    return this.start.distance(end);
    }

    /**
     * Returns the middle point of the line.
     *
     * @return the middle point of the line.
     */
    public Point middle() {
    double x = this.start.getX() + (this.end.getX() - this.start.getX()) / 2;
    double y = this.start.getY() + (this.end.getY() - this.start.getY()) / 2;
    return new Point(x, y);
    }

    /**
     * Returns the start point of the line.
     *
     * @return the start point of the line.
     */
    public Point start() {
    return this.start;
    }

    /**
     * Returns the end point of the line.
     * @return the end point of the line.
     */
    public Point end() {
    return this.end;
    }

    /**
    * Checks if the current line intersects with another line.
    * @param other the Line to check for intersection with the current line.
    * @return true if the lines intersect, false otherwise.
    */
    public boolean isIntersecting(Line other) {
        // Check if the lines have the same gradient (parallel)
        if (this.gradient == other.gradient) {
            // Check if the lines are overlapping
            if (this.start == other.end || this.end == other.start) {
                return true;
            } else {
                return false;
            }
        }

        // If one line is vertical, delegate to specialized method
        if (this.vertical) {
            return this.isIntersectingVertical(other);
        } else if (other.vertical) {
            return other.isIntersectingVertical(this);
        }

        // Calculate the x-coordinate of the intersection point
        double intersectX = ((other.b - this.b) / (this.gradient - other.gradient));

        // Get the corresponding y-coordinates for the intersection point on both lines
        Point p1 = this.getPointY(intersectX);
        Point p2 = other.getPointY(intersectX);

        // Check if the intersection point is within both line segments
        if (this.isPointInLine(p1) && other.isPointInLine(p2)) {
              return true;
        }

        return false;
    }

    /**
    * Checks if this line intersects with two other lines.
    * @param other1 the first Line to check for intersection with this line.
    * @param other2 the second Line to check for intersection with this line.
    * @return true if this line intersects with both other lines, false otherwise.
    */
    public boolean isIntersecting(Line other1, Line other2) {
        // Check if this line intersects with both other lines
        if (this.isIntersecting(other1) && this.isIntersecting(other2)) {
            return true;
        }
        return false;
    }

    /**
    * Finds the intersection point of this line with another line.
    * @param other the Line to find the intersection point with.
    * @return the Point representing the intersection point if lines intersect, or null otherwise.
    */
    public Point intersectionWith(Line other) {
        // Check if the lines intersect
        if (this.isIntersecting(other)) {
            // If this line is vertical, return the intersection point with the vertical line
            if (this.vertical) {
                return other.getPointY(this.start.getX());
                }  else if (other.vertical) {
                // If the other line is vertical, return the intersection point with the vertical line
                return this.getPointY(other.start.getX());
                }

            // If lines have the same gradient, check for collinear lines
            if (this.gradient == other.gradient) {
                // Return one of the endpoints as the intersection point (they are the same in this case)
                if (this.start == other.end) {
                    return this.start;
                } else {
                    return this.end;
                }
            }

            // Calculate the x-coordinate of the intersection point
            double intersectX = ((other.b - this.b) / (this.gradient - other.gradient));

            // Return the intersection point based on the calculated x-coordinate
            return this.getPointY(intersectX);
        }

        // Return null if lines do not intersect
        return null;
    }

    /**
    * Checks if this line is equal to another line.
    * @param other the Line to compare with this line.
    * @return true if the lines are equal, false otherwise.
    */
    public boolean equals(Line other) {
        // Check if the lines have the same gradient, y-intercept and length
        if (this.gradient == other.gradient
            && this.b == other.b
            && this.length() == other.length()) {
            return true;
        }
        return false;
    }

    /**
    * Gets a Point on the line for a given x-coordinate.
    * @param x the x-coordinate for which to calculate the corresponding y-coordinate.
    * @return the Point on the line with the specified x-coordinate.
    */
    public Point getPointY(double x) {
    // Calculate the y-coordinate based on the line equation (y = mx + b)
    double y = (this.gradient * x) + this.b;
    // Return a new Point with the calculated x and y coordinates
    return new Point(x, y);
}

    /**
    * Checks if a given Point lies within the line segment.
    * @param p the Point to check for inclusion in the line segment.
    * @return true if the Point is within the line segment, false otherwise.
    */
    public boolean isPointInLine(Point p) {
    // Check if the x-coordinate of the Point is within the range of the line segment
    if (p.getX() >= this.start.getX() && p.getX() <= this.end.getX()) {
        return true;
    }
    return false;
}

    /**
    * Checks if a given Point lies within the vertical line segment.
    * @param p the Point to check for inclusion in the vertical line segment.
    * @return true if the Point is within the vertical line segment, false otherwise.
    */
    public boolean isPointInlineVertical(Point p) {
    // Check if the y-coordinate of the Point is within the range of the vertical line segment
    if (p.getY() >= this.start.getY() && p.getY() <= this.end.getY()) {
       return true;
    }
    return false;
}

    /**
    * Checks if this line intersects with a given non-vertical line by delegating to a vertical line intersection check.
    * @param notVertic the non-vertical Line to check for intersection with this vertical line.
    * @return true if the lines intersect, false otherwise.
    */
    public boolean isIntersectingVertical(Line notVertic) {
    // Get the intersection Point with the non-vertical line for the vertical line's x-coordinate
    Point p = notVertic.getPointY(this.start.getX());

    // Check if the intersection Point is within both lines
    if (notVertic.isPointInLine(p) && this.isPointInlineVertical(p)) {
        return true;
    }

    return false;
}

    /**
    * Generates a random Line within a specified coordinate space.
    * @param xBound the maximum x-coordinate for the generated points.
    * @param yBound the maximum y-coordinate for the generated points.
    * @return a randomly generated Line with two endpoints within the specified coordinate space.
    */
    public static Line generateRandomLine(int xBound, int yBound) {
    Random rand = new Random(); // create a random-number generator

    // Generate random x and y coordinates for the first point within the specified coordinate space
    double x1 = rand.nextDouble() * xBound + 1;
    double y1 = rand.nextDouble() * yBound + 1;
    Point p1 = new Point(x1, y1);

    // Generate random x and y coordinates for the second point within the specified coordinate space
    double x2 = rand.nextDouble() * xBound + 1;
    double y2 = rand.nextDouble() * yBound + 1;
    Point p2 = new Point(x2, y2);

    // Return a new Line with the randomly generated endpoints
    return new Line(p1, p2);
}

    /**
     * Gets the start point of the line.
     * @return the start point of the line.
     */
    public Point getStart() {
        return this.start;
    }

    /**
     * Gets the end point of the line.
     * @return the end point of the line.
     */
    public Point getEnd() {
        return this.end;
    }

    /**
     * Gets the b value of the line.
     * @return the b value of the line.
     */
    public double getB() {
        return this.b;
    }

    /**
     * Gets the gradient value of the line.
     * @return the gradient value of the line.
     */
    public double getGradient() {
        return this.gradient;
    }

    // If this line does not intersect with the rectangle, return null.
    // Otherwise, return the closest intersection point to the
    // start of the line.
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> interPoints = rect.intersectionPoints(this);
        if(interPoints.isEmpty()) {
            return null;
        }
        Point nearest = interPoints.get(0);
        for (Point point : interPoints) {
            if(this.start.distance(nearest) > this.start.distance(point)) {
                nearest = point;
            }
        }

        return nearest;
    }
}
