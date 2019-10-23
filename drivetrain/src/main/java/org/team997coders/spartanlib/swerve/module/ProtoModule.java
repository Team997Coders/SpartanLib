package org.team997coders.spartanlib.swerve.module;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import org.team997coders.spartanlib.helpers.MiniPID;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ProtoModule extends SwerveModule<MiniPID, VictorSPX, VictorSPX> {

  private final int ALIGNMENT_TIMEOUT = 1250; // Milliseconds until I start complaining
  private final double ALIGNMENT_TOLERANCE = 2.5; // Tolerance in degrees
  private double lastGoodAlignment;

  public ProtoModule(int pID, int pAzimuthID, int pDriveID, int pEncoderID, double pEncoderZero,
      double pP, double pI, double pD) {

    super(pID, pEncoderID, pEncoderZero);

    mAzimuth = new VictorSPX(pAzimuthID);
    mAzimuth.setInverted(true);
    //azimuth.setNeutralMode(NeutralMode.Brake);
    mDrive = new VictorSPX(pDriveID);
    mAzimuthEncoder = new AnalogInput(pEncoderID);
    this.mEncoderZero = pEncoderZero;

    mAzimuthController = new MiniPID(pP, pI, pD);
    mAzimuthController.setOutputLimits(-1, 1);

    // setDefaultCommand(defaultCommand); // Not entirely sure if this would work but hek try it
  }

  @Override
  public void setAzimuthSpeed(double speed) {
    mAzimuth.set(ControlMode.PercentOutput, speed);
  }

  @Override
  public void setDriveSpeed(double speed) {
    mDrive.set(ControlMode.PercentOutput, speed);
  }

  @Override
  public double getContributingSpeed(double direction) {
    return 0;
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
      lastGoodAlignment = System.currentTimeMillis();
      SmartDashboard.putBoolean("[" + mID + "] Module Alignment Warning", true);
    } else {
      if (lastGoodAlignment + ALIGNMENT_TIMEOUT < System.currentTimeMillis()) {
        SmartDashboard.putBoolean("[" + mID + "] Module Alignment Warning", false);
      }
    }
  }

  // Leaving this to be changed just because I want the option for change in the update functions for testing
  @Override
  public void update() {
    double error = getAzimuthError();
    double output = mAzimuthController.getOutput(0, error);
    setAzimuthSpeed(output);
    setDriveSpeed(getTargetSpeed());
  }

  @Override
  protected void invertDrive(boolean pA) {
    mDrive.setInverted(pA);
  }

  @Override
  protected void invertAzimuth(boolean pA) {
    mAzimuth.setInverted(pA);  
  }

  @Override
  public void updateAzimuthPID(double p, double i, double d) {
    mAzimuthController.setP(p);
    mAzimuthController.setI(i);
    mAzimuthController.setD(d);
  }

  public void resetAzimuthController() {
    mAzimuthController.reset();
  }

  @Override
  protected void initDefaultCommand() {
    //setDefaultCommand(new UpdateModule(0, this));
    //Robot.moduleRunner.AddAction(new UpdateModule(ID, this));

  }

}
