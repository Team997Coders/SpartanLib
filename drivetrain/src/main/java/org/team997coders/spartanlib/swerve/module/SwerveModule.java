package org.team997coders.spartanlib.swerve.module;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class SwerveModule<AziCont, Azi, Dri> extends Subsystem {

  public SwerveModule(int pID, int pEncoderID, double pEncoderZero) {
    this.mID = pID;
    this.mAzimuthEncoder = new AnalogInput(pEncoderID);
    this.mEncoderZero = pEncoderZero;
  }

  protected AziCont mAzimuthController;
  protected Dri mDrive;
  protected Azi mAzimuth;

  protected AnalogInput mAzimuthEncoder;
  protected final double ENCODER_MAX = 5;
  protected double mEncoderZero;

  public int mID;
  protected double mTargetAngle = 0, mTargetSpeed = 0;

  // public abstract void setTargetAngle(double angle);
  // public abstract void setTargetSpeed(double speed);
  protected abstract void setAzimuthSpeed(double pSpeed);
  protected abstract void setDriveSpeed(double pSpeed);
  public abstract void invertDrive(boolean pA);
  public abstract void invertAzimuth(boolean pA);

  public abstract void update();
  public abstract void updateSmartDashboard();
  public abstract void updateAzimuthPID(double pP, double pI, double pD);

  // public abstract double getTargetAngle();
  // public abstract double getTargetSpeed();
  // protected abstract double getAzimuthError();
  public abstract double getContributingSpeed(double pDirection);

  public double getAzimuthError() {
    double current = getAngle();
    double error = mTargetAngle - current;
    if (Math.abs(error) > 180) {
      int sign = (int) (error / Math.abs(error));
      error += 180 * -sign;
      return error;
    } else {
      return error;
    }
  }

  public void setTargetSpeed(double speed) {
    mTargetSpeed = speed;
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

    this.mTargetAngle = p;
  }

  public double getAngle() {
    return encoderToAngle(getEncoderParsed(), true);
  }

  public double getRawEncoder() {
    return mAzimuthEncoder.getVoltage();
  }

  public double getEncoderParsed() {
    double a = getRawEncoder() - mEncoderZero;
    return limitRange(a, 0, ENCODER_MAX);
  }

  public double encoderToAngle(double val, boolean isParsed) {
    if (!isParsed) {
      val = val - mEncoderZero;
      val = limitRange(val, 0, ENCODER_MAX);
    }

    double mod = val / ENCODER_MAX;
    return 360 * mod;
  }

  public double angleToEncoder(double val) {
    return (ENCODER_MAX * val) / 360;
  }

  public double limitRange(double a, double min, double max) {
    while (a < min)
      a += max;
    while (a > max)
      a -= max;
    return a;
  }

  public double getTargetAngle() {
    return mTargetAngle;
  }

  public double getTargetSpeed() {
    return mTargetSpeed;
  }

}