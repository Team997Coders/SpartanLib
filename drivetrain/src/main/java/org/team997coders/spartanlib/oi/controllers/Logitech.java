package org.team997coders.spartanlib.oi.controllers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * Basic command-based wrapper class for Logitech controllers
 */
public class Logitech implements Gamepad {

  public Joystick mGamepad;

  public JoystickButton
    mA, mB, mX, mY,
    mLeftBumper, mRightBumper,
    mStart, mBack, mLeftStick, mRightStick;

  public Logitech(int port) {

    mGamepad = new Joystick(port);

    mA = new JoystickButton(mGamepad, Buttons.A);
    mB = new JoystickButton(mGamepad, Buttons.B);
    mX = new JoystickButton(mGamepad, Buttons.X);
    mY = new JoystickButton(mGamepad, Buttons.Y);
    mLeftBumper = new JoystickButton(mGamepad, Buttons.LEFT_BUMPER);
    mRightBumper = new JoystickButton(mGamepad, Buttons.RIGHT_BUMPER);
    mBack = new JoystickButton(mGamepad, Buttons.BACK);
    mStart = new JoystickButton(mGamepad, Buttons.START);
    mLeftStick = new JoystickButton(mGamepad, Buttons.LEFT_STICK);
    mRightStick = new JoystickButton(mGamepad, Buttons.RIGHT_STICK);
  }

  public double getAxis(int port) {
    return mGamepad.getRawAxis(port);
  }

  public static class Buttons {

    public static final int

      A = 1, B = 2, X = 3, Y = 4,
      LEFT_BUMPER = 5, RIGHT_BUMPER = 6,
      BACK = 7, START = 8,
      LEFT_STICK = 9, RIGHT_STICK = 10;

  }

  public static class Axis {

    public static final int

      LEFT_X = 0,
      LEFT_Y = 1,
      RIGHT_X = 4,
      RIGHT_Y = 5,

      LEFT_TRIGGER = 2,
      RIGHT_TRIGGER = 3;

  }

}