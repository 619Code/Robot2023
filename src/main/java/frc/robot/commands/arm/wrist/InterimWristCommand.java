package frc.robot.commands.arm.wrist;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.arm.Wrist;

public class InterimWristCommand extends CommandBase {
    private Wrist wrist;
    
    private double wristGoal;
    boolean atPosition;
    
    public InterimWristCommand(Wrist wrist, double goal) {
        this.wrist = wrist;
        wristGoal = goal;
        addRequirements(wrist);
    }

    @Override
    public void initialize() {
        atPosition = false;
    }

    @Override
    public void execute() {
        atPosition = wrist.moveToPositionSimple(wristGoal);
    }

    @Override
    public void end(boolean interrupted) {
        wrist.stop();
    }

    @Override
    public boolean isFinished() {
        return atPosition;
    }
}
