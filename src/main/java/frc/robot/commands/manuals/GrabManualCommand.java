package frc.robot.commands.manuals;

import frc.robot.Constants;
import frc.robot.subsystems.Grabber;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class GrabManualCommand extends CommandBase {
    private Grabber grabber;
    private CommandXboxController controller;

    double speed;

    public GrabManualCommand(Grabber grabber, CommandXboxController controller) {
        this.grabber = grabber;
        this.controller = controller;

        addRequirements(grabber);
    }

    @Override
    public void execute() {
        speed = controller.getRightY();
        if(Math.abs(speed) > 0.05) {
            grabber.spinMotor(speed, 0.1);
        } else {
            grabber.stop();
        }

        if(grabber.switchIsPressed()) {
            grabber.zeroAtPosition(Constants.ZERO_POSITION);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
