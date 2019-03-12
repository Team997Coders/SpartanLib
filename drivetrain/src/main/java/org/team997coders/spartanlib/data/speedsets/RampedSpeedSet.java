package org.team997coders.spartanlib.data.speedsets;

public class RampedSpeedSet extends SpeedSet {

  public double accelRate, prevA, prevB;

  public RampedSpeedSet(double left, double right, double accelRate) {
    super(left, right);
  }

}