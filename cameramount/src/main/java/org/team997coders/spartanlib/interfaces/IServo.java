package org.team997coders.spartanlib.interfaces;

/**
 * The IServo interface for interacting with any servo. Based
 * on the Arduino library, but perhaps this is generic enough to be
 * adapted to other Servo classes? We will see...
 */
public interface IServo {
  byte attach(int pinArg);
  byte attached();
  void detach();
  byte read();
  void write(int angleInDegrees);
}
