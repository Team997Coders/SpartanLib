package org.team997coders.spartanlib.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team997coders.spartanlib.interfaces.IJoystickValueProvider;
import org.team997coders.spartanlib.subsystems.CameraMount;

public class SlewCamera extends Command {
  private final CameraMount cameraMount;
  private final IJoystickValueProvider panRateProvider;
  private final IJoystickValueProvider tiltRateProvider;
  private final double slewRate180Degrees = 0.45;
  private final double heartbeatRateInmS = 20;
  private final double maxDegreesPerHeartbeat;

  public SlewCamera(CameraMount cameraMount, 
      IJoystickValueProvider panRateProvider, 
      IJoystickValueProvider tiltRateProvider) {
    super("SlewCamera");
    if (cameraMount == null) {
      throw new NullPointerException("cameraMount cannot be null");
    }
    requires(cameraMount);
    this.cameraMount = cameraMount;
    this.panRateProvider = panRateProvider;
    this.tiltRateProvider = tiltRateProvider;
    this.maxDegreesPerHeartbeat = 180 / (slewRate180Degrees / (heartbeatRateInmS / 1000));
  }

  protected void execute() {
    double panAngle = cameraMount.getPanAngleInDegrees() + (this.maxDegreesPerHeartbeat * panRateProvider.getValue());
    double tiltAngle = cameraMount.getTiltAngleInDegrees() + (this.maxDegreesPerHeartbeat * tiltRateProvider.getValue());
    cameraMount.panToAngle((int) Math.round(panAngle));
    cameraMount.tiltToAngle((int) Math.round(tiltAngle));
  }

  protected boolean isFinished() {
    if ((panRateProvider.getValue() == 0.0 || cameraMount.atPanLimit()) && 
        tiltRateProvider.getValue() == 0.0 || cameraMount.atTiltLimit()) {
      return true;
    } else {
      return false;
    }
  }
}