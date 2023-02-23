package frc.robot.commands;

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

    public IntakeHolderCommand(IntakeSub intakeSub, CommandXboxController controller) {
        this.intakeSub = intakeSub;
        this.stick = controller;
        this.addRequirements(intakeSub);
        Crashboard.toDashboard(PaddleRangeKey, paddleRange);
    }

    @Override
    public void execute() {

        if (States.intakeDeployed) {
            this.intakeSub.ActivateWheels(wheelSpeed);
            paddleRange = Crashboard.snagDouble(PaddleRangeKey);
            targetPosition = Constants.INTAKE_DEPLOYED_POSITION + (paddleRange * stick.getLeftTriggerAxis());

            // Using the left arm as master
            if (targetPosition < this.intakeSub.getPosition(IntakeArm.LeftArm) - 0.5) {
                intakeSub.setSpeed(speed * -1);
            } else if (targetPosition > this.intakeSub.getPosition(IntakeArm.LeftArm) + 0.5) {
                intakeSub.setSpeed(speed);
            } else { 
                intakeSub.setSpeed(0); 
            }
        } else {
            intakeSub.setSpeed(0);
        }
        if (stick.getLeftTriggerAxis() <= 0.15) {
            intakeSub.ActivateWheels(0);
        }
    }

    @Override
    public boolean isFinished() {
        
        return super.isFinished();
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
        // TODO Auto-generated method stub
        intakeSub.setSpeed(0);
        
        super.end(interrupted);     
    }
}
