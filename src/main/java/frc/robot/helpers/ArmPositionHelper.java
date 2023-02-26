package frc.robot.helpers;

import frc.robot.Constants;

public class ArmPositionHelper {
    public static Position currentPosition = Position.GRID_MID;
    public static boolean atHingePosition = true;
    public static boolean retracted = true;
    public static boolean atTelescopePosition = true;

    public static double fetchHingeValue(Position position) {
        switch(position) {
            case START:
                return Constants.START_POSITION_HINGE;
            case PICKUP:
                return Constants.PICKUP_POSITION_HINGE;
            case GRID_MID:
                return Constants.GRID_MID_POSITION_HINGE;
            case GRID_HIGH:
                return Constants.GRID_HIGH_POSITION_HINGE;
            default:
                return Constants.START_POSITION_HINGE;
        }
    }

    public static double fetchTelescopeValue(Position position) {
        switch(position) {
            case START:
                return Constants.START_POSITION_TELESCOPE;
            case PICKUP:
                return Constants.PICKUP_POSITION_TELESCOPE;
            case GRID_MID:
                return Constants.GRID_MID_POSITION_TELESCOPE;
            case GRID_HIGH:
                return Constants.GRID_HIGH_POSITION_TELESCOPE;
            default:
                return Constants.START_POSITION_TELESCOPE;
        }
    }
}