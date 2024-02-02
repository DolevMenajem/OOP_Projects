//207323411 Itamar Shapira
//207272220 Dolev Menajem

import biuoop.DrawSurface;

/**
 * The Ball class represents a simple geometric ball in a 2D space.
 * Each ball has a center point, a radius, a color, a velocity, and a bounding frame.
 * It provides methods to access and modify these properties, as well as to draw and move the ball.
 */
public class Ball {
    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity vel;
    private Line frame;

    /**
     * Constructs a new Ball with the specified center, radius, and color.
     *
     * @param center the center point of the ball.
     * @param r the radius of the ball.
     * @param color  the color of the ball.
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.vel = new Velocity(1, 1);
        this.frame = new Line(0, 0, 0, 0);
    }

    /**
     * Constructs a new Ball with the specified coordinates, radius, and color.
     *
     * @param x     the x-coordinate of the center point.
     * @param y     the y-coordinate of the center point.
     * @param r the radius of the ball.
     * @param color  the color of the ball.
     */
    public Ball(double x, double y, int r, java.awt.Color color) {
        Point center = new Point(x, y);
        this.center = center;
        this.radius = r;
        this.color = color;
        this.vel = new Velocity(1, 1);
        this.frame = new Line(0, 0, 0, 0);

    }

    /**
    * Gets the x-coordinate of the center point of the ball.
    *
    * @return the x-coordinate of the center point.
    */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Gets the y-coordinate of the center point of the ball.
     *
     * @return the y-coordinate of the center point.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Gets the center point of the ball.
     *
     * @return the center point of the ball.
     */
    public Point getCenter() {
        return this.center;
    }

    /**
     * Gets the size (radius) of the ball.
     *
     * @return the size (radius) of the ball.
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * Gets the color of the ball.
     *
     * @return the color of the ball.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Gets the velocity of the ball.
     *
     * @return the velocity of the ball as a Velocity object.
     */
    public Velocity getVelocity() {
        return this.vel;
    }

    /**
     * Gets the bounding frame of the ball.
     *
     * @return the Line representing the bounding frame of the ball.
     */
    public Line getFrame() {
        return this.frame;
    }

    /**
     * Draws the ball on the provided DrawSurface.
     * The ball is represented as a filled circle with the current color.
     *
     * @param surface the DrawSurface on which to draw the ball.
    */
    public void drawOn(DrawSurface surface) {
        //Set the current drawing color the the color of the ball
        surface.setColor(this.color);
        //Draw a filled circle with the given x and y values as the center point
        // and the size of the ball radius
        surface.fillCircle(this.getX(), this.getY(), this.radius);
    }

    /**
     * Changes the radius of the ball to the specified size.
     *
     * @param size the new radius for the ball.
     */
    public void setSize(int size) {
        this.radius = size;
    }

    /**
     * Changes the frame values of the ball to the specified coordinates and dimensions.
     *
     * @param x      the x-coordinate of the starting point of the frame.
     * @param y      the y-coordinate of the starting point of the frame.
     * @param width  the width of the frame.
     * @param height the height of the frame.
     */
    public void setFrame(int x, int y, int width, int height) {
        this.frame = new Line(x, y, x + width, y + height);
    }

    /**
     * Changes the center point of the ball to the specified point.
     *
     * @param center the new center point for the ball.
     */
    public void setCenter(Point center) {
        this.center = center;
    }

    /**
     * Sets the velocity of the ball using a Velocity object.
     *
     * @param v the new velocity for the ball.
     */
    public void setVelocity(Velocity v) {
        this.vel = v;
    }

    /**
     * Sets the velocity of the ball using the specified dx and dy values.
     *
     * @param dx the new velocity in the x-direction.
     * @param dy the new velocity in the y-direction.
     */
    public void setVelocity(double dx, double dy) {
        this.vel = new Velocity(dx, dy);
    }

    /**
    * Moves the ball one step based on its velocity and handles collisions with boundaries.
    * If a collision with the x-axis boundaries occurs, the ball's velocity is reversed along the x-axis.
    * If a collision with the y-axis boundaries occurs, the ball's velocity is reversed along the y-axis.
    */
    public void moveOneStep() {
        // Calculate the new center point based on the ball's velocity
        Point newCenter = this.getVelocity().applyToPoint(this.center);

        // Check and handle collisions with x-axis boundaries
        if ((newCenter.getX() + this.radius) >= this.frame.getEnd().getX()
            || (newCenter.getX() - this.radius) <= this.frame.getStart().getX()) {
            // Reverse the velocity along the x-axis
            this.setVelocity(-(this.vel.getdx()), this.vel.getdy());
        }

        // Check and handle collisions with y-axis boundaries
        if ((newCenter.getY() + this.radius) >= this.frame.getEnd().getY()
            || (newCenter.getY() - this.radius) <= this.frame.getStart().getY()) {
            // Reverse the velocity along the y-axis
            this.setVelocity(this.vel.getdx(), -(this.vel.getdy()));
        }

        // Update the center point based on the new velocity
        this.center = this.getVelocity().applyToPoint(this.center);
    }

