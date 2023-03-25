package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.helpers.LedStripQuarter;
import frc.robot.helpers.enums.LEDQuarterTypes;

public class LedStrip extends SubsystemBase{

    int rIndex = 0;
    int gIndex = 1;
    int bIndex = 2;

    AddressableLED ledStrip;
    AddressableLEDBuffer ledBuffer;
    int m_rainbowFirstPixelHue = 0;
    //int oneFourth, oneHalf, threeQuarts, full;
    LedStripQuarter[] ledSections = new LedStripQuarter[3];
    //, q4;

    public boolean isYellow;

    public LedStrip() {
        ledStrip = new AddressableLED(0);
        ledBuffer = new AddressableLEDBuffer(280);
        ledStrip.setLength(ledBuffer.getLength());
        ledStrip.setData(ledBuffer);
        ledStrip.start();

        ledStrip.setData(ledBuffer);

        ledSections[0] = new LedStripQuarter(LEDQuarterTypes.Communication, 76);
        ledSections[1] = new LedStripQuarter(LEDQuarterTypes.KnightRider, 94);
        ledSections[2] = new LedStripQuarter(LEDQuarterTypes.KnightRider, 99);

        isYellow = true;
    }

    public void periodic() {
        //rainbow();
        readFromStrips();
        ledStrip.setData(ledBuffer);
    }

    private void readFromStrips() {
        int startIndex = 0;
        for (int i = 0; i < ledSections.length; i++)
        {
            var values = ledSections[i].getValues();
            setRange(startIndex, values);
            startIndex = startIndex + values.length;
        }
    }

    private void setRange(int startIndex, int[][] values) {
        for (int i = 0; i < values.length; i++) {
            ledBuffer.setRGB(i + startIndex, values[i][rIndex], values[i][gIndex], values[i][bIndex]);
        }
    }

    private void setWholeStripRGB(int red, int green, int blue) {
        for (int i = 0; i < ledBuffer.getLength(); i++) {
            ledBuffer.setRGB(i, red, green, blue);
        }
    }

    private void rainbow() {

        // For every pixel
        
    
        for (int i = 0; i < ledBuffer.getLength(); i++) {
    
    
          final int hue = (m_rainbowFirstPixelHue + (i * 180 / ledBuffer.getLength())) % 180;
    
          ledBuffer.setHSV(i, hue, 255, 16);
    
        }
    
        // Increase by to make the rainbow "move"
    
        m_rainbowFirstPixelHue += 3;
    
        // Check bounds
    
        m_rainbowFirstPixelHue %= 300;
    
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