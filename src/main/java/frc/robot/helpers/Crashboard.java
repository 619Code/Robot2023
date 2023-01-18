package frc.robot.helpers;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Crashboard {

    public static boolean toDashboard(String identifier, double value) {

        SmartDashboard.putNumber(identifier, value);
        if (SmartDashboard.getEntry(identifier).getDouble(value) == value) {
            return true;
        }

        return false;
    }

    public static boolean toDashboard(String identifier, boolean value) {

        SmartDashboard.putBoolean(identifier, value);
        if (SmartDashboard.getEntry(identifier).getBoolean(value) == value) {
            return true;
        }

        return false;
    }

    public static double snagDouble(String identifier) {

        return SmartDashboard.getEntry(identifier).getDouble(0);

        //return 0;

    }

    public static boolean snagBoolean(String identifier) {

        return SmartDashboard.getEntry(identifier).getBoolean(false);

        //return 0;

    }

    public static double clamp(double in, double min, double max) {
        if (in < min) in = min;
        if (in > max) in = max;
        return in;
    }

    /* 
    public static boolean scaleSlider(String identifier) {

        SmartDashboard.getEntry(identifier).

        return false;
    }
    */

}
