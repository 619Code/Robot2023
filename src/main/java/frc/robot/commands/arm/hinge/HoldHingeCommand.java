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

public class HoldHingeCommand extends CommandBase {
    private Hinge hinge;
    private ArmPosition goalPosition;
    private double hingeGoal;

    private boolean holdAtCurrent;

    public HoldHingeCommand(Hinge hinge, boolean holdAtCurrent) {
        this.hinge = hinge;
        this.holdAtCurrent = holdAtCurrent;

        addRequirements(hinge);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(holdAtCurrent){
            goalPosition = ArmPositionHelper.currentPosition;
        } else {
            goalPosition = ArmLogicAssistant.startPosition;
        }

        hingeGoal = ArmPositionHelper.fetchHingeValue(goalPosition) + ArmPositionHelper.hingeAdjustment;
        Crashboard.toDashboard("Hinge Adjustment", ArmPositionHelper.hingeAdjustment, Constants.ARM_TAB);
        hinge.moveToPosition(hingeGoal);
    }

    @Override
    public void end(boolean interrupted) {
        hinge.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
