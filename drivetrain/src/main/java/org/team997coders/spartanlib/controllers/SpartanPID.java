package org.team997coders.spartanlib.controllers;

public class SpartanPID {

  private double mP = 0.0;
  private double mI = 0.0;
  private double mD = 0.0;

  private double mMinOutput = Double.NEGATIVE_INFINITY;
  private double mMaxOutput = Double.POSITIVE_INFINITY;

  private double mSetpoint = 0.0;

  private double intAccum = 0.0;
  private double lastErr = Double.NaN;

  public SpartanPID(double p, double i, double d) {
    mP = p;
    mI = i;
    mD = d;
  }

  public double WhatShouldIDo(double current, double deltaT) {

    double error = mSetpoint - current;

    if (!epsilon(error, Math.copySign(error, intAccum)) && !epsilon(intAccum, 0.0)) { // Basically if it over shot the and the integral is causing it, hit the big red button
      intAccum = 0.0;
    }

    intAccum += error * deltaT; // Add the new stuff

    double derivative = 0.0;
    if (Double.isFinite(lastErr)) {
      derivative = (error - lastErr) / deltaT;
    }
    lastErr = error;

    double p = mP * error;
    double i = mI * intAccum;
    double d = mD * derivative;

    return clamp(p + i + d, mMinOutput, mMaxOutput);
  }

  public void reset() { intAccum = 0.0; lastErr = Double.NaN; }

  public boolean epsilon(double a, double b) {
    return Math.abs(a - b) < 1e-9; // Literally just a tolerance because lazy
  }

  public double clamp(double val, double min, double max) {
    if (val < min) return min;
    else if (val > max) return max; // Yet another lazy method
    else return val;
  }

  /**
   * MWAHAHAHA I USED CODE TO GENERATE CODE. BOW DOWN TO MY SKILLLZZZ
   */

  public double getP() {
    return mP;
  }

  public void setP(double P) {
    mP = P;
  }

  public double getI() {
    return mI;
  }

  public void setI(double I) {
    mI = I;
  }

  public double getD() {
    return mD;
  }

  public void setD(double D) {
    mD = D;
  }

  public double getMinOutput() {
    return mMinOutput;
  }

  public void setMinOutput(double MinOutput) {
    mMinOutput = MinOutput;
  }

  public double getMaxOutput() {
    return mMaxOutput;
  }

  public void setMaxOutput(double MaxOutput) {
    mMaxOutput = MaxOutput;
  }

  public double getSetpoint() {
    return mSetpoint;
  }

  public void setSetpoint(double Setpoint) {
    mSetpoint = Setpoint;
  }
}