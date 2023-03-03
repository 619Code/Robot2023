package frc.robot.subsystems;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.helpers.ArmMotors;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.enums.IntakeArm;

public class IntakeSub extends SubsystemBase {
  
  ArmMotors leftMotors;
  ArmMotors rightMotors;

  public boolean zeroedLeft;
  public boolean zeroedRight;

  private GenericEntry leftEncoderPos, rightEncoderPos;

  public IntakeSub() { 

    leftMotors = new ArmMotors(Constants.LEFT_ARM, Constants.LEFT_WHEEL, Constants.INTAKE_LEFT_SWITCH, true, "Left Arm");
    rightMotors = new ArmMotors(Constants.RIGHT_ARM, Constants.RIGHT_WHEEL, Constants.INTAKE_RIGHT_SWITCH, false, "Right Arm");

    leftMotors.setPosition(0);
    rightMotors.setPosition(0);

    leftEncoderPos = Crashboard.toDashboard("Left Encoder", leftMotors.GetPosition(), Constants.INTAKE_TAB);
    rightEncoderPos = Crashboard.toDashboard("Right Encoder", rightMotors.GetPosition(), Constants.INTAKE_TAB);
    
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

  public void setSpeed(double speed)
  {
    this.setSpeed(speed, IntakeArm.LeftArm);
    this.setSpeed(speed, IntakeArm.RightArm);
  }

  public void setSpeed(double speed, IntakeArm arm) {
    var motor = this.getArmMotors(arm);
    motor.moveArmBySpeed(speed);
  }

  public ArmMotors getLeftArm() {
    return leftMotors;
  }
  public ArmMotors getRightArm() {
    return rightMotors;
  }
}