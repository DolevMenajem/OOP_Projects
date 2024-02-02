//207323411 Itamar Shapira
//207272220 Dolev Menajem

import java.util.Random;

/**
 * The Point class represents a point in a 2D space with double precision coordinates.
 * Each point has x and y coordinates, and provides methods for distance calculation,
 * equality comparison, and manipulation of its coordinates.
 */
public class Point {
    private double x;
    private double y;

   /**
     * Constructs a Point with the specified double x and y coordinates.
     *
     * @param x the x-coordinate of the point as a double.
     * @param y the y-coordinate of the point as a double.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Generates a random Point within the specified bounds.
     * @param xstart the starting x-coordinate of the bounding box.
     * @param ystart the starting y-coordinate of the bounding box.
     * @param xbound the upper x-coordinate limit of the bounding box.
     * @param ybound the upper y-coordinate limit of the bounding box.
     * @return a Point object with random x and y coordinates within the specified bounds.
     */
    public static Point randomPoint(int xstart, int ystart, int xbound, int ybound) {
        Random rand = new Random(); // create a random-number generator

        // Calculate random x-coordinate within the specified bounds
        int randomX = (int) (rand.nextDouble() * (xbound - xstart) + xstart);

        // Calculate random y-coordinate within the specified bounds
        int randomY = (int) (rand.nextDouble() * (ybound - ystart) + ystart);

        // Return a new Point with random coordinates
        return new Point(randomX, randomY);
    }

     /**
     * Calculates the distance between this point and another point.
     *
     * @param other the other Point to calculate the distance to.
     * @return the distance between this point and the other point.
     */
   public double distance(Point other) {
    // Calculate the square of the differences in x and y coordinates, sum them, and take the square root
    return Math.sqrt(Math.pow(this.x - other.getX(), 2) + Math.pow(this.y - other.getY(), 2));
    }

    /**
     * Checks if this point is equal to another point.
     *
     * @param other the other Point to compare with.
     * @return true if the points are equal, false otherwise.
     */
    public boolean equals(Point other) {
        if (Math.abs(this.x - other.getX()) <= 0.000001
            && Math.abs(this.y - other.getY()) <= 0.000001) {
            return true;
           }
        return false;
    }

    /**
     * Gets the x-coordinate of this point.
     *
     * @return the x-coordinate of this point.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Gets the y-coordinate of this point.
     *
     * @return the y-coordinate of this point.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Sets the x-coordinate of this point to the specified value.
     *
     * @param x the new x-coordinate.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of this point to the specified value.
     *
     * @param y the new y-coordinate.
     */
    public void setY(double y) {
        this.y = y;
    }
}