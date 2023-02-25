package frc.robot.helpers;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Constants;

public class ColorDetector {
    private static ColorSensorV3 colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
    
    private static Color detectedColor;
    public static boolean isCube;

    public static void update() {
        detectedColor = colorSensor.getColor();

        if(detectedColor.blue > 0.2) {
            isCube = true;
        } else {
            isCube = false;
        }

        Crashboard.toDashboard("Cube Detected", isCube, Constants.GrabTab);
        Crashboard.toDashboard("Cone Detected", !isCube, Constants.GrabTab);
    }
    
}
