package frc.robot.commands.grabber;

import frc.robot.Constants;
import frc.robot.subsystems.Grabber;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class GrabCommand extends CommandBase {
    private Grabber grabber;

    boolean ending;
    Timer endTimer;
    double timeLimit;

    public GrabCommand(Grabber grabber) {
        this.grabber = grabber;

        endTimer = new Timer();

        addRequirements(grabber);
    }

    @Override
    public void initialize() {
        endTimer.reset();
        endTimer.stop();
        ending = false;
        this.grabber.startSensingCube();
    }

    @Override
    public void execute() {
        /*if(!ending) {
            if(grabber.cubeSensed()) {
                timeLimit = Constants.CUBE_TIMER;
                endTimer.reset();
                endTimer.start();
                ending = true;
            } else if(grabber.coneSensed()) {
                timeLimit = Constants.CONE_TIMER;
                endTimer.reset();
                endTimer.start();
                ending = true;
            }
        }*/

        if(!ending) {
            if(grabber.coneSensed()) {
                timeLimit = Constants.CONE_TIMER;
                endTimer.reset();
                endTimer.start();
                ending = true;
            }
        }

        grabber.spin(Constants.GRAB_SPEED);
    }

    @Override
    public void end(boolean interrupted) {
        grabber.stopSensingCube();
        grabber.stop();
    }

    @Override
    public boolean isFinished() {
        return ending && endTimer.hasElapsed(timeLimit);
    }
}