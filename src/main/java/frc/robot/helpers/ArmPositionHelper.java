package frc.robot.helpers;

import frc.robot.Constants;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.helpers.enums.ArmPositionSide;

public class ArmPositionHelper {
    public static ArmPosition currentPosition = ArmPosition.START;
    public static double hingeAdjustment;

    public static double fetchHingeValue(ArmPosition position) {
        switch(position) {
            case START:
                return Constants.START_POSITION_HINGE;
            case PICKUP_LOW:
                return Constants.PICKUP_LOW_POSITION_HINGE;
            case PICKUP_HIGH:
                return Constants.PICKUP_HIGH_POSITION_HINGE;
            case CHUTE:
                return Constants.CHUTE_POSITION_HINGE;
            case CONE_MID:
                return Constants.CONE_MID_POSITION_HINGE;
            case CUBE_MID:
                return Constants.CUBE_MID_POSITION_HINGE;
            case CUBE_HIGH:
                return Constants.CUBE_HIGH_POSITION_HINGE;
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
            case CHUTE:
                return Constants.CHUTE_POSITION_TELESCOPE;
            case CONE_MID:
                return Constants.CONE_MID_POSITION_TELESCOPE;
            case CUBE_MID:
                return Constants.CUBE_MID_POSITION_TELESCOPE;
            case CUBE_HIGH:
                return Constants.CUBE_HIGH_POSITION_TELESCOPE;
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
            case CHUTE:
                return Constants.CHUTE_POSITION_WRIST;
            case CONE_MID:
                return Constants.CONE_MID_POSITION_WRIST;
            case CUBE_MID:
                return Constants.CUBE_MID_POSITION_WRIST;
            case CUBE_HIGH:
                return Constants.CUBE_HIGH_POSITION_WRIST;
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
            case CONE_MID:
                return Constants.CONE_MID_POSITION_SIDE;
            case CUBE_MID:
                return Constants.CUBE_MID_POSITION_SIDE;
            case CUBE_HIGH:
                return Constants.CUBE_HIGH_POSITION_SIDE;
            case CHUTE:
                return Constants.CHUTE_POSITION_SIDE;
            default:
                return Constants.START_POSITION_SIDE;
        }
    }
}