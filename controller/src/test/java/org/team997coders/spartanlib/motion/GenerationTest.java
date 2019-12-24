package org.team997coders.spartanlib.motion;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.team997coders.spartanlib.motion.pathfinder.GenerationOverlord;
import org.team997coders.spartanlib.motion.pathfinder.TrajectoryIngredients;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

public class GenerationTest {

  @Test
  public void TestGeneration() {
    Waypoint[] points0 = new Waypoint[] { new Waypoint(-4, -1, Pathfinder.d2r(-45)), new Waypoint(-2, -2, 0),
        new Waypoint(0, 0, 0) };
    Waypoint[] points1 = new Waypoint[] { new Waypoint(-4, -1, Pathfinder.d2r(-45)), new Waypoint(2, -2, 0),
        new Waypoint(0, 0, 0) };
    Waypoint[] points2 = new Waypoint[] { new Waypoint(-4, -1, Pathfinder.d2r(-45)), new Waypoint(-2, -2, 0),
        new Waypoint(0, 0, 0) };
    Waypoint[] points3 = new Waypoint[] { new Waypoint(-4, -1, Pathfinder.d2r(-45)), new Waypoint(2, -2, 0),
        new Waypoint(0, 0, 0) };
    Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH,
        0.05, 1.7, 2.0, 60.0);

    TrajectoryIngredients t0 = new TrajectoryIngredients("t0", points0, config);
    TrajectoryIngredients t1 = new TrajectoryIngredients("t1", points1, config);
    TrajectoryIngredients t2 = new TrajectoryIngredients("t2", points2, config);
    TrajectoryIngredients t3 = new TrajectoryIngredients("t3", points3, config);

    GenerationOverlord.getInstance().addIngredient(t0);
    GenerationOverlord.getInstance().addIngredient(t1);
    GenerationOverlord.getInstance().addIngredient(t2);
    GenerationOverlord.getInstance().addIngredient(t3);

    long startTime = System.currentTimeMillis();
    GenerationOverlord.getInstance().summonDaemons();

    Trajectory one = null, two = null, three = null, four = null;

    while (true) {
      if (one == null) {
        one = GenerationOverlord.getInstance().getOutput(t0.name);
      }
      if (two == null) {
        two = GenerationOverlord.getInstance().getOutput(t1.name);
      }
      if (three == null) {
        three = GenerationOverlord.getInstance().getOutput(t2.name);
      }
      if (four == null) {
        four = GenerationOverlord.getInstance().getOutput(t3.name);
      }

      if ((one != null) && (two != null) && (three != null) && (four != null)) {
        break;
      }
    }

    System.out.println("Done in " + ((double)(System.currentTimeMillis() - startTime) / 1000) + " seconds");

    startTime = System.currentTimeMillis();

    TankModifier mod = new TankModifier(one).modify(0.5);

    Trajectory left = mod.getLeftTrajectory();
    Trajectory right = mod.getRightTrajectory();

    System.out.println("Modded in " + ((double)(System.currentTimeMillis() - startTime) / 1000) + " seconds");

    assertTrue(true);
  }

}