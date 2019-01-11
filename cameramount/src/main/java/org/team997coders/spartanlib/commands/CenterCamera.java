package org.team997coders.spartanlib.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team997coders.spartanlib.subsystems.CameraMount;

/**
 * This command moves both camera mount axes to the 90 degree position
 */
public class CenterCamera extends Command {
  private final CameraMount cameraMount;

  /**
   * Constructor for the center camera command
   * 
   * @param cameraMount The CameraMount subsystem for the command to act upon
   */
  public CenterCamera(CameraMount cameraMount) {
    super("CenterCamera");
    if (cameraMount == null) {
      throw new NullPointerException("cameraMount cannot be null");
    }
    requires(cameraMount);
    this.cameraMount = cameraMount;
  }

  /**
   * Move camera to 90 degree position for both pan and tilt.
   */
  protected void initialize() {
    cameraMount.panToAngle(90);
    cameraMount.tiltToAngle(90);
  }

  /**
   * This command is done right away
   */
  protected boolean isFinished() {
    return true;
  }
}