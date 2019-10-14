package org.team997coders.spartanlib.scouting.data;

public interface GameData {

  public int teamNumber = 0, matchNumber = 0;
  public Color alliance = Color.Blue;

  public enum Color {
    Red(0), Blue(1);

    int value;

    Color(int v) {
      value = v;
    }

    public int getValue() { return value; }
  }
}