package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSub;

public class IntakeZeroCommand extends CommandBase {

    private IntakeSub intake;
    private double armSpeed = -0.1;
    private Timer timer;
    private double zeroTimerMaxTime = 10;

    public IntakeZeroCommand(IntakeSub intak) {
        intake = intak;
        timer = new Timer();
        
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
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
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(zeroTimerMaxTime) || (intake.zeroedLeft && intake.zeroedRight);
    }
}