//207323411 Itamar Shapira
//207272220 Dolev Menajem

import java.util.Random;
import java.awt.Color;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * The MultipleBouncingBallsAnimation class creates an animation
 * with multiple bouncing balls on a GUI window.
 */
public class MultipleBouncingBallsAnimation {
    /**
     * The main method of the MultipleBouncingBallsAnimation program.
     *
     * @param args an array of integers representing the sizes of the balls.
     */
    public static void main(String[] args) {
        int[] sizes = new int[args.length];
        // Parse size of balls
        for (int i = 0; i < args.length; i++) {
            sizes[i] = Integer.parseInt(args[i]);
        }

        multipleBallsAnimation(sizes);
    }

    /**
    * Creates an animation with multiple bouncing balls on a GUI window.
    *
    * @param sizes an array of integers representing the sizes of the balls.
    */
    public static void multipleBallsAnimation(int[] sizes) {
    // Create a GUI window titled "title" with dimensions 600x600 pixels.
    GUI gui = new GUI("Multiple Balls Animation", 600, 600);
    DrawSurface d = gui.getDrawSurface();
    Sleeper sleeper = new Sleeper();
    int defSpeed = 3;
    Random random = new Random();
    Ball[] balls = new Ball[sizes.length];

    // Create an array of balls with the given size and random velocities
    for (int i = 0; i < sizes.length; i++) {
        // Create a random color
        Color randomColor = generateRandomColor();
        // Initialize the ball
        balls[i] = new Ball(new Point(0, 0), sizes[i], randomColor);
        // Create the bounding frame for the ball
        balls[i].setFrame(0, 0, d.getWidth(), d.getHeight());

        double frameLength = balls[i].getFrame().getEnd().getX()
                             - balls[i].getFrame().getStart().getX();
        // Normalize size of the ball compared to the size of the bounding frame
        balls[i].setSize(Ball.normalizeSize(sizes[i], (int) frameLength));
        // Get a point in the frame to create the ball, considering its size
        balls[i].setCenter(balls[i].getPointInFrame());

        Velocity v;
        // Decide and set the ball speed and angle
        if (sizes[i] > 50) {
             v = Velocity.fromAngleAndSpeed(random.nextInt(360), defSpeed);
        } else {
             v = Velocity.fromAngleAndSpeed(random.nextInt(360), (81 - balls[i].getSize()) / 10);
        }
        balls[i].setVelocity(v);
    }

    // Animation loop
    while (true) {
        d = gui.getDrawSurface();
        for (int i = 0; i < balls.length; i++) {
            balls[i].moveOneStep();  // Move each ball
            balls[i].drawOn(d);  // Draw each ball on the surface
        }
        gui.show(d);  // Update the GUI
        sleeper.sleepFor(50);  // Wait for 50 milliseconds.
    }
}

    /**
     * Generates a random color.
     *
     * @return a randomly generated Color.
     */
    public static Color generateRandomColor() {
        Random rand = new Random();
        int red = rand.nextInt(256); // Random value between 0 and 255 for red
        int green = rand.nextInt(256); // Random value between 0 and 255 for green
        int blue = rand.nextInt(256); // Random value between 0 and 255 for blue

        return new Color(red, green, blue);
    }
}
