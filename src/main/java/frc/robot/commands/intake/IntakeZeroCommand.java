package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.subsystems.IntakeSub;

public class IntakeZeroCommand extends CommandBase {

    private IntakeSub intake;
    private double armSpeed = Constants.INTAKE_ZERO_SPEED;
    private Timer timer;
    private double zeroTimerTimeout = Constants.INTAKE_ZERO_TIMEOUT;

    public IntakeZeroCommand(IntakeSub intake) {
        this.intake = intake;
        timer = new Timer();
        
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        intake.zeroedLeft = false;
        intake.zeroedRight = false;
    }

    @Override
    public void execute() {
        if (!intake.zeroedLeft && !intake.getLeftArm().getZeroSwitch()){
            intake.getLeftArm().moveArmBySpeed(armSpeed, true);
            intake.zeroedLeft = false;
        } else {
            intake.getLeftArm().moveArmBySpeed(0);
            intake.getLeftArm().setPosition(0);
            intake.zeroedLeft = true;
        }

        if (!intake.zeroedRight && !intake.getRightArm().getZeroSwitch()){
            intake.getRightArm().moveArmBySpeed(armSpeed, true);
            intake.zeroedRight = false;
        } else {
            intake.getRightArm().moveArmBySpeed(0);
            intake.getRightArm().setPosition(0);
            intake.zeroedRight = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.getRightArm().moveArmBySpeed(0);
        intake.getLeftArm().moveArmBySpeed(0);

        //If all went well we should be undeployed at the zero position
        States.intakeDeployed = false;
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(zeroTimerTimeout) || (intake.zeroedLeft && intake.zeroedRight);
    }

    @Override
    protected void finalize() throws Throwable {
        intake.getRightArm().moveArmBySpeed(0);
        intake.getLeftArm().moveArmBySpeed(0);

        //If all went well we should be undeployed at the zero position
        States.intakeDeployed = false;
    }

    
}