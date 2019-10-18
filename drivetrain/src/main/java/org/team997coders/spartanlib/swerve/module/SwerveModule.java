package org.team997coders.spartanlib.swerve.module;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class SwerveModule<AziCont, Azi, Dri> extends Subsystem {

  public SwerveModule(int ID) { this.ID = ID; }

  protected AziCont azimuthController;
  protected Dri drive;
  protected Azi azimuth;

  public int ID;
  protected double targetAngle = 0, targetSpeed = 0;

  // public abstract void setTargetAngle(double angle);
  // public abstract void setTargetSpeed(double speed);
  protected abstract void setAzimuthSpeed(double speed);
  protected abstract void setDriveSpeed(double speed);
  protected abstract void invertDrive(boolean a);

  public abstract void update();
  public abstract void updateSmartDashboard();

  // public abstract double getTargetAngle();
  // public abstract double getTargetSpeed();
  // protected abstract double getAzimuthError();
  public abstract double getContributingSpeed(double direction);
  public abstract double getAngle();

  public double getAzimuthError() {
    double current = getAngle();
    double error = targetAngle - current;
    if (Math.abs(error) > 180) {
      int sign = (int) (error / Math.abs(error));
      error += 180 * -sign;
      return error;
    } else {
      return error;
    }
  }

  public void setTargetSpeed(double speed) {
    targetSpeed = speed;
  }

  public void setTargetAngle(double angle) {
    double p = limitRange(angle, 0, 360);
    double current = getAngle();

    double delta = current - p;

    if (delta > 180) {
      p += 360;
    } else if (delta < -180) {
      p -= 360;
    }

    delta = current - p;
    if (delta > 90 || delta < -90) {
      if (delta > 90)
        p += 180;
      else if (delta < -90)
        p -= 180;
      invertDrive(true); // drive.setInverted(true);
    } else {
      invertDrive(false); // drive.setInverted(false);
    }

    this.targetAngle = p;
  }

  public double limitRange(double a, double min, double max) {
    while (a < min)
      a += max;
    while (a > max)
      a -= max;
    return a;
  }

  public double getTargetAngle() {
    return targetAngle;
  }

  public double getTargetSpeed() {
    return targetSpeed;
  }

}