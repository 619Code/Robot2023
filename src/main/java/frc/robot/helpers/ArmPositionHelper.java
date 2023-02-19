package frc.robot.helpers;

import frc.robot.Constants;

public class ArmPositionHelper {
    public static double[] fetch(Position position) {
        switch(position) {
            case START:
                return Constants.START_POSITION;
            case PICKUP:
                return Constants.PICKUP_POSITION;
            case GRID_MID:
                return Constants.GRID_MID_POSITION;
            case GRID_HIGH:
                return Constants.GRID_HIGH_POSITION;
            default:
                return Constants.START_POSITION;
        }
    }
}