package org.team997coders.spartanlib.swerve;

import org.team997coders.spartanlib.helpers.SwerveMixerData;
import org.team997coders.spartanlib.swerve.module.SwerveModule;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class SwerveDrive extends Subsystem {

  protected double wheelBase, trackWidth;

  protected SwerveModule[] modules;

  public SwerveDrive(double pWheelBase, double pTrackWidth) {
    wheelBase = pWheelBase;
    trackWidth = pTrackWidth;

    // setDefaultCommand(defaultCommand); // Again, have no idea if this will work the same, test plz
  }

  public SwerveMixerData SwerveMixer(double forward, double strafe, double rotation, double angle) {
    return SwerveMixer(forward, strafe, rotation, true, angle);
  }

  public SwerveMixerData SwerveMixer(double forward, double strafe, double rotation) {
    return SwerveMixer(forward, strafe, rotation, false, 0.0);
  }

  /**
   * Basically 95% leveraged from Jack In The Bot
   */
  private SwerveMixerData SwerveMixer(double forward, double strafe, double rotation, boolean isFieldOriented, double angle) {

    SwerveMixerData smd = new SwerveMixerData();
    smd.setForward(forward);
    smd.setStrafe(strafe);
    smd.setRotate(rotation);

    if (isFieldOriented) {
      double angleRad = Math.toRadians(angle);
      double temp = forward * Math.cos(angleRad) + strafe * Math.sin(angleRad);
      strafe = -forward * Math.sin(angleRad) + strafe * Math.cos(angleRad);
      forward = temp;
    }

    double a = strafe - rotation * (wheelBase / trackWidth);
    double b = strafe + rotation * (wheelBase / trackWidth);
    double c = forward - rotation * (trackWidth / wheelBase);
    double d = forward + rotation * (trackWidth / wheelBase);

    double[] angles = new double[] { Math.atan2(b, c) * 180 / Math.PI, Math.atan2(b, d) * 180 / Math.PI,
        Math.atan2(a, d) * 180 / Math.PI, Math.atan2(a, c) * 180 / Math.PI };

    double[] speeds = new double[] { Math.sqrt(b * b + c * c), Math.sqrt(b * b + d * d), Math.sqrt(a * a + d * d),
        Math.sqrt(a * a + c * c) };

    double max = speeds[0];

    for (double speed : speeds) {
      if (speed > max) {
        max = speed;
      }
    }

    double mod = 1;
    if (max > 1) {
      mod = 1 / max;

      for (int i = 0; i < 4; i++) {
        speeds[i] *= mod;

        angles[i] %= 360;
        if (angles[i] < 0)
          angles[i] += 360;
      }
    }

    smd.setAngles(angles);
    smd.setSpeeds(speeds);
    return smd;
  }

  public void setSwerveInput(SwerveMixerData smd) {
    for (int i = 0; i < modules.length; i++) {
      if (Math.abs(smd.getForward()) > 0.05 || Math.abs(smd.getStrafe()) > 0.05 || Math.abs(smd.getRotate()) > 0.05) {
        modules[i].setTargetAngle(smd.getAngles()[i]);
      } else {
        modules[i].setTargetAngle(modules[i].getTargetAngle());
      }
      modules[i].setTargetSpeed(smd.getSpeeds()[i]);
    }
  }

  public SwerveModule getModule(int index) {
    return modules[index];
  }

  public SwerveModule[] getModules() { return modules; }

}