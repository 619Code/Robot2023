package frc.robot.unused.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.States;

public class ToggleDeployIntakeCommand extends CommandBase {
    
    public ToggleDeployIntakeCommand(){}

    @Override
    public void execute() {        
    }

    @Override
    public void initialize() {
        States.intakeDeployed = !States.intakeDeployed;
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
