package org.team997coders.spartanlib.swerve.module;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import org.team997coders.spartanlib.helpers.MiniPID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HybridModule extends SwerveModule<MiniPID, WPI_TalonSRX, WPI_VictorSPX> {

  private final int ALIGNMENT_TIMEOUT = 1250; // Milliseconds until I start complaining
  private final double ALIGNMENT_TOLERANCE = 2.5; // Tolerance in degrees
  private double mLastGoodAlignment;

  public HybridModule(int pID, int pAzimuthID, int pDriveID, int pEncoderID, double pEncoderZero, double pP, double pI,
      double pD) {

    super(pID, pEncoderID, pEncoderZero);

    mAzimuth = new WPI_TalonSRX(pAzimuthID);
    invertAzimuth(true);
    mDrive = new WPI_VictorSPX(pDriveID);

    mAzimuthController = new MiniPID(pP, pI, pD);
    mAzimuthController.setOutputLimits(-1, 1);
  }

  @Override
  protected void setAzimuthSpeed(double pSpeed) {
    mAzimuth.set(ControlMode.PercentOutput, pSpeed);
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
  public void invertAzimuth(boolean pA) {
    mAzimuth.setInverted(pA);
  }

  @Override
  public void update() {
    double error = getAzimuthError();
    SmartDashboard.putNumber("[" + mID + "] Module Error", error);
    double output = mAzimuthController.getOutput(0, error);
    SmartDashboard.putNumber("[" + mID + "] Module Spin Speed", output);
    setAzimuthSpeed(output);
    setDriveSpeed(getTargetSpeed() * 0.25);
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
  }

  @Override
  public double getContributingSpeed(double pDirection) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  protected void initDefaultCommand() {
    // TODO Auto-generated method stub

  }

}