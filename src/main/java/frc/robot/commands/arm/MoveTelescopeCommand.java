package frc.robot.commands.arm;

import frc.robot.Constants;
import frc.robot.helpers.ArmPositionHelper;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.Position;
import frc.robot.subsystems.arm.Hinge;
import frc.robot.subsystems.arm.Telescope;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class MoveTelescopeCommand extends CommandBase {
    private Hinge hinge;
    private Telescope telescope;
    private Position state;
    private boolean retracting;

    private double telescopeGoal;
    private boolean finished;

    private double hingeGoal;

    public MoveTelescopeCommand(Hinge hinge, Telescope telescope, Position state, boolean retracting) {
        this.hinge = hinge;
        this.telescope = telescope;
        this.state = state;
        this.retracting = retracting;

        telescopeGoal = ArmPositionHelper.fetchTelescopeValue(state);
        hingeGoal = ArmPositionHelper.fetchHingeValue(state);

        addRequirements(hinge, telescope);
    }

    @Override
    public void initialize() {
        finished = false;
        ArmPositionHelper.currentPosition = state;
    }

    @Override
    public void execute() {
        if(retracting) {
            finished = telescope.retractFull();
            Crashboard.toDashboard("Retracted", finished);
            Crashboard.toDashboard("At Telescope Goal", false);
        } else {
            finished = telescope.moveToPosition(telescopeGoal);
            hinge.moveToPosition(hingeGoal);
            Crashboard.toDashboard("Retracted", true);
            Crashboard.toDashboard("At Telescope Goal", finished);
        }
    }

    @Override
    public void end(boolean interrupted) {
        telescope.stop();
    }

    @Override
    public boolean isFinished() {
        return !telescope.zeroed || finished;
    }
}
