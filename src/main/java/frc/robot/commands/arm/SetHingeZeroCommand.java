package frc.robot.commands.arm;

import frc.robot.Constants;
import frc.robot.subsystems.arm.Hinge;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

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
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}