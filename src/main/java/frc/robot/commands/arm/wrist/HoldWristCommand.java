package frc.robot.commands.arm.wrist;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.subsystems.arm.Wrist;
import frc.robot.helpers.ArmPositionHelper;

public class HoldWristCommand extends CommandBase {
    private Wrist wrist;
    private ArmPosition currentPosition;
    private double wristGoal;

    public HoldWristCommand(Wrist wrist) {
        this.wrist = wrist;

        addRequirements(wrist);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        currentPosition = ArmPositionHelper.currentPosition;
        wristGoal = ArmPositionHelper.fetchWristValue(currentPosition);
        wrist.moveToPositionSimple(wristGoal);
    }

    @Override
    public void end(boolean interrupted) {
        wrist.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}