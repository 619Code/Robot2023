package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.subsystems.IntakeSub;

public class HoldIntakeCommand extends CommandBase {

    //default command for sub
    IntakeSub intake;

    public HoldIntakeCommand(IntakeSub intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        if (States.intakeDeployed) {
            intake.moveToPosition(Constants.INTAKE_DEPLOYED_POSITION);
        } else {
            intake.moveToPosition(Constants.INTAKE_RETRACTED_POSITION);
        }
    }

    
    
}
