package org.team997coders.spartanlib.math;

public class MathUtils {

    public static double clamp(double value, double min, double max) {
        if (value > max) return max; 
        else if (value < min) return min;
        else return value;
    }

    public static double deadband(double value, double deadband) {
        if (Math.abs(value) < deadband) return 0;
        else return value;
    }

}