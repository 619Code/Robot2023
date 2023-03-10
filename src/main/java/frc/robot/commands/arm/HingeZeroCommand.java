package frc.robot.commands.arm;

import frc.robot.Constants;
import frc.robot.subsystems.arm.Hinge;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class HingeZeroCommand extends CommandBase {
    private Hinge hinge;

    public HingeZeroCommand(Hinge hinge) {
        this.hinge = hinge;

        addRequirements(hinge);
    }

    @Override
    public void execute() {
        hinge.hingeLeaderMotor.set(-Constants.HINGE_ZERO_SPEED);
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