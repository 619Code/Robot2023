package frc.robot.commands.manuals;

import frc.robot.Constants;
import frc.robot.helpers.Crashboard;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.arm.Hinge;
import frc.robot.subsystems.arm.Telescope;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class HingeManualCommand extends CommandBase {
    private Hinge hinge;
    private CommandXboxController controller;

    double hingeSpeed;
    double holdPosition;

    public HingeManualCommand(Hinge hinge, CommandXboxController controller) {
        this.hinge = hinge;
        this.controller = controller;

        addRequirements(hinge);
    }

    @Override
    public void initialize() {
        holdPosition = hinge.getPosition();
    }

    @Override
    public void execute() {
        hingeSpeed = controller.getLeftY();
        if(Math.abs(hingeSpeed) > Constants.JOYSTICK_DEADZONE) {
            hinge.move(hingeSpeed * Constants.HINGE_SPEED);
            holdPosition = hinge.getPosition();
        } else {
            hinge.moveToPosition(hinge.getPosition());
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}