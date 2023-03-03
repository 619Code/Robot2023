package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.enums.IntakeArm;
import frc.robot.subsystems.IntakeSub;

public class IntakeDefaultCommand extends CommandBase {

    private IntakeSub intakeSub;
    private double tolerance = .5;
    private double speed = .1;

    public IntakeDefaultCommand(IntakeSub intakeSub) {
        this.intakeSub = intakeSub;
        this.addRequirements(intakeSub);
    }

    @Override
    public void execute() {
        //double targetPosition = States.intakeDeployed ? Constants.INTAKE_DEPLOYED_POSITION : Constants.INTAKE_RETRACTED_POSITION;
        double leftTargetPosition = States.intakeDeployed ? Constants.INTAKE_DEPLOYED_POSITION - 3 : Constants.INTAKE_RETRACTED_POSITION;
        double rightTargetPosition = States.intakeDeployed ? Constants.INTAKE_DEPLOYED_POSITION : Constants.INTAKE_RETRACTED_POSITION;
      
        this.MoveIntake(leftTargetPosition, IntakeArm.LeftArm);
        this.MoveIntake(rightTargetPosition, IntakeArm.RightArm);
    }

    private void MoveIntake(double targetPosition, IntakeArm arm)
    {
        double diff = targetPosition - intakeSub.getPosition(arm);
        if (Math.abs(diff) > tolerance )
        {
            if (diff > 0)
            {
                intakeSub.setSpeed(speed, arm);
            }
            else
            {
                intakeSub.setSpeed(-speed, arm);
            }
        }
        else
        {
            intakeSub.setSpeed(0, arm);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        this.intakeSub.setSpeed(0, IntakeArm.LeftArm);
        this.intakeSub.setSpeed(0, IntakeArm.RightArm);
    }
}
