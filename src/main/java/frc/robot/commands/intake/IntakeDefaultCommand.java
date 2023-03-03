package frc.robot.commands.intake;

import javax.lang.model.util.ElementScanner14;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.enums.IntakeArm;
import frc.robot.subsystems.IntakeSub;

public class IntakeDefaultCommand extends CommandBase {

    private IntakeSub intakeSub;
    private double tolerance = .5;
    private double minSpeed = .1;
    private double maxSpeed = .3;
    private double leftIntakDeployedOffset = -3;
    private double rightIntakeDeployedOffset = 0;

    public IntakeDefaultCommand(IntakeSub intakeSub) {
        this.intakeSub = intakeSub;
        this.addRequirements(intakeSub);
    }

    @Override
    public void execute() {
        //double targetPosition = States.intakeDeployed ? Constants.INTAKE_DEPLOYED_POSITION : Constants.INTAKE_RETRACTED_POSITION;
        double leftTargetPosition = States.intakeDeployed ? Constants.INTAKE_DEPLOYED_POSITION + leftIntakDeployedOffset : Constants.INTAKE_RETRACTED_POSITION;
        double rightTargetPosition = States.intakeDeployed ? Constants.INTAKE_DEPLOYED_POSITION + rightIntakeDeployedOffset : Constants.INTAKE_RETRACTED_POSITION;
      
        this.MoveIntake(leftTargetPosition, IntakeArm.LeftArm);
        this.MoveIntake(rightTargetPosition, IntakeArm.RightArm);
    }

    // Always returns a positive speed
    private double calculateSpeed(double distance)
    {
        double distanceToTarget = Math.abs(distance);
        double speed = 0;
        if (distanceToTarget >= 15 && distanceToTarget > 40)
            speed = maxSpeed;
        else if (distanceToTarget > 5 && distanceToTarget < 15 )
            speed = maxSpeed/2;
        else
            speed = minSpeed;

        return Math.max(Math.abs(speed), minSpeed);       
    }

    private void MoveIntake(double targetPosition, IntakeArm arm)
    {
        double diff = targetPosition - intakeSub.getPosition(arm);
        double speed = calculateSpeed(diff);

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
