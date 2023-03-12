package frc.robot.commands.arm;

import frc.robot.Constants;
import frc.robot.helpers.ArmPositionHelper;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.subsystems.arm.Hinge;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class HoldArmCommand extends CommandBase {
    private Hinge hinge;
    private ArmPosition currentPosition;
    private double hingeGoal;

    public HoldArmCommand(Hinge hinge) {
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
