package frc.robot.commands.grabber;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Grabber;

public class GrabDefaultCommand extends CommandBase {
    private Grabber grabber;

    private Timer timer;
    private boolean waiting;

    public GrabDefaultCommand(Grabber grabber) {
        this.grabber = grabber;
        timer = new Timer();

        addRequirements(grabber);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        waiting = true;
    }

    @Override
    public void execute() {
        if(!waiting) {
            if(grabber.coneSensed() || grabber.cubeSensed()) {
                grabber.spin(Constants.GRAB_SPEED_DEFAULT);
            } else {
                grabber.stop();
            }
        } else {
            grabber.stop();
        }

        if(waiting && timer.hasElapsed(Constants.DEFAULT_WAIT_TIME)) {
            waiting = false;
            timer.reset();
            timer.start();
        } else if(!waiting && timer.hasElapsed(Constants.DEFAULT_PULSE_TIME)) {
            waiting = true;
            timer.reset();
            timer.start();
        }

        //alternative constant rate default
        /*if(!grabber.coneSensed() && grabber.cubeSensed()) {
            grabber.spin(Constants.GRAB_SPEED_DEFAULT);
        }*/
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
