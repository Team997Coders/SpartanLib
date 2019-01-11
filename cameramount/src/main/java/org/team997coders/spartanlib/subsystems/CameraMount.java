package org.team997coders.spartanlib.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.team997coders.spartanlib.interfaces.IServo;

/**
 * A subsystem to define an automated camera mount
 * that uses servos for panning and tilting.
 */
public class CameraMount extends Subsystem {
  private final IServo panServo;
  private final IServo tiltServo;
  private final int tiltLowerLimitInDegrees;
  private final int tiltUpperLimitInDegrees; 
  private final int panLowerLimitInDegrees;
  private final int panUpperLimitInDegrees;
  private int panAngleInDegrees;
  private int tiltAngleInDegrees;

  /**
   * Construct a camera mount with angle limits set to 0..180 degrees.
   * 
   * @param panServo    An interface to a Servo for panning
   * @param tiltServo   An interface to a Servo for tilting
   */
  public CameraMount(IServo panServo, IServo tiltServo) {
    this(panServo, tiltServo, 0, 180, 0, 180);
  }

  /**
   * Construct a camera mount specifing limits of travel in degrees.
   * 
   * @param panServo                  An interface to a Servo for panning
   * @param tiltServo                 An interface to a Servo for tilting
   * @param tiltLowerLimitInDegrees   The lower tilt limit angle in degrees
   * @param tiltUpperLimitInDegrees   The upper tilt limit angle in degrees
   * @param panLowerLimitInDegrees    The lower pan limit angle in degrees
   * @param panUpperLimitInDegrees    The upper pan limit angle in degrees
   */
  public CameraMount(IServo panServo, 
      IServo tiltServo, 
      int tiltLowerLimitInDegrees, 
      int tiltUpperLimitInDegrees, 
      int panLowerLimitInDegrees,
      int panUpperLimitInDegrees) {
    super("CameraMount");
    this.panServo = panServo;
    this.tiltServo = tiltServo;
    this.tiltLowerLimitInDegrees = tiltLowerLimitInDegrees;
    this.tiltUpperLimitInDegrees = tiltUpperLimitInDegrees;
    this.panLowerLimitInDegrees = panLowerLimitInDegrees;
    this.panUpperLimitInDegrees = panUpperLimitInDegrees;
  }

  protected void initDefaultCommand() {}

  /**
   * Pan to an angle within limits set at construction time.
   * 
   * @param angleInDegrees
   */
  public void panToAngle (int angleInDegrees) {
    if (angleInDegrees >= panUpperLimitInDegrees) {
      panServo.write(panUpperLimitInDegrees);
      panAngleInDegrees = panUpperLimitInDegrees;
    } else if (angleInDegrees <= panLowerLimitInDegrees) {
      panServo.write(panLowerLimitInDegrees);
      panAngleInDegrees = panLowerLimitInDegrees;
    } else {
      panServo.write(angleInDegrees);
      panAngleInDegrees = angleInDegrees;
    }
  }

  /**
   * Tilt to an angle within limits set at construction time.
   * 
   * @param angleInDegrees
   */
  public void tiltToAngle (int angleInDegrees) {
    if (angleInDegrees >= tiltUpperLimitInDegrees) {
      tiltServo.write(tiltUpperLimitInDegrees);
      tiltAngleInDegrees = tiltUpperLimitInDegrees;
    } else if (angleInDegrees <= tiltLowerLimitInDegrees) {
      tiltServo.write(tiltLowerLimitInDegrees);
      tiltAngleInDegrees = tiltLowerLimitInDegrees;
    } else {
      tiltServo.write(angleInDegrees);
      tiltAngleInDegrees = angleInDegrees;
    }
  }

  /**
   * Get the last set tilt angle
   * 
   * @return  Tilt angle in degrees
   */
  public int getTiltAngleInDegrees() {
    return tiltAngleInDegrees;
  }

  /**
   * Get the last set pan angle
   * 
   * @return  Pan angle in degrees
   */
  public int getPanAngleInDegrees() {
    return panAngleInDegrees;
  }

  /**
   * Determines if panning is at a limit.
   * 
   * @return  True if at a limit
   */
  public boolean atPanLimit() {
    return getPanAngleInDegrees() == panLowerLimitInDegrees || getPanAngleInDegrees() == panUpperLimitInDegrees;
  }

  /**
   * Determines if tilting is at a limit.
   * 
   * @return  True if at a limit
   */
  public boolean atTiltLimit() {
    return getTiltAngleInDegrees() == tiltLowerLimitInDegrees || getTiltAngleInDegrees() == tiltUpperLimitInDegrees;
  }
}