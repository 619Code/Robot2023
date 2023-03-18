package frc.robot.helpers;

public class nightrider {
    private static boolean[] ledStrip;
    private static int startPos;
    private static int endPos;
    private static int size;
    private static int direction;

    public nightrider() {
        ledStrip = new boolean[300];
        startPos = 0;
        size = 300/8;
        direction = 1;
        endPos = startPos + size;
    }

    public boolean[] looooop(){

        startPos += direction; endPos += direction;

        if (startPos == 0) {
            direction = 1;
        }
        else if (endPos == ledStrip.length - 1) {
            direction = -1;
        }

        for (int i = 0; i < ledStrip.length; i++){
            ledStrip[i] = false;
        }
        for (int i = startPos; i >= startPos && i <= endPos; i++){
            ledStrip[i] = true;
        }

        return ledStrip;
    }

    
}
