package frc.robot.helpers;

import frc.robot.helpers.enums.ArmPosition;
import frc.robot.helpers.enums.ArmPositionSide;
import frc.robot.helpers.enums.HingeCommandOption;
import frc.robot.helpers.enums.TelescopeCommandOption;

public class ArmLogicAssistant {
    private static ArmPosition startPosition = ArmPosition.START;
    private static ArmPosition endPosition = ArmPosition.START;

    public static boolean movingToBack;

    public static boolean atHingePosition;
    public static boolean atTelescopePosition;

    public static void updatePositions(ArmPosition endPositionNew) {
        startPosition = endPosition;
        endPosition = endPositionNew;
        
        movingToBack = ArmPositionHelper.fetchSide(startPosition) == ArmPositionSide.FRONT && 
        ArmPositionHelper.fetchSide(endPosition) == ArmPositionSide.BACK;

        if(startPosition == endPosition) {
            atHingePosition = true;
        } else {
            atHingePosition = false;
        }
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
}
