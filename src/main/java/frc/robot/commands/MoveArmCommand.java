package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.helpers.ArmPositionHelper;
import frc.robot.helpers.Position;
import frc.robot.subsystems.arm.Telescope;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class MoveArmCommand extends CommandBase {
    private Telescope telescope;
    private Position state;
    private double telescopeGoal;
    private boolean atTelescopeGoal;

    public MoveArmCommand(Telescope telescope, Position state) {
        this.telescope = telescope;
        this.state = state;

        telescopeGoal = ArmPositionHelper.fetch(state)[1];
        atTelescopeGoal = false;

        addRequirements(telescope);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        atTelescopeGoal = telescope.moveToPosition(telescopeGoal);
    }

    @Override
    public void end(boolean interrupted) {
        telescope.stop();
    }

    @Override
    public boolean isFinished() {
        return !telescope.zeroed;
    }
}
