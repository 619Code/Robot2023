package frc.robot.commands.arm.wrist;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.helpers.ArmLogicAssistant;
import frc.robot.helpers.ArmPositionHelper;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.subsystems.arm.Wrist;

public class MoveWristCommand extends CommandBase {
    private Wrist wrist;
    
    private double wristGoal;
    
    public MoveWristCommand(Wrist wrist, ArmPosition wristGoalPosition) {
        this.wrist = wrist;

        wristGoal = ArmPositionHelper.fetchWristValue(wristGoalPosition);

        addRequirements(wrist);
    }

    @Override
    public void initialize() {
        ArmLogicAssistant.atWristPosition = false;
    }

    @Override
    public void execute() {
        ArmLogicAssistant.atWristPosition = wrist.moveToPositionSimple(wristGoal);
    }

    @Override
    public void end(boolean interrupted) {
        wrist.stop();
    }

    @Override
    public boolean isFinished() {
        return ArmLogicAssistant.atWristPosition;
    }
}
