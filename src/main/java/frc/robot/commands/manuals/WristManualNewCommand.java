package frc.robot.commands.manuals;

import frc.robot.Constants;
import frc.robot.helpers.Crashboard;
import frc.robot.subsystems.arm.Wrist;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class WristManualNewCommand extends CommandBase {
    private Wrist wrist;

    double targetPosition = 0;
    GenericEntry targetPositionEntry;

    public WristManualNewCommand(Wrist wrist) {
        this.wrist = wrist;
        targetPositionEntry = Crashboard.toDashboard("Manual Wrist Target", 0, Constants.ARM_TAB);
        addRequirements(wrist);
    }

    @Override
    public void initialize() {

        if (targetPositionEntry != null)
        {
            targetPosition = targetPositionEntry.getDouble(this.wrist.getRelativePosition());
        }
        else
        {
            targetPosition = wrist.getRelativePosition();
        }
    }

    @Override
    public void execute() {
        targetPosition = targetPositionEntry.getDouble(this.wrist.getRelativePosition());
        Crashboard.toDashboard("Wrist Angle", wrist.getAngle(), Constants.ARM_TAB);
        System.out.println("Wrist Angle: " + wrist.getAngle());
        wrist.moveToPositionSimple(targetPosition);
        //wrist.move(wrist.calculateFF());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}