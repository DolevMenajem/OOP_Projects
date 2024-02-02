//207323411 Itamar Shapira
//207272220 Dolev Menajem

import java.util.Random;
import java.awt.Color;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * The MultipleFramesBouncingBallsAnimation class creates an animation
 * with bouncing balls inside and outside frames
 * on a GUI window.
 */
public class MultipleFramesBouncingBallsAnimation {
    /**
     * The main method of the MultipleFramesBouncingBallsAnimation program.
     *
     * @param args an array of integers representing the sizes of the balls.
     */
    public static void main(String[] args) {
        int[] sizes = new int[args.length];
        // Parse size of balls
        for (int i = 0; i < args.length; i++) {
            sizes[i] = Integer.parseInt(args[i]);
        }
        bouncingBallsWithFrames(sizes);
    }

    /**
     * Creates an animation with bouncing balls inside and outside frames on a GUI window.
     *
     * @param sizes an array of integers representing the sizes of the balls.
     */
    public static void bouncingBallsWithFrames(int[] sizes) {
        // Create a GUI window titled "title" with dimensions 800x600 pixels.
        GUI gui = new GUI("Bouncing Balls With Frames", 800, 600);
        DrawSurface d = gui.getDrawSurface();
        Sleeper sleeper = new Sleeper();
        // Default speed
        int defSpeed = 3;

        // Create frame of the yellow inner frame
        Line yellowBorders = new Line(450, 450, 600, 600);

        Random random = new Random();
        Ball[] balls = new Ball[sizes.length];

        int half = (balls.length % 2 == 0) ? (balls.length / 2) : (balls.length / 2) + 1;

        // Create balls inside the grey frame with random colors, sizes, and velocities
        for (int i = 0; i < half; i++) {
            Color randomColor = generateRandomColor();
            // Initialize a ball
            balls[i] = new Ball(new Point(0, 0), sizes[i], randomColor);
            // Set frame for the ball using bounds of the grey square
            balls[i].setFrame(50, 50, 450, 450);
            double frameLength = balls[i].getFrame().getEnd().getX()
                                 - balls[i].getFrame().getStart().getX();
            // Normalize size of balls in relation to a specified frame length
            balls[i].setSize(Ball.normalizeSize(sizes[i], (int) frameLength));
            balls[i].setCenter(balls[i].getPointInFrame());

            Velocity v;
            // Set velocity of the ball in relation to its size
            if (sizes[i] > 50) {
                v = Velocity.fromAngleAndSpeed(random.nextInt(360), defSpeed);
            } else {
                v = Velocity.fromAngleAndSpeed(random.nextInt(360), (81 - balls[i].getSize()) / 10);
            }
            balls[i].setVelocity(v);
        }

        // Create an array of the 2 inner frames
        Line[] frames = new Line[2];
        frames[0] = balls[0].getFrame();
        frames[1] = yellowBorders;

        // Create balls outside the inner frames with random colors, sizes, and velocities
        for (int i = half; i < balls.length; i++) {
            Color randomColor = generateRandomColor();
            // Initialize a ball
            balls[i] = new Ball(new Point(0, 0), sizes[i], randomColor);
            // Set frame for the ball using bounds of the GUI window
            balls[i].setFrame(0, 0, d.getWidth(), d.getHeight());
            double frameLength = d.getWidth() - balls[0].getFrame().getEnd().getX();
            // Normalize size of balls in relation to a specified frame length
            balls[i].setSize(Ball.normalizeSize(sizes[i], (int) frameLength));
            balls[i].setCenter(balls[i].getPointInOuterFrame(frames));

            // Set velocity of the ball in relation to its size
            Velocity v;
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
            d.setColor(java.awt.Color.GRAY);
            d.fillRectangle(50, 50, 450, 450);

            // Move and draw balls inside the grey frame
            for (int i = 0; i < half; i++) {
                balls[i].moveOneStep();
                balls[i].drawOn(d);
            }

            // Validate, move, and draw balls outside the inner frames
            for (int i = half; i < balls.length; i++) {
                balls[i].validateInnerFrame(frames[0]);
                balls[i].validateInnerFrame(frames[1]);
                balls[i].moveOneStep();
                balls[i].drawOn(d);
            }

            // Draw the yellow inner frame
            d.setColor(java.awt.Color.YELLOW);
            d.fillRectangle(450, 450, 150, 150);

            gui.show(d);
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
