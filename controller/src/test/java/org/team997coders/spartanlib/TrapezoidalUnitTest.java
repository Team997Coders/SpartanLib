package org.team997coders.spartanlib;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;

import org.junit.Test;
import org.team997coders.spartanlib.controller.TrapezoidalMotion;

public class TrapezoidalUnitTest {

  @Test
  public void BasicTest() {
    TrapezoidalMotion t = new TrapezoidalMotion(0.2, 2);
    t.initMotion(100);

    double[] a = new double[600];

    for (double i = 0; i < 60; i += 0.1) {
      a[(int) (i * 10)] = t.getVelocity(i);
    }

    try {
      File f = new File("/home/n30b4rt/Desktop/oi.txt");
      FileWriter wr = new FileWriter(f);

      for (double b : a) {
        wr.write("\n" + b);
      }

      wr.flush();
      wr.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    assertTrue("Test", true);
  }

}