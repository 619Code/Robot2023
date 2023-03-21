package frc.robot.commands.arm.hinge;

import frc.robot.Constants;
import frc.robot.helpers.ArmPositionHelper;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.subsystems.arm.Hinge;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class HoldHingeCommand extends CommandBase {
    private Hinge hinge;
    private ArmPosition currentPosition;
    private double hingeGoal;

    public HoldHingeCommand(Hinge hinge) {
        this.hinge = hinge;

        addRequirements(hinge);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        currentPosition = ArmPositionHelper.currentPosition;
        hingeGoal = ArmPositionHelper.fetchHingeValue(currentPosition) + ArmPositionHelper.hingeAdjustment;
        System.out.println(currentPosition.toString());
        Crashboard.toDashboard("Hinge Goal 1", ArmPositionHelper.fetchHingeValue(currentPosition), Constants.ARM_TAB);
        Crashboard.toDashboard("Hinge Goal 2", ArmPositionHelper.hingeAdjustment, Constants.ARM_TAB);
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
