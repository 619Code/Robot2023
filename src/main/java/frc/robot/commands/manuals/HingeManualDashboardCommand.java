package frc.robot.commands.manuals;

import frc.robot.Constants;
import frc.robot.helpers.Crashboard;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.arm.Hinge;
import frc.robot.subsystems.arm.Telescope;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class HingeManualDashboardCommand extends CommandBase {
    private Hinge hinge;

    double hingeSpeed;
    double holdPosition;

    GenericEntry targetPositionEntry;

    public HingeManualDashboardCommand(Hinge hinge) {
        this.hinge = hinge;

        targetPositionEntry = Crashboard.toDashboard("Manual Arm Target", 0, Constants.ARM_TAB);

        addRequirements(hinge);
    }

    @Override
    public void initialize() {
        if (targetPositionEntry != null) {
            holdPosition = targetPositionEntry.getDouble(hinge.getPosition());
        } else {
            holdPosition = hinge.getPosition();
        }
    }

    @Override
    public void execute() {
        holdPosition = Math.max(0, targetPositionEntry.getDouble(0));
        hinge.moveToPosition(this.holdPosition);
    }
}
