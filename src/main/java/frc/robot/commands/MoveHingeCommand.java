package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.helpers.ArmPositionHelper;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.Position;
import frc.robot.subsystems.arm.Hinge;
import frc.robot.subsystems.arm.Telescope;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class MoveHingeCommand extends CommandBase {
    private Hinge hinge;
    private Position state;
    
    private double hingeGoal;
    private boolean atHingeGoal;

    public MoveHingeCommand(Hinge hinge, Position state) {
        this.hinge = hinge;
        this.state = state;

        hingeGoal = ArmPositionHelper.fetchHingeValue(state);

        addRequirements(hinge);
    }

    @Override
    public void initialize() {
        atHingeGoal = false;
        ArmPositionHelper.currentPosition = state;
    }

    @Override
    public void execute() {
        if(hinge.moveToPosition(hingeGoal)) {
            atHingeGoal = true;
        }

        Crashboard.toDashboard("At Hinge Goal", atHingeGoal);
    }

    @Override
    public void end(boolean interrupted) {
        hinge.stop();
    }

    @Override
    public boolean isFinished() {
        return atHingeGoal;
    }
}
