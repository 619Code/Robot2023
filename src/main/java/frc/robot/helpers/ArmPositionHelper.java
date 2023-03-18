package frc.robot.helpers;

import frc.robot.Constants;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.helpers.enums.ArmPositionSide;

public class ArmPositionHelper {
    public static ArmPosition currentPosition = ArmPosition.START;
    public static double hingeAdjustment = 0;

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
            case PARALLEL_POSITION:
                return Constants.PARALLEL_POSITION_HINGE;
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
            case PARALLEL_POSITION:
                return Constants.PARALLEL_POSITION_TELESCOPE;
            default:
                return Constants.START_POSITION_TELESCOPE;
        }
    }

    public static double fetchWristValue(ArmPosition position) {
        switch(position) {
            case START:
                return Constants.START_POSITION_WRIST;
            case PICKUP_LOW:
                return Constants.PICKUP_LOW_POSITION_WRIST;
            case PICKUP_HIGH:
                return Constants.PICKUP_HIGH_POSITION_WRIST;
            case GRID_MID:
                return Constants.GRID_MID_POSITION_WRIST;
            case GRID_HIGH:
                return Constants.GRID_HIGH_POSITION_WRIST;
            case PARALLEL_POSITION:
                return Constants.PARALLEL_POSITION_WRIST;
            default:
                return Constants.START_POSITION_WRIST;
        }
    }

    public static ArmPositionSide fetchSide(ArmPosition position) {
        switch(position) {
            case START:
                return Constants.START_POSITION_SIDE;
            case PICKUP_LOW:
                return Constants.PICKUP_LOW_POSITION_SIDE;
            case PICKUP_HIGH:
                return Constants.PICKUP_HIGH_POSITION_SIDE;
            case GRID_MID:
                return Constants.GRID_MID_POSITION_SIDE;
            case GRID_HIGH:
                return Constants.GRID_HIGH_POSITION_SIDE;
            case PARALLEL_POSITION:
                return Constants.PARALLEL_POSITION_SIDE;
            default:
                return Constants.START_POSITION_SIDE;
        }
    }
}