package org.team997coders.spartanlib.scouting;

public class DeepSpaceData {

  public enum Color {
    Red(0), Blue(1);

    int value;

    Color(int v) {
      value = v;
    }

    public int getValue() { return value; }
  }

  public int matchNumber, teamNumber, cargo, hatches;
  public Color alliance;
}