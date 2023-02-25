package frc.robot.commands;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.IntakeArm;
import frc.robot.subsystems.IntakeSub;

public class IntakeHolderCommand extends CommandBase {

    private IntakeSub intakeSub;
    private CommandXboxController stick;
    private double paddleRange = 6;
    private String PaddleRangeKey = "Paddle Range";
    double targetPosition;
    double speed = .1;
    double wheelSpeed = -0.1;
    double tolerance = .3;
    GenericEntry paddleRangeEntry;

    public IntakeHolderCommand(IntakeSub intakeSub, CommandXboxController controller) {
        this.intakeSub = intakeSub;
        this.stick = controller;
        this.addRequirements(intakeSub);
        paddleRangeEntry = Crashboard.toDashboard(PaddleRangeKey, paddleRange, Constants.IntakeTab);
    }

    @Override
    public void execute() {

        if (States.intakeDeployed) {
            this.intakeSub.ActivateWheels(wheelSpeed);
            paddleRange = paddleRangeEntry.getDouble(paddleRange);
            targetPosition = Constants.INTAKE_DEPLOYED_POSITION + (paddleRange * stick.getLeftTriggerAxis());
            this.moveIntake(targetPosition, IntakeArm.LeftArm);
            this.moveIntake(targetPosition, IntakeArm.RightArm);
        }
        if (stick.getLeftTriggerAxis() <= 0.15) {
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
        super.finalize();   
    }

    @Override
    public void end(boolean interrupted) {
        intakeSub.setSpeed(0);
        intakeSub.ActivateWheels(0);       
        super.end(interrupted);     
    }
}
