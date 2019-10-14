package org.team997coders.spartanlib.scouting.data;

public interface GameData {
  
  public int teamNumber, matchNumber;
  public Color alliance;

  public enum Color {
    Red(0), Blue(1);

    int value;

    Color(int v) {
      value = v;
    }

    public int getValue() { return value; }
  }
}