package frc.robot.helpers;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

public class ColorDetector {
    private ColorSensorV3 colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
    
    private Color detectedColor;
    public boolean isCube;

    public void update() {
        detectedColor = colorSensor.getColor();

        if(detectedColor.blue > 0.2) {
            isCube = true;
        } else {
            isCube = false;
        }
    }
}
