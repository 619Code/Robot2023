package frc.robot.commands.arm;

import frc.robot.subsystems.arm.Hinge;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetHingeZeroCommand extends CommandBase {
    private Hinge hinge;

    public SetHingeZeroCommand(Hinge hinge) {
        this.hinge = hinge;

        addRequirements(hinge);
    }

    public void initialize() {
        hinge.hingeEncoder.setPosition(0.0);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}