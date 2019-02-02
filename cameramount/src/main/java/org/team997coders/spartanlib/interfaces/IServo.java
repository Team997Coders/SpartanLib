package org.team997coders.spartanlib.interfaces;

/**
 * The IServo interface for interacting with any servo. Based
 * on the Arduino library, but perhaps this is generic enough to be
 * adapted to other Servo classes? We will see...
 */
public interface IServo {
  /**
   * Attach the given pin to the next free channel, sets pinMode, returns channel number or 0 if failure
   * @param pinArg  The pin to which the servo is attached
   * @return        The PWM channel attached to or zero if failure
   */
  byte attach(int pinArg);
  /**
   * Returns true if there is a servo attached. 
   * @return  True if attached.
   */
  byte attached();
  /**
   * Stops an attached servos from pulsing its i/o pin.
   */
  void detach();
  /**
   * Gets the last written servo pulse width as an angle.
   * @return   Returns an angle between 0 and 180.
   */
  int read();
  /**
   * Sets the servo angle in degrees.
   * Some libraries, with an invalid angle that is valid as pulse in microseconds will be treated as microseconds.
   * If not valid, an implementaiton should throw an IllegalArgumentException exception.
   * @param angleInDegrees  Angle in degrees between 0 and 180.
   */
  void write(int angleInDegrees);
}
