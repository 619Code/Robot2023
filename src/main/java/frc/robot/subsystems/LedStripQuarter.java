package frc.robot.subsystems;

import frc.robot.States;

public class LedStripQuarter {
    private int[][] ledRGBs;
    private LEDQuarterTypes type;
    private nightrider nr;
    
    public LedStripQuarter(LEDQuarterTypes type, int length) {
        this.type = type;
        ledRGBs = new int[length][3]; //2D array, first array is LED array, second dimension is HSV values. Dont forget :3 
        nr = new nightrider(length);
    }

    public int[][] getValues() {
        if (type.equals(LEDQuarterTypes.Communication)) {
            for (int i = 0; i < ledRGBs.length; i ++) {
                if (States.lightsAreYellow) {
                ledRGBs[i][0] = 255;
                ledRGBs[i][1] = 128;
                ledRGBs[i][2] = 0; //V value reduced to darken LEDs and lower power draw.
                } else {
                ledRGBs[i][0] = 168;
                ledRGBs[i][1] = 0;
                ledRGBs[i][2] = 149; //V value reduced to darken LEDs and lower power draw.
                }
            } return ledRGBs;
        } else { //is KnightRider
            boolean[] vals = nr.looooop();
            for (int i = 0; i < vals.length; i ++) {
                if (vals[i]) {
                    ledRGBs[i][0] = 255;
                    ledRGBs[i][1] = 64;
                    ledRGBs[i][2] = 0; //V value reduced to darken LEDs and lower power draw.
                } else {
                    ledRGBs[i][0] = 0;
                    ledRGBs[i][1] = 0;
                    ledRGBs[i][2] = 255; //V value reduced to darken LEDs and lower power draw.
                }
            }
            return ledRGBs;
        }
    }
}
