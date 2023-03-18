package frc.robot.commands.arm.telescope;

import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.ArmLogicAssistant;
import frc.robot.helpers.ArmPositionHelper;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.subsystems.arm.Hinge;
import frc.robot.subsystems.arm.Telescope;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class MoveTelescopeCommand extends CommandBase {
    private Telescope telescope;
    
    private double telescopeGoal;

    public MoveTelescopeCommand(Telescope telescope, ArmPosition telescopeGoalPosition) {
        this.telescope = telescope;

        telescopeGoal = ArmPositionHelper.fetchTelescopeValue(telescopeGoalPosition);

        addRequirements(telescope);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        ArmLogicAssistant.atTelescopePosition = telescope.moveToPosition(telescopeGoal);
    }

    @Override
    public void end(boolean interrupted) {
        telescope.stop();
    }

    @Override
    public boolean isFinished() {
        return ArmLogicAssistant.atTelescopePosition;
    }
}
