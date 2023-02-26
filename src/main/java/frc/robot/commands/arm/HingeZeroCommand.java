package frc.robot.commands.arm;

import frc.robot.Constants;
import frc.robot.subsystems.arm.Hinge;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class HingeZeroCommand extends CommandBase {
    private Hinge hinge;

    public HingeZeroCommand(Hinge hinge) {
        this.hinge = hinge;

        addRequirements(hinge);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(hinge.lowSwitchIsPressed()) {
            hinge.stop();
            hinge.zero();
            hinge.zeroed = true;
        } else {
            hinge.move(-Constants.HINGE_ZERO_SPEED);
        }
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