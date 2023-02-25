package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LedStrip extends SubsystemBase{
    AddressableLED ledStrip;
    AddressableLEDBuffer ledBuffer;

    public boolean isYellow;

    public LedStrip() {
        ledStrip = new AddressableLED(Constants.LED_PWM_PORT);
        ledBuffer = new AddressableLEDBuffer(Constants.LED_STRIP_LENGTH);
        ledStrip.setLength(Constants.LED_STRIP_LENGTH);
        ledStrip.setData(ledBuffer);
        ledStrip.start();

        off();

        isYellow = false;
    }

    private void setWholeStripRGB(int red, int green, int blue) {
        for (int i = 0; i < Constants.LED_STRIP_LENGTH; i++) {
            ledBuffer.setRGB(i, red, green, blue);
        }
    }

    public void setColorYellow() {
        setWholeStripRGB(255, 125, 0);
        isYellow = true;
        show();
    }

    public void setColorPurple() {
        setWholeStripRGB(125, 0, 255);
        isYellow = false;
        show();
    }

    public void off(){
        setWholeStripRGB(0, 0, 0);
        show();
    }

    public void show() {
        ledStrip.setData(ledBuffer);
    }
}