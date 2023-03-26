package frc.robot.commands.arm.hinge;

import frc.robot.Constants;
import frc.robot.helpers.ArmPositionHelper;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.subsystems.arm.Hinge;
import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class HingeAdjustCommand extends CommandBase {
    private Hinge hinge;

    private CommandXboxController controller;

    private HoldHingeDefault myHoldCommand;

    public HingeAdjustCommand(Hinge hinge, CommandXboxController controller) {
        this.hinge = hinge;
        this.controller = controller;

        myHoldCommand = new HoldHingeDefault(hinge,true);

        addRequirements(hinge);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double adjustment = controller.getLeftY() * 0.2;
        ArmPositionHelper.hingeAdjustment += adjustment;
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
