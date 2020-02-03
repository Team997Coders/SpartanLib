package org.team997coders.spartanlib.core.threading;

public class Daemon {

  private Runnable mExec;
  private Thread mThread;

  /**
   * A daemon to process, then wait for data
   * 
   * @param exec What to do every update
   */
  public Daemon(Runnable exec) {
    mExec = exec;
    mThread = new Thread(mExec);
  }

  public void start() {
    mThread.start();
  }

  public boolean cancel() {
    try {
      mThread.join(100);
      return true;
    } catch (Exception e) { return false; }
  }

}