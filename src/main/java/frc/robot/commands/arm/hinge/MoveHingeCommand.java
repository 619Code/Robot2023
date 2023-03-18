package frc.robot.commands.arm.hinge;

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

public class MoveHingeCommand extends CommandBase {
    private Hinge hinge;
    
    ArmPosition hingeGoalPosition;
    private double hingeGoal;
    
    public MoveHingeCommand(Hinge hinge, ArmPosition hingeGoalPosition) {
        this.hinge = hinge;
        this.hingeGoalPosition = hingeGoalPosition;

        hingeGoal = ArmPositionHelper.fetchHingeValue(hingeGoalPosition);

        addRequirements(hinge);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        ArmLogicAssistant.atHingePosition = hinge.moveToPosition(hingeGoal);
    }

    @Override
    public void end(boolean interrupted) {
        if(!interrupted) {
            ArmPositionHelper.currentPosition = hingeGoalPosition;
        }

        hinge.stop();
    }

    @Override
    public boolean isFinished() {
        return ArmLogicAssistant.atHingePosition;
    }
}
