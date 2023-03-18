package frc.robot.commands.arm.hinge;

import frc.robot.Constants;
import frc.robot.helpers.ArmPositionHelper;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.subsystems.arm.Hinge;
import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class HingeAdjustCommand extends CommandBase {
    private Hinge hinge;

    private CommandXboxController controller;

    private HoldHingeCommand myHoldCommand;

    public HingeAdjustCommand(Hinge hinge, CommandXboxController controller) {
        this.hinge = hinge;
        this.controller = controller;

        myHoldCommand = new HoldHingeCommand(hinge);

        addRequirements(hinge);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        ArmPositionHelper.hingeAdjustment += controller.getLeftY() * 0.1;
        myHoldCommand.execute();
    }

    @Override
    public void end(boolean interrupted) {
        hinge.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
