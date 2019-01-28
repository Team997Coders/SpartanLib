package org.team997coders.spartanlib.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team997coders.spartanlib.interfaces.IJoystickValueProvider;
import org.team997coders.spartanlib.subsystems.CameraMount;

/**
 * This command will slew pan and tilt servos on the CameraMount
 * subsystem based on a rate factor (between -1 and 1 inclusive)
 * as a percentage of maximum.
 */
public class SlewCamera extends Command {
  private final CameraMount cameraMount;
  private final IJoystickValueProvider panRateProvider;
  private final IJoystickValueProvider tiltRateProvider;
  private final double slewRate180DegreesInSec;
  private final double heartbeatRateInMs;
  private final double maxDegreesPerHeartbeat;

  /**
   * Constructor requiring a CameraMount to act upon and rate providers
   * to control rate of slew. Time to slew 180 degrees is defaulted to
   * 0.45 seconds and the default heartbeat is 20 ms.
   * 
   * @param cameraMount       A CameraMount subsystem.
   * @param panRateProvider   A value provider for panning.
   * @param tiltRateProvider  A value provider fot tilting.
   */
  public SlewCamera(CameraMount cameraMount, 
      IJoystickValueProvider panRateProvider, 
      IJoystickValueProvider tiltRateProvider) {
    this(cameraMount, panRateProvider, tiltRateProvider, 0.45, 20);
  }

  /**
   * Constructor requiring a CameraMount to act upon and rate providers
   * to control rate of slew. Time to slew 180 in seconds and heartbeat
   * in milliseconds can be provided to establish max slewing rate.
   * 
   * @param cameraMount               A CameraMount subsystem.
   * @param panRateProvider           A value provider for panning.
   * @param tiltRateProvider          A value provider for tilting.
   * @param slewRate180DegreesInSec   Rate in seconds to slew 180 degrees.
   * @param heartbeatRateInMs         Rate in milliseconds for each call to execute method.
   */
  public SlewCamera(CameraMount cameraMount, 
      IJoystickValueProvider panRateProvider, 
      IJoystickValueProvider tiltRateProvider,
      double slewRate180DegreesInSec,
      double heartbeatRateInMs) {
    super("SlewCamera");
    if (cameraMount == null) {
      throw new NullPointerException("cameraMount cannot be null");
    }
    if (panRateProvider == null) {
      throw new NullPointerException("panRateProvider cannot be null");
    }
    if (tiltRateProvider == null) {
      throw new NullPointerException("tiltRateProvider cannot be null");
    }
    if (slewRate180DegreesInSec <= 0) {
      throw new IllegalArgumentException("slewRate180DegreesInSec must be > 0");
    }
    if (heartbeatRateInMs <= 0) {
      throw new IllegalArgumentException("heartbeatRateInMs must be > 0");
    }
    requires(cameraMount);
    this.cameraMount = cameraMount;
    this.panRateProvider = panRateProvider;
    this.tiltRateProvider = tiltRateProvider;
    this.slewRate180DegreesInSec = slewRate180DegreesInSec;
    this.heartbeatRateInMs = heartbeatRateInMs;
    this.maxDegreesPerHeartbeat = (180 / slewRate180DegreesInSec) / (1000 / heartbeatRateInMs);
  }

  /**
   * Called each time a servo update is to be made. Assumed frequency is
   * heartbeatRateInMs but should be verified with driving scheduler.
   */
  protected void execute() {
    double panAngle = cameraMount.getPanAngleInDegrees() + (this.maxDegreesPerHeartbeat * panRateProvider.getValue());
    double tiltAngle = cameraMount.getTiltAngleInDegrees() + (this.maxDegreesPerHeartbeat * tiltRateProvider.getValue());
    cameraMount.panToAngle((int) Math.round(panAngle));
    cameraMount.tiltToAngle((int) Math.round(tiltAngle));
  }

  /**
   * Finished when rate providers return zero rate factors or
   * servos are at their limits of travel.
   */
  protected boolean isFinished() {
    if ((panRateProvider.getValue() == 0.0 || cameraMount.atPanLimit()) && 
        tiltRateProvider.getValue() == 0.0 || cameraMount.atTiltLimit()) {
      return true;
    } else {
      return false;
    }
  }
}