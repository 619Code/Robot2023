package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.IntakeArm;
import frc.robot.subsystems.IntakeSub;

public class MoveBySpeedCommand extends CommandBase {

    private IntakeSub intakeSub;

    public MoveBySpeedCommand(IntakeSub intakeSub) {
        this.intakeSub = intakeSub;
        this.addRequirements(intakeSub);
    }

    @Override
    public void execute() {
        
        var speed = States.intakeDeployed ? .1 : -.1;
        intakeSub.setSpeed(speed, IntakeArm.LeftArm);
        super.execute();
    }

    @Override
    public boolean isFinished() {

        var targetPosition = States.intakeDeployed ? Constants.INTAKE_DEPLOYED_POSITION : Constants.INTAKE_RETRACTED_POSITION;
        
        if (States.intakeDeployed && intakeSub.getPosition(IntakeArm.LeftArm) >= targetPosition)
        {
            intakeSub.setSpeed(0, IntakeArm.LeftArm);
            return true;
        }
        else if (!States.intakeDeployed && intakeSub.getPosition(IntakeArm.LeftArm) <= targetPosition)
        {
            intakeSub.setSpeed(0, IntakeArm.LeftArm);
            return true;
        }            
        else
        {
            return false;
        }
    }
    

    @Override
    public void initialize() {
        States.intakeDeployed = !States.intakeDeployed;
    }

    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
        this.intakeSub.setSpeed(0, IntakeArm.LeftArm);
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        super.end(interrupted);
        this.intakeSub.setSpeed(0, IntakeArm.LeftArm);
    }
}
