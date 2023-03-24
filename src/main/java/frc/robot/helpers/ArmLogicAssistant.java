package frc.robot.helpers;

import frc.robot.helpers.enums.ArmPosition;
import frc.robot.helpers.enums.ArmPositionSide;

public class ArmLogicAssistant {
    public static ArmPosition startPosition = ArmPosition.START;
    public static ArmPosition endPosition = ArmPosition.START;

    public static boolean movingToBack;

    public static boolean atHingePosition;
    public static boolean atTelescopePosition;
    public static boolean atWristPosition;

    public static void updatePositions(ArmPosition endPositionNew) {
        startPosition = endPosition;
        endPosition = endPositionNew;
        
        movingToBack = ArmPositionHelper.fetchSide(startPosition) == ArmPositionSide.FRONT && 
            ArmPositionHelper.fetchSide(endPosition) == ArmPositionSide.BACK;

        atHingePosition = false;
        atTelescopePosition = false;
        atWristPosition = false;
    }

    public static boolean atHingePosition() {
        return atHingePosition;
    }

    public static boolean atTelescopePosition() {
        return atTelescopePosition;
    }

    public static boolean atBothPositions() {
        return atHingePosition && atTelescopePosition;
    }

    public static boolean movingToBack() {
        return movingToBack;
    }

    public static boolean goalIsCurrent(ArmPosition goal) {
        return goal == ArmPositionHelper.currentPosition;
    }
}
