package frc.robot.commands.grabber;

import frc.robot.Constants;
import frc.robot.subsystems.Grabber;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class HoldInCommand extends CommandBase {
    private Grabber grabber;

    public HoldInCommand(Grabber grabber) {
        this.grabber = grabber;

        addRequirements(grabber);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(grabber.coneSensed() || grabber.cubeSensed()) {
            grabber.spin(Constants.HOLD_SPEED);
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