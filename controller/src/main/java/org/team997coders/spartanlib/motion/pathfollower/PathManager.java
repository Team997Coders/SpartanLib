package org.team997coders.spartanlib.motion.pathfollower;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.team997coders.spartanlib.core.threading.Daemon;

import edu.wpi.first.wpilibj.trajectory.Trajectory;

public class PathManager {

  private Daemon[] mDaemons;
  private ConcurrentHashMap<String, Trajectory> mProcessedData;
  private ConcurrentLinkedQueue<PathData> mRawData;

  private PathManager() {
    mDaemons = new Daemon[2];
    mRawData = new ConcurrentLinkedQueue<PathData>();
    mProcessedData = new ConcurrentHashMap<String, Trajectory>();
  }

  public void startDaemons() {
    for (int i = 0; i < mDaemons.length; i++) {
      mDaemons[i] = new Daemon(() -> processData());
    }
  }

  public void stopDaemons() {
    for (int i = 0; i < mDaemons.length; i++) {
      mDaemons[i].cancel();
    }
  }

  private void processData() {
    if (mRawData.size() > 0) {
      PathData pd = mRawData.poll();
      try {
        if (pd != null) {
          Trajectory t = pd.processTrajectory();
          if (t != null) {
            mProcessedData.put(pd.getName(), t);
          }
        }
      } catch (Exception e) {
      }
    } else {
      try {
        Thread.sleep(250);
      } catch (Exception e) {
        try { Thread.currentThread().join(); } catch (Exception e2) { }
      }
    }
  }

  public Trajectory getTrajectory(String name) {
    return mProcessedData.get(name);
  }

  private static PathManager instance;
  public static PathManager getInstance() {
    if (instance == null)
      instance = new PathManager();
    return instance;
  }

}