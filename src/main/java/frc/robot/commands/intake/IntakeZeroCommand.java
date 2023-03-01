package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSub;

public class IntakeZeroCommand extends CommandBase {

    private IntakeSub intake;
    private double armSpeed = -0.1;

    public IntakeZeroCommand(IntakeSub intak) {
        intake = intak;
        
    }

    @Override
    public void execute() {
        if (!intake.getLeftArm().getZeroSwitch()){
            intake.getLeftArm().moveArmBySpeed(armSpeed);
            intake.zeroedLeft = false;
        } else {
            intake.getLeftArm().moveArmBySpeed(0);
            intake.getLeftArm().setPosition(0);
            intake.zeroedLeft = true;
        }
        if (!intake.getRightArm().getZeroSwitch()){
            intake.getRightArm().moveArmBySpeed(armSpeed);
            intake.zeroedRight = false;
        } else {
            intake.getRightArm().moveArmBySpeed(0);
            intake.getRightArm().setPosition(0);
            intake.zeroedRight = true;
        
        }
    }
}