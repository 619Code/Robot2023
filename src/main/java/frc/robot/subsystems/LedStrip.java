package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.LEDQuarterTypes;

public class LedStrip extends SubsystemBase{
    AddressableLED ledStrip;
    AddressableLEDBuffer ledBuffer;
    int m_rainbowFirstPixelHue = 0;

    int oneFourth, oneHalf, threeQuarts, full;

    LedStripQuarter q1, q2, q3, q4;

    public boolean isYellow;

    public LedStrip() {
        ledStrip = new AddressableLED(0);
        ledBuffer = new AddressableLEDBuffer(120);
        ledStrip.setLength(ledBuffer.getLength());
        ledStrip.setData(ledBuffer);
        ledStrip.start();

        oneFourth = ledBuffer.getLength()/4;
        oneHalf = 2*ledBuffer.getLength()/4;
        threeQuarts = 3*ledBuffer.getLength()/4;
        full = ledBuffer.getLength();

        //rainbow();
        ledStrip.setData(ledBuffer);
        System.out.println("PZDFKGHESKJGNUI");

        q1 = new LedStripQuarter(LEDQuarterTypes.KnightRider, ledBuffer.getLength()/4);
        q2 = new LedStripQuarter(LEDQuarterTypes.Communication, ledBuffer.getLength()/4);
        q3 = new LedStripQuarter(LEDQuarterTypes.Communication, ledBuffer.getLength()/4);
        q4 = new LedStripQuarter(LEDQuarterTypes.KnightRider, ledBuffer.getLength()/4);

        isYellow = true;
    }

    public void periodic() {
        //rainbow();
        readFromStrips();
        // setWholeStripRGB(0, 0, 255, 0, ledBuffer.getLength()/4);
        // setWholeStripRGB(255, 0, 255, ledBuffer.getLength()/4, 2 * ledBuffer.getLength()/4);
        // setWholeStripRGB(0, 255, 0, ledBuffer.getLength()/2, 3*ledBuffer.getLength()/4);
        // setWholeStripRGB(255, 0, 255, 3*ledBuffer.getLength()/4,ledBuffer.getLength());


        //funnymode();
        ledStrip.setData(ledBuffer);
    }

    private void readFromStrips() {

        int[][] q1v = q1.getValues();
        int[][] q2v = q2.getValues();
        int[][] q3v = q3.getValues();
        int[][] q4v = q4.getValues();

        //kr stuff

        for (int i = 0; i < oneFourth; i ++) {
            ledBuffer.setRGB(i, q1v[i][0], q1v[i][1], q1v[i][2]);
        }

        for (int i = 0; i < oneFourth; i ++) {
            ledBuffer.setRGB(i + oneFourth, q2v[i][0], q2v[i][1], q2v[i][2]);
        }

        for (int i = 0; i < oneFourth; i ++) {
            ledBuffer.setRGB(i+oneHalf, q3v[i][0], q3v[i][1], q3v[i][2]);
        }

        for (int i = 0; i < oneFourth; i ++) {
            ledBuffer.setRGB(i+threeQuarts, q4v[i][0], q4v[i][1], q4v[i][2]);
        }
        
    }

    private void setWholeStripRGB(int red, int green, int blue) {
        for (int i = 0; i < ledBuffer.getLength(); i++) {
            ledBuffer.setRGB(i, red, green, blue);
        }
    }
    private void setPartialStripRGB(int red, int green, int blue, int start, int end) {
        for (int i = start; i < end; i++) {
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