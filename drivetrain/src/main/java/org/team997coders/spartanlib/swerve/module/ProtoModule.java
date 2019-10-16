package org.team997coders.spartanlib.swerve.module;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import org.team997coders.spartanlib.helpers.MiniPID;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ProtoModule extends SwerveModule<MiniPID, VictorSPX, VictorSPX> {

  private final int ALIGNMENT_TIMEOUT = 1250; // Milliseconds until I start complaining
  private final double ALIGNMENT_TOLERANCE = 2.5; // Tolerance in degrees
  private double lastGoodAlignment;

  // private VictorSPX azimuth, drive;
  private AnalogInput azimuthEncoder;

  private final double ENCODER_MAX = 5;
  private double encoderZero;

  public ProtoModule(int ID, int azimuthID, int driveID, int encoderID, double encoderZero,
      double p, double i, double d) {

    super(ID);

    azimuth = new VictorSPX(azimuthID);
    azimuth.setInverted(true);
    //azimuth.setNeutralMode(NeutralMode.Brake);
    drive = new VictorSPX(driveID);
    azimuthEncoder = new AnalogInput(encoderID);
    this.encoderZero = encoderZero;

    azimuthController = new MiniPID(p, i, d);
    azimuthController.setOutputLimits(-1, 1);

    // setDefaultCommand(defaultCommand); // Not entirely sure if this would work but hek try it
  }

  @Override
  public void setAzimuthSpeed(double speed) {
    azimuth.set(ControlMode.PercentOutput, speed);
  }

  @Override
  public void setDriveSpeed(double speed) {
    drive.set(ControlMode.PercentOutput, speed);
  }

  public double getRawEncoder() {
    return azimuthEncoder.getVoltage();
  }

  public double getEncoderParsed() {
    double a = getRawEncoder() - encoderZero;
    return limitRange(a, 0, ENCODER_MAX);
  }

  @Override
  public double getContributingSpeed(double direction) {
    return 0;
  }

  @Override
  public double getAngle() {
    return encoderToAngle(getEncoderParsed(), true);
  }

  public double limitRange(double a, double min, double max) {
    while (a < min)
      a += max;
    while (a > max)
      a -= max;
    return a;
  }

  public double encoderToAngle(double val, boolean isParsed) {
    if (!isParsed) {
      val = val - encoderZero;
      val = limitRange(val, 0, ENCODER_MAX);
    }

    double mod = val / ENCODER_MAX;
    return 360 * mod;
  }

  public double angleToEncoder(double val) {
    return (ENCODER_MAX * val) / 360;
  }

  @Override
  public void updateSmartDashboard() {
    SmartDashboard.putNumber("[" + ID + "] Module Encoder", getRawEncoder());
    SmartDashboard.putNumber("[" + ID + "] Module Angle", getAngle());
    SmartDashboard.putNumber("[" + ID + "] Module Target Angle", getTargetAngle());
    SmartDashboard.putNumber("[" + ID + "] Module Target Speed", getTargetSpeed());
  }

  // Leaving this to be changed just because I want the option for change in the update functions for testing
  @Override
  public void update() {
    double target = getTargetAngle();
    double actual = getAngle();
    if (Math.abs(target - actual) <= ALIGNMENT_TOLERANCE) {
      lastGoodAlignment = System.currentTimeMillis();
      SmartDashboard.putBoolean("[" + ID + "] Module Alignment Warning", true);
    } else {
      if (lastGoodAlignment + ALIGNMENT_TIMEOUT < System.currentTimeMillis()) {
        SmartDashboard.putBoolean("[" + ID + "] Module Alignment Warning", false);
      }
    }

    double error = getAzimuthError();
    double output = azimuthController.getOutput(0, error);
    setAzimuthSpeed(output);
    setDriveSpeed(getTargetSpeed());
  }

  @Override
  protected void invertDrive(boolean a) {
    drive.setInverted(a);
  }

  @Override
  protected void initDefaultCommand() {
    //setDefaultCommand(new UpdateModule(0, this));
    //Robot.moduleRunner.AddAction(new UpdateModule(ID, this));

  }

}
