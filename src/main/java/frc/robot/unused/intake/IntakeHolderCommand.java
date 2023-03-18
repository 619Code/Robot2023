package frc.robot.unused.intake;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.enums.IntakeArm;

public class IntakeHolderCommand extends CommandBase {

    private IntakeSub intakeSub;
    private CommandXboxController stick;
    private double paddleRangeRight = Constants.INTAKE_PADDLE_RANGE_RIGHT;
    private double paddleRangeLeft = Constants.INTAKE_PADDLE_RANGE_LEFT;
    private String PaddleRangeLeftKey = "Paddle Range Left";
    private String PaddleRangeRightKey = "Paddle Range Right";
    double targetPosition;
    double wheelSpeed = Constants.INTAKE_WHEEL_SPEED;    
    GenericEntry paddleRangeLeftEntry;
    GenericEntry paddleRangeRightEntry;
    private double tolerance = Constants.INTAKE_TOLERANCE;
    private double speed = Constants.INTAKE_MINSPEED;

    public IntakeHolderCommand(IntakeSub intakeSub, CommandXboxController controller) {
        this.intakeSub = intakeSub;
        this.stick = controller;
        this.addRequirements(intakeSub);
        paddleRangeLeftEntry = Crashboard.toDashboard(PaddleRangeLeftKey, paddleRangeLeft, Constants.INTAKE_TAB);
        paddleRangeRightEntry = Crashboard.toDashboard(PaddleRangeRightKey, paddleRangeRight, Constants.INTAKE_TAB);
    }

    @Override
    public void execute() {
        if (States.intakeDeployed) {
            this.intakeSub.ActivateWheels(wheelSpeed);
            
            var targetLeftPosition = Constants.LEFT_INTAKE_DEPLOYED_POSITION + (paddleRangeLeft * stick.getRightTriggerAxis());
            var targetRightPosition = Constants.RIGHT_INTAKE_DEPLOYED_POSITION + (paddleRangeRight * stick.getRightTriggerAxis()); 
            this.moveIntake(targetLeftPosition, IntakeArm.LeftArm);
            this.moveIntake(targetRightPosition, IntakeArm.RightArm);
        } else {
            intakeSub.ActivateWheels(0);
        }
    }

    private void moveIntake(double targetPosition, IntakeArm arm) {
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
        return !States.intakeDeployed;
    }

    @Override
    public void initialize() {
    }

    @Override
    protected void finalize() throws Throwable {
        intakeSub.ActivateWheels(0);
        intakeSub.setSpeed(0);   
    }

    @Override
    public void end(boolean interrupted) {
        intakeSub.setSpeed(0);
        intakeSub.ActivateWheels(0);       
        super.end(interrupted);     
    }
}
