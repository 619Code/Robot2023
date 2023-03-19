package frc.robot.subsystems;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.nightrider;

public class LedStripArm extends SubsystemBase{
    AddressableLED ledStrip;
    AddressableLEDBuffer ledBuffer;
    int m_rainbowFirstPixelHue = 0;
    nightrider sixoneninelights;

    GenericEntry hue, sat, val;

    private boolean[] array;

    public boolean isYellow;

    public LedStripArm(int length, int pwm) {


        ledStrip = new AddressableLED(pwm);
        ledBuffer = new AddressableLEDBuffer(length);
        ledStrip.setLength(ledBuffer.getLength());
        //ledStrip.setData(ledBuffer);
        //off();
        ledStrip.start();
        array = new boolean[ledBuffer.getLength()];
        for (int i = 0; i < array.length; i ++) {
            if (i % 2 == 0) {
                array[i] = true;
            } else array[i] = false;
        }
        off();
        hue = Crashboard.toDashboard("hue", 0, Constants.GRABBER_TAB);
        sat = Crashboard.toDashboard("sat", 0, Constants.GRABBER_TAB);
        val = Crashboard.toDashboard("val", 0, Constants.GRABBER_TAB);

        //rainbow();
        // setWholeStripHSV(255, 79, 100);
        ledStrip.setData(ledBuffer);
        show();
        System.out.println("PZDFKGHESKJGNUI");

        

        isYellow = true;
    }

    public void periodic() {
        //rainbow();
        //setWholeStripHSV(128, 79, 100);
        //setColorPurple();
        purpleOrYellow();
        //setByArray(sixoneninelights.looooop());
        //funnymode();
        ledStrip.setData(ledBuffer);
    }

    private void setWholeStripHSV(int h, int s, int v) {
        for (int i = 0; i < ledBuffer.getLength(); i++) {
            ledBuffer.setHSV(i, h, s, v);
        }
    }

    private void setByArray(boolean[] in) {
        for (int i = 0; i < ledBuffer.getLength(); i++) {
            try {
                if (in[i]) {
                    ledBuffer.setHSV(i,270,66,60);
                } else {
                    ledBuffer.setHSV(i, 125,255,127);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                ledBuffer.setHSV(i, 0, 0, 0);
            }
        }
    }

    private void purpleOrYellow() {
        if (this.isYellow) setColorYellow();
        else setColorPurple();
    }

    private void rainbow() {

        // For every pixel
        
    
        for (int i = 0; i < ledBuffer.getLength(); i++) {
    
    
          final int hue = (m_rainbowFirstPixelHue + (i * 180 / ledBuffer.getLength())) % 180;
    
          ledBuffer.setHSV(i, hue, 255, 128);
    
        }
    
        // Increase by to make the rainbow "move"
    
        m_rainbowFirstPixelHue += 3;
    
        // Check bounds
    
        m_rainbowFirstPixelHue %= 300;
    
      }

    public void setColorYellow() {
        //setWholeStripHSV(270,66,60); //THIS USED TO BE YELLOW. NOW IT IS TEAL. WHAT AM I DOING WRONG.
        setWholeStripHSV(0, 255, 127);  //THIS WAS RED AT THE TIME OF WRITING.
        isYellow = true;
        show();
    }

    public void setColorPurple() {
        setWholeStripHSV(125,255,127); //THIS USED TO BE OURPLE. ITS BLUE. WHY.
        isYellow = false;
        show();
    }

    public void setColorSlider() {
        setWholeStripHSV((int)hue.getInteger(0),(int)sat.getInteger(0),(int)val.getInteger(0));
        
        show();
    }

    public void funnymode() {
        for (int i = 0; i < ledBuffer.getLength(); i++) {
            ledBuffer.setHSV(i, (int)Math.random()*365, 255, 127);
        }
    }

    public void off(){
        setWholeStripHSV(0, 0, 0);
        show();
    }

    public void show() {
        ledStrip.setData(ledBuffer);
    }
}