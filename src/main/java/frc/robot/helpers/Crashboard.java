package frc.robot.helpers;

import java.util.Dictionary;
import java.util.Hashtable;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.WidgetType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Crashboard {

    private static Dictionary<String, GenericEntry> DashboardEntries = new Hashtable<>();

    public static GenericEntry toDashboard(String identifier, double value, String tab) {
        return toDashboardGeneric(identifier, value, tab);
    }

    private static GenericEntry toDashboardGeneric(String identifier, Object value, String tab) {

        GenericEntry genericEntry = DashboardEntries.get(getKey(identifier, tab));
        if (genericEntry == null) {
            genericEntry = Shuffleboard.getTab(tab).add(identifier, value).getEntry();
            DashboardEntries.put(getKey(identifier, tab), genericEntry);
        } else {
            genericEntry.setValue(value);
        }
        return genericEntry;

    }

    public static ComplexWidget AddChooser(String identifier, SendableChooser<String> value, String tab, BuiltInWidgets widget) {
        return Shuffleboard.getTab(tab).add(identifier, value).withWidget(widget);
    }

    private static String getKey(String identifier, String tab) {
        return identifier + "-" + tab;
    }

    public static GenericEntry toDashboard(String identifier, boolean value, String tab) {
        return toDashboardGeneric(identifier, value, tab);
    }

    public static GenericEntry toDashboard(String identifier, Sendable value, String tab) {
        return toDashboardGeneric(identifier, value, tab);
    }


    public static double clamp(double in, double min, double max) {
        if (in < min)
            in = min;
        if (in > max)
            in = max;
        return in;
    }

    /*
     * public static boolean scaleSlider(String identifier) {
     * 
     * SmartDashboard.getEntry(identifier).
     * 
     * return false;
     * }
     */
}
