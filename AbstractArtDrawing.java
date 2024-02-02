//207323411 Itamar Shapira
//207272220 Dolev Menajem

import biuoop.GUI;
import biuoop.DrawSurface;
import java.awt.Color;

/**
 * The AbstractArtDrawing class generates random lines on a GUI window
 * and highlights midpoints, intersections, and triangles.
 */
public class AbstractArtDrawing {
    /**
     * Draws 10 randomly generated lines on a GUI window
     * and highlights midpoints, intersections, and triangles.
     */
    public void drawRandomLines() {
      // Create a window with the title "Lines and Intersections"
      // which is 400 pixels wide and 300 pixels high.
      GUI gui = new GUI("Lines and Intersections", 400, 300);
      DrawSurface d = gui.getDrawSurface();
      // Create an array of lines to store generated lines
      Line[] drawnLines = new Line[10];

      // Generate and draw random lines
      for (int i = 0; i < 10; ++i) {
          Line line = Line.generateRandomLine(d.getWidth(), d.getHeight());
          drawnLines[i] = line;
          d.setColor(Color.BLACK);
          d.drawLine((int) line.start().getX(), (int) line.start().getY(),
                  (int) line.end().getX(), (int) line.end().getY());
      }

      // Draw blue dots for each line mid-point
      d.setColor(Color.BLUE);
      for (int i = 0; i < 10; i++) {
          Point midpoint = drawnLines[i].middle();
          d.fillCircle((int) midpoint.getX(), (int) midpoint.getY(), 3);
      }

      // Draw red dots for intersections between lines
      d.setColor(Color.RED);
      for (int i = 0; i < 9; i++) {
          Line currentLine = drawnLines[i];
          for (int j = i + 1; j < 10; j++) {
              // Check for intersection between 2 lines
              if (currentLine.isIntersecting(drawnLines[j])) {
                  Point intersection = currentLine.intersectionWith(drawnLines[j]);
                  d.fillCircle((int) intersection.getX(), (int) intersection.getY(), 3);
              }
          }
      }

      // Draw green lines where a triangle is formed due to lines intersecting
      d.setColor(Color.GREEN);
      for (int i = 0; i < 10; i++) {
          Line line1 = drawnLines[i];
          for (int j = i + 1; j < 10; j++) {
              Line line2 = drawnLines[j];
              for (int k = j + 1; k < 10; k++) {
                  Line line3 = drawnLines[k];
                  //Check for intersection between the three lines
                  if (line1.isIntersecting(line2, line3) && line2.isIntersecting(line3)) {
                     // Get intersection points of the three lines
                      Point p1 = line1.intersectionWith(line2);
                      Point p2 = line1.intersectionWith(line3);
                      Point p3 = line2.intersectionWith(line3);

                      // Draw the lines between the intersecton points
                      d.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
                      d.drawLine((int) p1.getX(), (int) p1.getY(), (int) p3.getX(), (int) p3.getY());
                      d.drawLine((int) p2.getX(), (int) p2.getY(), (int) p3.getX(), (int) p3.getY());
                  }
              }
          }
      }

      gui.show(d);
  }
    /**
     * Main method to execute the example.
     *
     * @param args command-line arguments (not used).
     */
  public static void main(String[] args) {
     AbstractArtDrawing example = new AbstractArtDrawing();
     example.drawRandomLines();
  }
}
