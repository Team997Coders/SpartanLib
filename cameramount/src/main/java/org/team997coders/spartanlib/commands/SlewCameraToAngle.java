package org.team997coders.spartanlib.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team997coders.spartanlib.subsystems.CameraMount;

/**
 * This command will slew pan and tilt servos on the CameraMount
 * to specified angles at rates determined by slewRate180DegreesInSec 
 * and heartbeatRateInMs.
 */
public class SlewCameraToAngle extends Command {
  private final CameraMount cameraMount;
  private final int panAngle;
  private final int tiltAngle;
  private final double slewRate180DegreesInSec;
  private final double heartbeatRateInMs;
  private final double maxDegreesPerHeartbeat;

  /**
   * Constructor requiring a CameraMount to act upon and angles
   * to slew to (subject to max and mins established be the subsystem).
   * Time to slew 180 degrees is defaulted to
   * 0.45 seconds and the default heartbeat is 20 ms, which will
   * be used to calculate the rate of slew.
   * 
   * @param cameraMount       A CameraMount subsystem.
   * @param panAngle          A pan angle.
   * @param tiltAngle         A tilt angle.
   */
  public SlewCameraToAngle(CameraMount cameraMount, 
      int panAngle, 
      int tiltAngle) {
    this(cameraMount, panAngle, tiltAngle, 0.45, 20);
  }

  /**
   * Constructor requiring a CameraMount to act upon and angles to
   * slew to. Time to slew 180 in seconds and heartbeat
   * in milliseconds can be provided to establish slewing rate.
   * 
   * @param cameraMount               A CameraMount subsystem.
   * @param panAngle                  A pan angle.
   * @param tiltAngle                 A tilt angle.
   * @param slewRate180DegreesInSec   Rate in seconds to slew 180 degrees.
   * @param heartbeatRateInMs         Rate in milliseconds for each call to execute method.
   */
  public SlewCameraToAngle(CameraMount cameraMount, 
      int panAngle, 
      int tiltAngle,
      double slewRate180DegreesInSec,
      double heartbeatRateInMs) {
    super("SlewCamera");
    if (cameraMount == null) {
      throw new NullPointerException("cameraMount cannot be null");
    }
    if (slewRate180DegreesInSec <= 0) {
      throw new IllegalArgumentException("slewRate180DegreesInSec must be > 0");
    }
    if (heartbeatRateInMs <= 0) {
      throw new IllegalArgumentException("heartbeatRateInMs must be > 0");
    }
    requires(cameraMount);
    this.cameraMount = cameraMount;
    this.panAngle = panAngle;
    this.tiltAngle = tiltAngle;
    this.slewRate180DegreesInSec = slewRate180DegreesInSec;
    this.heartbeatRateInMs = heartbeatRateInMs;
    this.maxDegreesPerHeartbeat = (180 / slewRate180DegreesInSec) / (1000 / heartbeatRateInMs);
  }

  /**
   * Called each time a servo update is to be made. Assumed frequency is
   * heartbeatRateInMs but should be verified with driving scheduler.
   */
  protected void execute() {
    int nextPanAngle =(int) Math.round(cameraMount.getPanAngleInDegrees() + this.maxDegreesPerHeartbeat);
    int nextTiltAngle = (int) Math.round(cameraMount.getTiltAngleInDegrees() + this.maxDegreesPerHeartbeat);
    cameraMount.panToAngle(nextPanAngle > this.panAngle ? this.panAngle : nextPanAngle);
    cameraMount.tiltToAngle(nextTiltAngle > this.tiltAngle ? this.tiltAngle : nextTiltAngle);
  }

  /**
   * Finished when angles are reached or subsystem is at travel limits.
   */
  protected boolean isFinished() {
    if ((cameraMount.getPanAngleInDegrees() == this.panAngle || cameraMount.atPanLimit()) && 
        cameraMount.getTiltAngleInDegrees() == this.tiltAngle || cameraMount.atTiltLimit()) {
      return true;
    } else {
      return false;
    }
  }
}