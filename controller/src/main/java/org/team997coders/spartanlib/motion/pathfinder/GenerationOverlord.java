package org.team997coders.spartanlib.motion.pathfinder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

public class GenerationOverlord {

  private static final int DAEMON_COUNT = 2;
  private static final Lock QUEUE_LOCK = new ReentrantLock();
  private static final Lock OUTPUT_LOCK = new ReentrantLock();

  private Queue<TrajectoryIngredients> queue;
  private HashMap<String, Trajectory> output;
  private Thread[] daemons;

  private static GenerationOverlord instance;

  public static synchronized GenerationOverlord getInstance() {
    if (instance == null) {
      instance = new GenerationOverlord();
    }
    return instance;
  }

  public GenerationOverlord() {
    output = new HashMap<String, Trajectory>();
    queue = new LinkedList<TrajectoryIngredients>();
  }

  public void addIngredient(TrajectoryIngredients a) {
    QUEUE_LOCK.lock();
    queue.add(a);
    QUEUE_LOCK.unlock();
  }

  public void summonDaemons() {
    daemons = new Thread[DAEMON_COUNT];
    for (int i = 0; i < daemons.length; i++) {
      daemons[i] = new Thread(this::process);
      daemons[i].setDaemon(true);
      daemons[i].start();
    }
  }

  private void process() {

    TrajectoryIngredients a = null;
    Trajectory traj = null;
    boolean status = true;

    while (status) {
      QUEUE_LOCK.lock();
      if (queue.size() > 0) {
        a = queue.poll();
      }
      QUEUE_LOCK.unlock();

      if (a == null) {
        status = false;
        break;
      }

      traj = Pathfinder.generate(a.points, a.config);

      addOutput(a.name, traj);
    }

    try {
      Thread.currentThread().join();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Failed to join Daemon");
    }

    return;
  }

  private void addOutput(String name, Trajectory output) {
    OUTPUT_LOCK.lock();
    this.output.put(name, output);
    OUTPUT_LOCK.unlock();
  }

  public Trajectory getOutput(String name) {
    Trajectory a = null;
    OUTPUT_LOCK.lock();
    if (output.containsKey(name)) {
      a = output.get(name);
    }
    OUTPUT_LOCK.unlock();
    return a;
  }

  public void killDaemons() {
    for (int i = 0; i < daemons.length; i++) {
      daemons[i].interrupt();
    }
  }

}