package frc.robot.subsystems;


public class nightrider {
    private  boolean[] ledStrip;
    private  int startPos;
    private  int endPos;
    private  int size;
    private int direction;

    public nightrider(int length) {
        ledStrip = new boolean[length];
        startPos = 0;
        size = length/4;
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
