package frc.robot.unused.intake;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.enums.IntakeArm;

public class IntakeDefaultCommand extends CommandBase {

    private IntakeSub intakeSub;
    private double tolerance = Constants.INTAKE_TOLERANCE;
    private double minSpeed = Constants.INTAKE_MINSPEED;
    private double maxSpeed = Constants.INTAKE_MAXSPEED;

    public IntakeDefaultCommand(IntakeSub intakeSub) {

        this.intakeSub = intakeSub;
        this.addRequirements(intakeSub);
    }

    @Override
    public void execute() {
        double leftTargetPosition = States.intakeDeployed ? Constants.LEFT_INTAKE_DEPLOYED_POSITION : Constants.INTAKE_RETRACTED_POSITION;
        double rightTargetPosition = States.intakeDeployed ? Constants.RIGHT_INTAKE_DEPLOYED_POSITION : Constants.INTAKE_RETRACTED_POSITION;
      
        this.MoveIntake(leftTargetPosition, IntakeArm.LeftArm);
        this.MoveIntake(rightTargetPosition, IntakeArm.RightArm);
    }

    // Always returns a positive speed
    private double calculateSpeed(double distance)
    {
        double distanceToTarget = Math.abs(distance);
        double speed = 0;
        if (distanceToTarget >= 15)
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

    @Override
    protected void finalize() throws Throwable {
        this.intakeSub.setSpeed(0, IntakeArm.LeftArm);
        this.intakeSub.setSpeed(0, IntakeArm.RightArm);
    }
}
