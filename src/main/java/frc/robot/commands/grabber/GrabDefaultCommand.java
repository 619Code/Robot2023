package frc.robot.commands.grabber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Grabber;

public class GrabDefaultCommand extends CommandBase {
    private Grabber grabber;

    boolean ending;

    public GrabDefaultCommand(Grabber grabber) {
        this.grabber = grabber;

        addRequirements(grabber);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(!grabber.coneSensed() && grabber.cubeSensed()) {
            grabber.spin(Constants.GRAB_SPEED_DEFAULT);
        } else {
            grabber.stop();
        }
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
