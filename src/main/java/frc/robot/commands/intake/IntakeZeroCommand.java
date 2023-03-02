package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSub;

public class IntakeZeroCommand extends CommandBase {

    private IntakeSub intake;
    private double armSpeed = -0.1;
    private int counter;

    public IntakeZeroCommand(IntakeSub intak) {
        intake = intak;
        counter = 0;
        
    }

    @Override
    public void execute() {
        counter ++;
        if (!intake.getLeftArm().getZeroSwitch() && counter <= 500){
            intake.getLeftArm().moveArmBySpeed(armSpeed);
            intake.zeroedLeft = false;
        } else {
            intake.getLeftArm().moveArmBySpeed(0);
            intake.getLeftArm().setPosition(0);
            intake.zeroedLeft = true;
        }
        if (!intake.getRightArm().getZeroSwitch() && counter <= 500){
            intake.getRightArm().moveArmBySpeed(armSpeed);
            intake.zeroedRight = false;
        } else {
            intake.getRightArm().moveArmBySpeed(0);
            intake.getRightArm().setPosition(0);
            intake.zeroedRight = true;
        }
    }
}