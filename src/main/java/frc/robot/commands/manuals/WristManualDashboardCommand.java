package frc.robot.commands.manuals;

import frc.robot.Constants;
import frc.robot.helpers.Crashboard;
import frc.robot.subsystems.arm.Wrist;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class WristManualDashboardCommand extends CommandBase {
    private Wrist wrist;

    double targetPosition = 0;
    GenericEntry targetPositionEntry;

    public WristManualDashboardCommand(Wrist wrist) {
        this.wrist = wrist;
        
        addRequirements(wrist);
    }

    @Override
    public void initialize() {
        targetPositionEntry = Crashboard.toDashboard("Manual Wrist Target", 0, Constants.WRIST_TAB);

        if (targetPositionEntry != null) {
            targetPosition = targetPositionEntry.getDouble(this.wrist.getRelativePosition());
        } else {
            targetPosition = wrist.getRelativePosition();
        }
    }

    @Override
    public void execute() {
        targetPosition = targetPositionEntry.getDouble(this.wrist.getRelativePosition());
        wrist.moveToPositionSimple(targetPosition);
        wrist.CalculateStuff(targetPosition);

        //Crashboard.toDashboard("Wrist Angle", wrist.getAngle(), Constants.ARM_TAB);
        //System.out.println("Wrist Angle: " + wrist.getAngle());
        //wrist.move(wrist.calculateFF());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}