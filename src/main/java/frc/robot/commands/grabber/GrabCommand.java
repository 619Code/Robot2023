package frc.robot.commands.grabber;

import frc.robot.Constants;
import frc.robot.subsystems.Grabber;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class GrabCommand extends CommandBase {
    private Grabber grabber;

    private double limit;
    private double maxSpeed;

    public GrabCommand(Grabber grabber, boolean isCube) {
        this(grabber, isCube, Constants.MAX_GRABBER_SPEED);
    }

    public GrabCommand(Grabber grabber, boolean isCube, double maxSpeed) {
        this.grabber = grabber;
        this.maxSpeed = Math.min(maxSpeed, Constants.MAX_GRABBER_SPEED);

        addRequirements(grabber);

        if(isCube) {
            limit = Constants.CUBE_POSITION;
        } else {
            limit = Constants.CONE_POSITION;
        }
    }

    @Override
    public void execute() {
        if(limit > grabber.getPosition()) {
            if(Math.abs(limit - grabber.getPosition()) < 5) {
                grabber.spinMotor(1,maxSpeed/2.0);
            } else {
                grabber.spinMotor(1,maxSpeed);
            }
        } else {
            grabber.stop();
            grabber.grabbing = true;
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
