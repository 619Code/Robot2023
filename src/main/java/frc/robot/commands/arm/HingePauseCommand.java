package frc.robot.commands.arm;

import frc.robot.subsystems.arm.Hinge;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class HingePauseCommand extends CommandBase {
    private Hinge hinge;

    public HingePauseCommand(Hinge hinge) {
        this.hinge = hinge;

        addRequirements(hinge);
    }

    @Override
    public void execute() {
        hinge.move(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
