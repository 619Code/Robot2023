package frc.robot.helpers;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class Crashboard {

    public static GenericEntry toDashboard(String identifier, double value, String Tab) {
       return Shuffleboard.getTab(Tab).add(identifier, value).getEntry();
    }

    public static GenericEntry toDashboard(String identifier, boolean value, String Tab) {
       return Shuffleboard.getTab(Tab).add(identifier, value).getEntry();
    }

    public static double clamp(double in, double min, double max) {
        if (in < min) in = min;
        if (in > max) in = max;
        return in;
    }


    /* public static boolean scaleSlider(String identifier) {

        SmartDashboard.getEntry(identifier).

        return false;
    } */
}
