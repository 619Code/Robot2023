package frc.robot.commands.arm.telescope;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.helpers.ArmLogicAssistant;
import frc.robot.helpers.Crashboard;
import frc.robot.subsystems.arm.Telescope;

public class RetractTelescopeCommand extends CommandBase {
    private Telescope telescope;
    private boolean retracted;

    public RetractTelescopeCommand(Telescope telescope) {
        this.telescope = telescope;

        retracted = false;

        addRequirements(telescope);
    }

    @Override
    public void initialize() {
        Crashboard.toDashboard("Retracting", true, Constants.ARM_TAB);
    }

    @Override
    public void execute() {
        retracted = telescope.retractFull();
    }

    @Override
    public void end(boolean interrupted) {
        telescope.stop();
        Crashboard.toDashboard("Retracting", false, Constants.ARM_TAB);
    }

    @Override
    public boolean isFinished() {
        return retracted;
    }
}
