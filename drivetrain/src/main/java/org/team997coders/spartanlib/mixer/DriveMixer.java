package org.team997coders.spartanlib.mixer;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.team997coders.spartanlib.data.speedsets.RampedSpeedSet;
import org.team997coders.spartanlib.data.speedsets.SpeedSet;
import org.team997coders.spartanlib.helpers.RoboMisc;

public class DriveMixer {

  public static SpeedSet kiwiMixer(double forward, double strafe, double spin, double angle, boolean fieldOrientated) {
    if (fieldOrientated) {
      //cos angle * forward
      //sin angle * strafe
      forward = Math.cos(RoboMisc.angle2Rad(angle)) * forward;
      strafe = Math.sin(RoboMisc.angle2Rad(angle)) * strafe;
    }

    
  }

  public static RampedSpeedSet arcadeRampedMixer(double speed, double turn, RampedSpeedSet dat, double deltaTime) {
    double newY = speed;

    double maxIncrement = deltaTime * dat.accelRate;

    if (Math.abs(speed - dat.prevA) > maxIncrement) {
      double sign = (speed - dat.prevA) / Math.abs(speed - dat.prevA);
      newY = (maxIncrement * sign) + dat.prevA;
    }

    if (Math.abs(newY) > 0.6) {
      newY = (Math.abs(newY) / newY) * 0.6;
    }

    RampedSpeedSet g = new RampedSpeedSet(newY + turn, newY - turn, dat.accelRate);

    //leftTalon.set(ControlMode.PercentOutput, newY + turn);
    //rightTalon.set(ControlMode.PercentOutput, newY - turn);

    g.prevA = newY;

    return g;
  }

}