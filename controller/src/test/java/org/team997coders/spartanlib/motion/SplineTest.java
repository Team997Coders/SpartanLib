package org.team997coders.spartanlib.motion;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.team997coders.spartanlib.motion.path.MotionSpline;
import org.team997coders.spartanlib.motion.path.Point;
import org.team997coders.spartanlib.motion.path.Waypoint;

public class SplineTest {

  //@Test
  public void WriteOutput() {

    Waypoint a = new Waypoint(new Point(0, 0), new Point(500, 60));
    Waypoint b = new Waypoint(new Point(30, 0), new Point(0, 0));

    MotionSpline spline = new MotionSpline(a, b);
    spline.calulate(1000);
    Point[] p = spline.getPoints();

    //WriteShitOut(p);
  }

  public void WriteShitOut(Point[] p) {
    char[][] output = new char[70][];
    for (int i = 0; i < output.length; i++) {
      output[i] = new char[70];
    }

    for (int x = 0; x < output.length; x++) {
      for (int y = 0; y < output[0].length; y++) {
        output[x][y] = ' ';
      }
    }

    for (int i = 0; i < p.length; i++) {
      int x = (int)p[i].x;
      int y = (output[0].length) - (int)p[i].y;
      if (x > output.length - 1 || x < 0) continue;
      if (y > output[0].length - 1 || y < 0) continue;
      output[x][y] = (char)(219);
    }

    List<String> lines = new ArrayList<String>();
    
    for (int y = 0; y < output[0].length; y++) {
      String s = "";
      for (int x = 0; x < output.length; x++) {
        s += output[x][y];
      }
      lines.add(s + "\n");
    }

    File f = new File(System.getProperty("user.home") + "\\Desktop\\Spline.txt");
    try {
      f.createNewFile();
      FileWriter fw = new FileWriter(f);
      for (int i = 0; i < lines.size(); i++)  {
        fw.write(lines.get(i));
        fw.flush();
      }
      fw.close();
    } catch (Exception e) {
      assertTrue(false);
    }
    assertTrue(true);
  }

}