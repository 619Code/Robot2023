package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.helpers.ArmMotors;
import frc.robot.helpers.IntakeArm;

public class IntakeSub extends SubsystemBase {
  
  ArmMotors leftMotors;
  ArmMotors rightMotors;

  public IntakeSub() { 

    leftMotors = new ArmMotors(Constants.LEFT_ARM, Constants.LEFT_WHEEL, true, "Left Arm");
    rightMotors = new ArmMotors(Constants.RIGHT_ARM, Constants.RIGHT_WHEEL, false, "Right Arm");
    
  }

  @Override
  public void periodic() {
    rightMotors.LogData();
    leftMotors.LogData();

    rightMotors.SafetyCheck();
  }

  public void ActivateWheels(double speed) {
    rightMotors.ActivateWheel(speed);
    leftMotors.ActivateWheel(speed);
  }

  public double getPosition(IntakeArm arm) {
    ArmMotors motors = getArmMotors(arm);
    return motors.GetPosition();
  }

  private ArmMotors getArmMotors(IntakeArm arm) {
    if (arm == IntakeArm.LeftArm) {
      return this.leftMotors;
    } else {
      return this.rightMotors;
    }
  }

  public void setSpeed(double speed, IntakeArm arm) {
    var motor = this.getArmMotors(arm);
    motor.moveArmBySpeed(speed);
  }
}