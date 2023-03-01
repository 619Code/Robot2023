package frc.robot.commands.arm;

import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.ArmPositionHelper;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.subsystems.arm.Hinge;
import frc.robot.subsystems.arm.Telescope;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class MoveHingeCommand extends CommandBase {
    private Hinge hinge;
    
    private double hingeGoal;

    public MoveHingeCommand(Hinge hinge) {
        this.hinge = hinge;

        hingeGoal = ArmPositionHelper.fetchHingeValue(ArmPositionHelper.currentPosition);

        addRequirements(hinge);
    }

    @Override
    public void initialize() {
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
        if(!hinge.movable()) {
            return true;
        }
        
        if(ArmPositionHelper.atHingePosition) {
            return true;
        }

        return false;
    }
}
