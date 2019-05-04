package org.team997coders.spartanlib;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;

import org.junit.Test;
import org.team997coders.spartanlib.controller.TrapezoidalMotion;

public class TrapezoidalUnitTest {

  @Test
  public void BasicTest() {
    TrapezoidalMotion t = new TrapezoidalMotion(160, 40);
    t.initMotion(20);

    double[] a = new double[1400];
    double[] g = new double[1400];

    for (double i = 0; i < 14; i += 0.01) {
      a[(int) (i * 100)] = t.getVelocity(i);
      g[(int) (i * 100)] = t.getPosition(i);
    }

    try {
      File f = new File("/home/n30b4rt/Desktop/oi.txt");
      FileWriter wr = new FileWriter(f);

      //wr.write("time, pos, velo\n");

      for (double i = 0; i < 14; i += 0.01) {
        wr.write(i + ",");
        wr.write(g[(int) (i * 100)] + ",");
        wr.write(a[(int) (i * 100)] + "\n");
      }

      wr.flush();
      wr.close();
    } catch (Exception e) {
      e.printStackTrace();
      assertTrue("Error when logging data", false);
    }

    assertTrue("Test", true);
  }

}