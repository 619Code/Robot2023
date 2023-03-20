package frc.robot.commands.manuals;

import frc.robot.Constants;
import frc.robot.helpers.Crashboard;
import frc.robot.subsystems.arm.Wrist;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class WristManualCommand extends CommandBase {
    private Wrist wrist;
    private CommandXboxController controller;

    double wristSpeed;

    public WristManualCommand(Wrist wrist, CommandXboxController controller) {
        this.wrist = wrist;
        this.controller = controller;

        addRequirements(wrist);
    }

    @Override
    public void execute() {
        wristSpeed = controller.getLeftY();
        Crashboard.toDashboard("Wrist Speed", wristSpeed, Constants.ARM_TAB);
        if(Math.abs(wristSpeed) > Constants.JOYSTICK_DEADZONE) {
            wrist.move(wristSpeed * Constants.WRIST_SPEED);
        } else {
            wrist.stop();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}