package org.team997coders.spartanlib.scouting.data;

public class DeepSpaceData implements GameData {

  public int teamNumber, matchNumber, lowCargo, midCargo, highCargo, csCargo, lowHatch, midHatch, highHatch, climb,
      autoHab, defense, penalty;

  public Color alliance = Color.Blue;

  public enum Color {
    Red(0), Blue(1);

    int value;

    Color(int v) {
      value = v;
    }

    public int getValue() {
      return value;
    }
  }

}