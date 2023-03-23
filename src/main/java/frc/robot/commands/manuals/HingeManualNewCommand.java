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

public class HingeManualNewCommand extends CommandBase {
    private Hinge hinge;

    double hingeSpeed;
    double holdPosition;

    GenericEntry targetPosition;

    public HingeManualNewCommand(Hinge hinge) {
        this.hinge = hinge;

        targetPosition = Crashboard.toDashboard("Manual Arm Target", 0, Constants.ARM_TAB);

        addRequirements(hinge);
    }

    @Override
    public void initialize() {

        //targetPosition = Crashboard.toDashboard("Manual Arm Target", 0, Constants.ARM_TAB);

        if (targetPosition != null)
        {
            holdPosition = targetPosition.getDouble(hinge.getPosition());
        }
        else
        {
            holdPosition = hinge.getPosition();
        }
    }

    @Override
    public void execute() {

        this.holdPosition = Math.max(0, targetPosition.getDouble(0));
        System.out.println("New Position: " + this.holdPosition);
        //this.hinge.moveToPositionSparkPID(holdPosition);
        this.hinge.moveToPositionSimple(this.holdPosition);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}