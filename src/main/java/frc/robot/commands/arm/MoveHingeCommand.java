package frc.robot.commands.arm;

import frc.robot.Constants;
import frc.robot.helpers.ArmPositionHelper;
import frc.robot.helpers.Crashboard;
import frc.robot.subsystems.arm.Hinge;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveHingeCommand extends CommandBase {
    private Hinge hinge;
    
    private double hingeGoal;

    public MoveHingeCommand(Hinge hinge) {
        this.hinge = hinge;

        hingeGoal = ArmPositionHelper.fetchHingeValue(ArmPositionHelper.currentPosition);

        addRequirements(hinge);
    }

    @Override
    public void execute() {
        if(hinge.moveToPosition(hingeGoal)) {
            ArmPositionHelper.atHingePosition = true;
        }

        Crashboard.toDashboard("At Hinge Goal", ArmPositionHelper.atHingePosition, Constants.ARM_TAB);
    }

    @Override
    public void end(boolean interrupted) {
        hinge.stop();
    }

    @Override
    public boolean isFinished() {        
        if(ArmPositionHelper.atHingePosition) {
            return true;
        }

        return false;
    }
}
