package frc.robot.commands.arm;

import frc.robot.Constants;
import frc.robot.helpers.ArmPositionHelper;
import frc.robot.helpers.Crashboard;
import frc.robot.subsystems.arm.Telescope;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveTelescopeCommand extends CommandBase {
    private Telescope telescope;
    private double telescopeGoal;
    private boolean retractOnly;

    public MoveTelescopeCommand(Telescope telescope, boolean retractOnly) {
        this.telescope = telescope;
        this.retractOnly = retractOnly;

        telescopeGoal = ArmPositionHelper.fetchTelescopeValue(ArmPositionHelper.currentPosition);

        addRequirements(telescope);
    }


    @Override
    public void execute() {
        if(retractOnly) {
            ArmPositionHelper.retracted = telescope.retractFull();
        } else {
            ArmPositionHelper.atTelescopePosition = telescope.moveToPosition(telescopeGoal);
        }

        Crashboard.toDashboard("Retracted", ArmPositionHelper.retracted, Constants.ARM_TAB);
    }

    @Override
    public void end(boolean interrupted) {
        telescope.stop();
    }

    @Override
    public boolean isFinished() {
        if(ArmPositionHelper.atTelescopePosition) {
            return true;
        }

        return false;
    }
}
