package org.team997coders.spartanlib.hardware.roborio;

import org.team997coders.spartanlib.interfaces.IServo;

public class Servo implements IServo {

  private final edu.wpi.first.wpilibj.Servo wpiServo;
  private boolean isAttached;

  /**
   * Create a new servo on pin 0.
   */
  public Servo() {
    this(0);
  }

  /**
   * Create a new servo. By default 2.4 ms is used as the maxPWM value By default 0.6 ms is used as the minPWM value.
   *  
   * @param pinArg  The PWM channel to which the servo is attached. 0-9 are on-board. 10-19 are on the MXP port.
   */
  public Servo(int pinArg) {
    this(new edu.wpi.first.wpilibj.Servo(pinArg));
  }

  /**
   * Create a new servo. Take in an already instantiated wpilibj Servo (for testing purposes).
   * 
   * @param wpiServo  A wpilibj Servo.
   */
  public Servo(edu.wpi.first.wpilibj.Servo wpiServo) {
    this.wpiServo = wpiServo;
    this.isAttached = true;
  }

  /**
   * Roborio servos are always "attached" to their timers. This method is only here
   * to meet interface implementation requirements. 
   * @param pinArg  The PWM channel to which the servo is attached. 0-9 are on-board. 10-19 are on the MXP port.
   * @return        1 unless you attempt to assign to another pin on the fly, then return 0;
   */
  public byte attach(int pinArg) {
    // There is no concept of holding references to wpilibj servos without being attached
    // to their associated timers, so do nothing here.
    // However, since it is possible to change the pin on the fly with this call, simply
    // return 0 here, indicating an error.
    if (pinArg != wpiServo.getChannel()) {
      return 0;
    } else {
      return 1;
    }
  }

  /**
   * Returns whether roborio is pulsing servo.
   * 
   * @return  1 if pulsing or 0 if not
   */
  public byte attached() {
    return isAttached ? (byte)1 : (byte)0;
  }

  /**
   * Stop pulsing servo. The next write will automatically
   * restart PWM.
   */
  public void detach() {
    isAttached = false;
    wpiServo.setDisabled();
  }

  public int read() {
    return (byte)Math.round(wpiServo.getAngle());
  }

  public void write(int angleInDegrees) {
    if (angleInDegrees < 0 || angleInDegrees > 180) {
      throw new IllegalArgumentException("Angle must be 0 to 180 degrees");
    }
    wpiServo.setAngle(angleInDegrees);
    isAttached = true;
  }
}