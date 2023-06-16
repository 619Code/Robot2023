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

    double wristAdjustment;
    double adjustment;

    public WristManualCommand(Wrist wrist, CommandXboxController controller) {
        this.wrist = wrist;
        this.controller = controller;

        wristAdjustment = 0;
        adjustment = 0;

        addRequirements(wrist);
    }

    @Override
    public void initialize() {
        adjustment = 0;
        wristAdjustment = wrist.getCurrentPositionInDegrees();
    }

    @Override
    public void execute() {
        adjustment = controller.getLeftY() * 0.5;
        wristAdjustment += adjustment;
        wristAdjustment = Math.max(0,wristAdjustment);

        //Crashboard.toDashboard("Wrist Adjustment", wristAdjustment, Constants.ARM_TAB);
        wrist.moveToPositionSimple(wristAdjustment);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}