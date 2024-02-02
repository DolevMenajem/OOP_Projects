//207323411 Itamar Shapira
//207272220 Dolev Menajem

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * The BouncingBallAnimation class creates a bouncing ball animation on a GUI window.
 */
public class BouncingBallAnimation {
     /**
     * The main method of the BouncingBallAnimation program.
     *
     * @param args command-line arguments containing four integers
     *             specifying the initial position and velocity of the ball.
     */
    public static void main(String[] args) {

        // Parse each part to an integer
        int n1 = Integer.parseInt(args[0]);
        int n2 = Integer.parseInt(args[1]);
        int n3 = Integer.parseInt(args[2]);
        int n4 = Integer.parseInt(args[3]);
        drawAnimation(new Point(n1, n2), n3, n4);
    }

    static private void drawAnimation(Point start, double dx, double dy) {
      GUI gui = new GUI("title", 600, 600);
      DrawSurface d = gui.getDrawSurface();
      Sleeper sleeper = new Sleeper();

      Ball ball = new Ball(start.getX(), start.getY(), 30, java.awt.Color.BLACK);
      Velocity vel = Velocity.fromAngleAndSpeed(dx, dy);
      ball.setVelocity(vel);
      ball.setFrame(0, 0, d.getWidth(), d.getHeight());
      ball.setCenter(ball.getPointInFrame());

      while (true) {
         ball.moveOneStep();
         d = gui.getDrawSurface();
         ball.drawOn(d);
         gui.show(d);
         sleeper.sleepFor(50);  // wait for 50 milliseconds.
      }
   }
}
