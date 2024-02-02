//207323411 Itamar Shapira
//207272220 Dolev Menajem
//207272220 ori

/**
 * The Velocity class represents a 2D velocity vector with components for the horizontal and vertical directions.
 * It provides methods for constructing a velocity with given components, creating a velocity from an angle and speed,
 * and applying the velocity to a point to calculate a new position.
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
    * Constructs a new Velocity with the given components for the horizontal and vertical directions.
    * @param dx the component for the horizontal direction.
    * @param dy the component for the vertical direction.
    */
    public Velocity(double dx, double dy) {
        // Initialize the horizontal and vertical components of the Velocity
        this.dx = dx;
        this.dy = dy;
    }

    /**
    * Creates a new Velocity from a given angle and speed.
    * @param angle the angle in degrees.
    * @param speed the speed magnitude.
    * @return a new Velocity object derived from the angle and speed.
    */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        // Calculate the horizontal and vertical components based on angle and speed
        double dx = speed * Math.sin(Math.toRadians(angle));
        double dy = -(speed * Math.cos(Math.toRadians(angle)));

        // Return a new Velocity object with the calculated components
        return new Velocity(dx, dy);
     }

    /**
     * Gets the horizontal component (dx) of the velocity.
     *
     * @return the horizontal component of the velocity.
     */
    public double getdx() {
        return this.dx;
    }

    /**
     * Gets the vertical component (dy) of the velocity.
     *
     * @return the vertical component of the velocity.
     */
    public double getdy() {
        return this.dy;
    }

    /**
    * Applies the velocity to a given Point, resulting in a new Point.
    *
    * @param p the Point to which the velocity is applied.
    * @return a new Point with updated position based on the velocity.
    */
    public Point applyToPoint(Point p) {
        // Calculate the new coordinates based on the velocity components
        double newX = p.getX() + dx;
        double newY = p.getY() + dy;

        // Return a new Point with the updated position
        return new Point(newX, newY);
    }
 }