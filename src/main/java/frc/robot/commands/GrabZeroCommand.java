package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Grabber;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class GrabZeroCommand extends CommandBase {
    private Grabber grabber;

    public GrabZeroCommand(Grabber grabber) {
        this.grabber = grabber;

        addRequirements(grabber);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(grabber.switchIsPressed()) {
            grabber.stop();
            grabber.zeroAtPosition(Constants.GRABBER_ZERO_POSITION);
            grabber.zeroed = true;
        } else {
            grabber.spinMotor(1, 0.1);
        }
    }

    @Override
    public void end(boolean interrupted) {
        grabber.stop();
    }

    @Override
    public boolean isFinished() {
        return grabber.zeroed;
    }
}
