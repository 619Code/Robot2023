package frc.robot.commands.grabber;

import frc.robot.Constants;
import frc.robot.subsystems.Grabber;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class ReleaseCommand extends CommandBase {
    private Grabber grabber;

    private double maxSpeed;

    public ReleaseCommand(Grabber grabber) {
        this(grabber, Constants.MAX_GRABBER_SPEED);
    }

    public ReleaseCommand(Grabber grabber, double speed) {
        this.grabber = grabber;
        this.maxSpeed = Math.min(speed,Constants.MAX_GRABBER_SPEED);

        addRequirements(grabber);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        //grabber.grabbing = !grabber.moveToPosition(0.0);

        if(grabber.getPosition() > 0) {
            if(Math.abs(0.0 - grabber.getPosition()) < 5) {
                grabber.spinMotor(-1,maxSpeed/2.0);
            } else {
                grabber.spinMotor(-1,maxSpeed);
            }
        } else {
            grabber.stop();
            grabber.grabbing = false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        grabber.stop();
    }

    @Override
    public boolean isFinished() {
        return (!grabber.grabbing);
    }
}