    /**
    * Validates the position of the ball in relation to an inner frame and handles collisions if necessary.
    * If the ball is inside the inner frame, it checks for collisions with the frame boundaries.
    * If a collision occurs along the x-axis, the ball's velocity is reversed along the x-axis.
    * If a collision occurs along the y-axis, the ball's velocity is reversed along the y-axis.
    * The ball's center is updated based on the adjusted velocity.
    * @param innerFrame the Line representing the inner frame boundaries.
    */
    public void validateInnerFrame(Line innerFrame) {
        double x = this.center.getX();
        double y = this.center.getY();
        boolean impact = false;

        // Check if the ball is inside the inner frame
        if (this.isBallInFrame(innerFrame)) {
            // Check and handle collisions with x-axis boundaries of the inner frame
            if (this.isNearLine(innerFrame.getEnd().getX(), x, this.getVelocity().getdx())
                || this.isNearLine(innerFrame.getStart().getX(), x, this.getVelocity().getdx())) {
                // Reverse the velocity along the x-axis
                this.setVelocity(-(this.vel.getdx()), this.vel.getdy());
                impact = true;
            }

            // Check and handle collisions with y-axis boundaries of the inner frame
            if (this.isNearLine(innerFrame.getEnd().getY(), y, this.getVelocity().getdy())
                || this.isNearLine(innerFrame.getStart().getY(), y, this.getVelocity().getdy())) {
                // Reverse the velocity along the y-axis
                this.setVelocity(this.vel.getdx(), -(this.vel.getdy()));
                impact = true;
            }

            // Update the center point based on the adjusted velocity
            if (impact) {
                this.center = this.getVelocity().applyToPoint(this.center);
            }
        }
    }

    /**
    * Generates a random Point within the boundaries of the ball's frame.
    * The generated Point is guaranteed to be within the frame and avoids overlap with the ball's radius.
    * @return a randomly generated Point within the ball's frame.
    */
    public Point getPointInFrame() {
        // Generate a random Point within the frame, avoiding overlap with the ball's radius
        return Point.randomPoint(
            (int) this.frame.getStart().getX() + this.radius,
            (int) this.frame.getStart().getY() + this.radius,
            (int) this.frame.getEnd().getX() - this.radius,
            (int) this.frame.getEnd().getY() - this.radius
        );
    }

    /**
    * Checks if the ball, considering its updated position based on velocity, is within the given frame.
    * @param frame the Line representing the frame boundaries.
    * @return true if the ball is within the frame, false otherwise.
    */
    public boolean isBallInFrame(Line frame) {
        double rad = this.getSize();
        // Get the future x and y values of the ball center
        double x = this.center.getX() + this.getVelocity().getdx();
        double y = this.center.getY() + this.getVelocity().getdy();

        // Check if the ball is within the frame boundaries
        if (x + rad >= frame.getStart().getX()
            && x - rad <= frame.getEnd().getX()
            && y + rad >= frame.getStart().getY()
            && y - rad <= frame.getEnd().getY()) {
            return true;
        }
        return false;
    }

    /**
    * Generates a random Point within the outer frames while avoiding overlap with inner frames.
    * If the ball's initial position is within inner frames, it adjusts the ball's size and retries.
    * @param frames an array of Lines representing the outer frames.
    * @return a randomly generated Point within the outer frames, avoiding overlap with inner frames.
    */
    public Point getPointInOuterFrame(Line[] frames) {
        boolean redo = false;
        int counter = 0;
        Point p = new Point(0, 0);

        do {
            redo = false;

            // If the ball's initial position is within inner frames after a few attempts, reduce its size
            if (counter == 4) {
                this.setSize((int) (this.getSize() * 0.8));
            }

            // Generate a random point within the frame and set the ball's center to that point
            p = this.getPointInFrame();
            this.setCenter(p);

            // Check if the ball is within inner frames and needs to be repositioned
            if (this.isBallInFrame(frames[0]) || this.isBallInFrame(frames[1])) {
               redo = true;
               counter++;
            }
        } while (redo);

        return p;
    }

    /**
    * Checks if a point (xy) is near a given border, considering a velocity component (veldxy).
    * @param border the border value to check proximity to.
    * @param xy the current coordinate value.
    * @param veldxy the velocity component for the coordinate.
    * @return true if the point is near the border, false otherwise.
    */
    public boolean isNearLine(double border, double xy, double veldxy) {
        // Check if the point is within a certain distance from the border, considering velocity
        if (Math.abs(border - (xy + veldxy)) < this.getSize()
            && Math.abs(border - (xy - veldxy)) > this.getSize()) {
            return true;
        }
        return false;
    }

    /**
     * Normalizes the size of the ball based on the specified parameters.
     * It adjusts the size to prevent it from being too large compared to the given frame length.
     * If the size is greater than or equal to twice the frame length, it reduces the size by a factor of 4.
     * After 5 attempts, if the size is still not within bounds, it sets the size to a default value.
     *
     * @param size        the original size of the ball.
     * @param frameLength the length of the frame to compare with the size.
     * @return the normalized size of the ball.
     */
    public static int normalizeSize(int size, int frameLength) {
        boolean redo = true;
        int counter = 0;
        int defSize = 50;

        do {
            redo = true;

            // If after 5 attempts the size is still not within bounds, set it to the default size
            if (counter == 5) {
                size = defSize;
                redo = false;
            }

            // If the size is greater than or equal to twice the frame length, reduce it by a factor of 4
            if (size * 2 >= frameLength) {
                size = size / 4;
            } else {
                redo = false;
            }

            counter++;
        } while (redo);

        return size;
    }

}