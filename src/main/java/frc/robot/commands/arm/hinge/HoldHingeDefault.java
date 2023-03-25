package frc.robot.commands.arm.hinge;

import frc.robot.Constants;
import frc.robot.helpers.ArmLogicAssistant;
import frc.robot.helpers.ArmPositionHelper;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.subsystems.arm.Hinge;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class HoldHingeDefault extends CommandBase {
    private Hinge hinge;
    private ArmPosition goalPosition;
    private double hingeGoal;

    private boolean holdAtCurrent;

    public HoldHingeDefault(Hinge hinge, boolean holdAtCurrent) {
        this.hinge = hinge;
        this.holdAtCurrent = holdAtCurrent;

        addRequirements(hinge);
    }

    @Override
    public void initialize() {
        Crashboard.toDashboard("Holding Hinge", true, Constants.ARM_TAB);
    }

    @Override
    public void execute() {
        if(holdAtCurrent){
            goalPosition = ArmPositionHelper.currentPosition;
            Crashboard.toDashboard("Holding At Start", false, Constants.ARM_TAB);
        } else {
            goalPosition = ArmLogicAssistant.startPosition;
            Crashboard.toDashboard("Holding At Start", true, Constants.ARM_TAB);
        }
        
        hingeGoal = ArmPositionHelper.fetchHingeValue(goalPosition) + ArmPositionHelper.hingeAdjustment;
        hinge.moveToPosition(hingeGoal);
    }

    @Override
    public void end(boolean interrupted) {
        hinge.stop();
        Crashboard.toDashboard("Holding Hinge", false, Constants.ARM_TAB);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}