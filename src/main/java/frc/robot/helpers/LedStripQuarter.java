package frc.robot.helpers;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.States;
import frc.robot.helpers.enums.LEDQuarterTypes;

public class LedStripQuarter {
    private int[][] ledRGBs;
    private LEDQuarterTypes type;
    private nightrider nr;

    private SimpleColor Orange = new SimpleColor(255, 64, 0);
    private SimpleColor Blue = new SimpleColor(0, 0, 255);
    private SimpleColor Yellow = new SimpleColor(255, 128,0);
    private SimpleColor LightYellow = new SimpleColor(255, 179, 102);
    private SimpleColor Purple = new SimpleColor(168, 0, 149);
    private SimpleColor LightPurple = new SimpleColor(255, 102, 237);

    public LedStripQuarter(LEDQuarterTypes type, int length) {
        this.type = type;
        ledRGBs = new int[length][3]; //2D array, first array is LED array, second dimension is HSV values. Dont forget :3 
        nr = new nightrider(length);
    }

    public int[][] getValues() {
        switch(type)
        {
            case Communication:
                return getValuesComms();
            case KnightRiderComs: 
                return getValuesKnightRiderComms();
            default: 
                return getValuesKnightRider();
        }
    }

    private int[][] getValuesComms() {
        for (int i = 0; i < ledRGBs.length; i ++) {
            if (States.lightsAreYellow) {
                ledRGBs[i][0] = Yellow.red;
                ledRGBs[i][1] = Yellow.green;
                ledRGBs[i][2] = Yellow.blue; //V value reduced to darken LEDs and lower power draw.
            } else {
                ledRGBs[i][0] = Purple.red;
                ledRGBs[i][1] = Purple.green;
                ledRGBs[i][2] = Purple.blue; //V value reduced to darken LEDs and lower power draw.
            }
        } return ledRGBs;
    }

    public int[][] getValuesKnightRiderComms() {
        if (States.lightsAreYellow)
        {
            return this.getValuesKnightRider(LightYellow, Yellow);            
        }
        else
        {
            return this.getValuesKnightRider(LightPurple, Purple);
        }
    }

    private int[][] getValuesKnightRider(SimpleColor inner, SimpleColor outer)
    {
        boolean[] vals = nr.looooop();
        for (int i = 0; i < vals.length; i ++) {
            if (vals[i]) {
                ledRGBs[i][0] = inner.red;
                ledRGBs[i][1] = inner.green;
                ledRGBs[i][2] = inner.blue; //V value reduced to darken LEDs and lower power draw.
            } else {
                ledRGBs[i][0] = outer.red;
                ledRGBs[i][1] = outer.green;
                ledRGBs[i][2] = outer.blue; //V value reduced to darken LEDs and lower power draw.
            }
        }
        return ledRGBs;
    }

    public int[][] getValuesKnightRider() {
        return getValuesKnightRider(Orange, Blue);
    }
    
}
