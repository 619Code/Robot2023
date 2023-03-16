package frc.robot.commands.grabber;

import frc.robot.Constants;
import frc.robot.subsystems.Grabber;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class ReleaseCommand extends CommandBase {
    private Grabber grabber;

    public ReleaseCommand(Grabber grabber) {
        this.grabber = grabber;

        addRequirements(grabber);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        grabber.spin(Constants.RELEASE_SPEED);
    }

    @Override
    public void end(boolean interrupted) {
        grabber.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}