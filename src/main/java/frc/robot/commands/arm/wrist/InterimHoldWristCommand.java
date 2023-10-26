package frc.robot.commands.arm.wrist;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.arm.Wrist;

public class InterimHoldWristCommand extends CommandBase {
    private Wrist wrist;
    private double wristGoal;

    public InterimHoldWristCommand(Wrist wrist, double position) {
        this.wrist = wrist;
        wristGoal = position;

        addRequirements(wrist);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
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