package org.team997coders.spartanlib.commands;

import org.team997coders.spartanlib.helpers.threading.SpartanAction;
import org.team997coders.spartanlib.swerve.module.SwerveModule;

import edu.wpi.first.wpilibj.command.Subsystem;

public class UpdateModule extends SpartanAction {

  // private final int ALIGNMENT_TIMEOUT = 1250; // Milliseconds until I start complaining
  // private final double ALIGNMENT_TOLERANCE = 2.5; // Tolerance in degrees

  private SwerveModule<Object, Object, Object> mod;

  // private double lastTargetAngle = 0;
  // private double lastGoodAlignment;

  public UpdateModule(SwerveModule<Object, Object, Object> module, Subsystem s) {
    mod = module;
    requires(s);
  }

  public UpdateModule(SwerveModule<Object, Object, Object> module) {
    mod = module;
  }

  @Override
  protected void init() {
    //if (mod instanceof ProtoModule) {
      //((ProtoModule)mod).resetAzimuthController();
    //}
    // lastGoodAlignment = System.currentTimeMillis();
  }

  @Override
  protected void exec() {
    mod.update();
  }

  @Override
  protected boolean isDone() {
    return false;
  }

  @Override
  protected void end() {
    // module().setAzimuthSpeed(0);
    // module().setTargetSpeed(0);
  }

  @Override
  protected void interrupt() {
    System.out.println("[" + mod.mID + "] Interrupted");
    end();
  }

}