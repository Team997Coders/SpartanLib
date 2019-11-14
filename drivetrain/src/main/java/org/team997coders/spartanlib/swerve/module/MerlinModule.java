package org.team997coders.spartanlib.swerve.module;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import org.team997coders.spartanlib.controllers.SpartanPID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MerlinModule extends SwerveModule<SpartanPID, TalonSRX, VictorSPX> {

  private final int ALIGNMENT_TIMEOUT = 1250; // Milliseconds until I start complaining
  private final double ALIGNMENT_TOLERANCE = 2.5; // Tolerance in degrees

  private double mLastUpdate = Double.NaN;
  private double mLastGoodAlignment;

  public MerlinModule(int pID, int pAzimuthID, int pDriveID, int pEncoderID, double pEncoderZero, double pP, double pI,
      double pD) {

    super(pID, pEncoderID, pEncoderZero);

    mLastUpdate = System.currentTimeMillis();

    mAzimuth = new WPI_TalonSRX(pAzimuthID);
    invertAzimuth(true);
    mDrive = new WPI_VictorSPX(pDriveID);

    mAzimuthController = new SpartanPID(pP, pI, pD);
    mAzimuthController.setMinOutput(-1);
    mAzimuthController.setMaxOutput(1);
  }

  @Override
  protected void setAzimuthSpeed(double pSpeed) {
    mAzimuth.set(ControlMode.PercentOutput, pSpeed);
  }

  @Override
  public void setTargetAngle(double angle) {
    super.setTargetAngle(angle);
    mAzimuthController.reset();
    mAzimuthController.setSetpoint(mTargetAngle);
  }

  @Override
  protected void setDriveSpeed(double pSpeed) {
    mDrive.set(ControlMode.PercentOutput, pSpeed);
  }

  @Override
  public void invertDrive(boolean pA) {
    mDrive.setInverted(pA);
  }

  @Override
  public void invertDrive(boolean pA, boolean internal) {
    mDrive.setInverted(pA);
    driveDir = !pA;
  }

  @Override
  public void invertAzimuth(boolean pA) {
    mAzimuth.setInverted(pA);
  }

  @Override
  public void update() {
    mAzimuthController.setSetpoint(0.0);

    double deltaT = 0.0;
    double now = System.currentTimeMillis();
    if (Double.isFinite(mLastUpdate)) deltaT = (now - mLastUpdate) * 1000;
    mLastUpdate = now;

    double adjustedTheta = getAngle();
    while (adjustedTheta < mTargetAngle - 180) adjustedTheta += 360;
    while (adjustedTheta >= mTargetAngle + 180) adjustedTheta -= 360;

    double error = mTargetAngle - adjustedTheta;
    SmartDashboard.putNumber("[" + mID + "] Module Error", error);
    
    double output = mAzimuthController.WhatShouldIDo(adjustedTheta, deltaT);
    SmartDashboard.putNumber("[" + mID + "] Module Spin Speed", output);
    setAzimuthSpeed(output);
    setDriveSpeed(getTargetSpeed() * 1);
  }

  @Override
  public void updateSmartDashboard() {
    SmartDashboard.putNumber("[" + mID + "] Module Encoder", getRawEncoder());
    SmartDashboard.putNumber("[" + mID + "] Module Angle", getAngle());
    SmartDashboard.putNumber("[" + mID + "] Module Target Angle", getTargetAngle());
    SmartDashboard.putNumber("[" + mID + "] Module Target Speed", getTargetSpeed());

    double target = getTargetAngle();
    double actual = getAngle();
    if (Math.abs(target - actual) <= ALIGNMENT_TOLERANCE) {
      mLastGoodAlignment = System.currentTimeMillis();
      SmartDashboard.putBoolean("[" + mID + "] Module Alignment Warning", true);
    } else {
      if (mLastGoodAlignment + ALIGNMENT_TIMEOUT < System.currentTimeMillis()) {
        SmartDashboard.putBoolean("[" + mID + "] Module Alignment Warning", false);
      }
    }
  }

  @Override
  public void updateAzimuthPID(double pP, double pI, double pD) {
    mAzimuthController.setP(pP);
    mAzimuthController.setI(pI);
    mAzimuthController.setD(pD);
    System.out.println(pD);
  }

  @Override
  public double getContributingSpeed(double pDirection) { return 0; }

  @Override
  protected void initDefaultCommand() { }

}