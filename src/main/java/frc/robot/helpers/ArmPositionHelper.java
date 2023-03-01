package frc.robot.helpers;

import frc.robot.Constants;
import frc.robot.helpers.enums.ArmPosition;

public class ArmPositionHelper {
    public static ArmPosition currentPosition = ArmPosition.GRID_MID;
    public static boolean atHingePosition = true;
    public static boolean retracted = true;
    public static boolean atTelescopePosition = true;

    public static double fetchHingeValue(ArmPosition position) {
        switch(position) {
            case START:
                return Constants.START_POSITION_HINGE;
            case PICKUP_LOW:
                return Constants.PICKUP_LOW_POSITION_HINGE;
            case PICKUP_HIGH:
                return Constants.PICKUP_HIGH_POSITION_HINGE;
            case GRID_MID:
                return Constants.GRID_MID_POSITION_HINGE;
            case GRID_HIGH:
                return Constants.GRID_HIGH_POSITION_HINGE;
            default:
                return Constants.START_POSITION_HINGE;
        }
    }

    public static double fetchTelescopeValue(ArmPosition position) {
        switch(position) {
            case START:
                return Constants.START_POSITION_TELESCOPE;
            case PICKUP_LOW:
                return Constants.PICKUP_LOW_POSITION_TELESCOPE;
            case PICKUP_HIGH:
                return Constants.PICKUP_HIGH_POSITION_TELESCOPE;
            case GRID_MID:
                return Constants.GRID_MID_POSITION_TELESCOPE;
            case GRID_HIGH:
                return Constants.GRID_HIGH_POSITION_TELESCOPE;
            default:
                return Constants.START_POSITION_TELESCOPE;
        }
    }
}