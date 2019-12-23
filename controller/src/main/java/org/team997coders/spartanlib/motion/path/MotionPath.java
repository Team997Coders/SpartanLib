package org.team997coders.spartanlib.motion.path;

import java.util.ArrayList;
import java.util.List;

public class MotionPath {
  
  private MotionSpline[] mSplines;
  private Point[] mPoints;

  public MotionPath(Waypoint... waypoints) {
    mSplines = new MotionSpline[waypoints.length - 1];
    for (int i = 0; i < waypoints.length - 1; i++) {
      mSplines[i] = new MotionSpline(waypoints[i], waypoints[i + 1]);
    }
  }

  public void calculate(int steps) {
    List<Point> points = new ArrayList<Point>();
    for (int i = 0; i < mSplines.length; i++) {
      mSplines[i].calulate(steps);
    }
    for (int i = 0; i < mSplines.length; i++) {
      Point[] p = mSplines[i].getPoints();
      int offset = -1;
      if (i == mSplines.length - 1) offset = 0;
      for (int f = 0; f < p.length - offset; f++) {
        points.add(p[f]);
      }
    }
    mPoints = new Point[points.size()];
    for (int i = 0; i < mPoints.length; i++) {
      mPoints[i] = points.get(i);
    }
  }

}