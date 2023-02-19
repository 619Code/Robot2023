package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.IntakeArm;

public class IntakeSub extends SubsystemBase {
  CANSparkMax LeftArmMotor, RightArmMotor, LeftWheelMotor, RightWheelMotor;
  RelativeEncoder LeftArmEncoder, RightArmEncoder;
  private double maxRotation = 10;
  private double range = 0.2;
  private double ArmSpeed = 0.07;
  private double defaultSpeed = -0.02;

  double targetPositionRight;
  double targetPositionLeft;
  boolean inRange;

  private double pVal;

  
  public IntakeSub() { 
    LeftWheelMotor = new CANSparkMax(Constants.LEFT_WHEEL, MotorType.kBrushless);
    RightWheelMotor = new CANSparkMax(Constants.RIGHT_WHEEL, MotorType.kBrushless);
    LeftArmMotor = new CANSparkMax(Constants.LEFT_ARM, MotorType.kBrushless);
    RightArmMotor = new CANSparkMax(Constants.RIGHT_ARM, MotorType.kBrushless);

    LeftArmMotor.restoreFactoryDefaults();
    RightArmMotor.restoreFactoryDefaults();
    LeftWheelMotor.restoreFactoryDefaults();
    RightWheelMotor.restoreFactoryDefaults();

    LeftArmMotor.setSmartCurrentLimit(30);
    RightArmMotor.setSmartCurrentLimit(30);

    RightArmMotor.setIdleMode(IdleMode.kBrake);
    LeftArmMotor.setIdleMode(IdleMode.kBrake);

    LeftArmEncoder = LeftArmMotor.getEncoder();
    RightArmEncoder = RightArmMotor.getEncoder();
    RightArmEncoder.setPosition(0);
    LeftArmEncoder.setPosition(0);

    LeftWheelMotor.setInverted(false);
    RightWheelMotor.setInverted(true);
    RightArmMotor.setInverted(false);
    LeftArmMotor.setInverted(true);

    Crashboard.toDashboard("LeftPosition", LeftArmEncoder.getPosition() );
    Crashboard.toDashboard("RightPosition", RightArmEncoder.getPosition() );
    Crashboard.toDashboard("LeftTarget", targetPositionLeft);
    Crashboard.toDashboard("RightTarget", targetPositionRight);
    Crashboard.toDashboard("Intake kP", 0.0001);
    
    
  }

  public void moveMotor(double proportion, boolean isRight) {
    double targetPosition; CANSparkMax motor; RelativeEncoder armEncoder;
    targetPosition = proportion*maxRotation;

    if(isRight) {
      targetPositionRight = targetPosition;
      motor = RightArmMotor;
      armEncoder = RightArmEncoder;
    } else {
      targetPositionLeft = targetPosition;
      motor = LeftArmMotor;
      armEncoder = LeftArmEncoder;
    }

    inRange = Math.abs(targetPosition-armEncoder.getPosition()) <= range;

    if(!inRange) {
      if(targetPosition < armEncoder.getPosition()){
        //System.out.println("Moving in");
        motor.set(-ArmSpeed);
      } else {
        //System.out.println("Moving out");
        motor.set(ArmSpeed);
      }
    } else {
      //System.out.println("Staying in place");
      motor.set(defaultSpeed);
    }
  }

  @Override
  public void periodic() {
    Crashboard.toDashboard("LeftPosition", LeftArmEncoder.getPosition() );
    Crashboard.toDashboard("RightPosition", RightArmEncoder.getPosition() );
    Crashboard.toDashboard("LeftTarget", targetPositionLeft);
    Crashboard.toDashboard("RightTarget", targetPositionRight);
    Crashboard.toDashboard("IntakeDeployed", States.intakeDeployed);
    pVal = Crashboard.snagDouble("Left kP");
    
  }

  public void ActivateWheels(double speed) {
    RightWheelMotor.set(speed);
    LeftWheelMotor.set(speed);
  }

  public void moveToPosition(double pos, IntakeArm arm) {
    CANSparkMax motor = getArmMotor(arm);
    SparkMaxPIDController PID = motor.getPIDController();

    PID.setOutputRange(-0.2, 0.2);
    

    if (PID.getP() != pVal) {
      PID.setP(pVal);
    }
    PID.setI(0);
    PID.setD(0);
    

    PID.setReference(pos, ControlType.kPosition);

    
    
  }

  public double getPosition(IntakeArm arm) {
    CANSparkMax motor = getArmMotor(arm);
    RelativeEncoder encoder = motor.getEncoder();

    return encoder.getPosition();
    
  }

  public void moveToPosition(double pos) {
    System.out.println("moveToPosition called - " + pos);
    moveToPosition(pos, IntakeArm.LeftArm);
    moveToPosition(pos, IntakeArm.RightArm);
  }

  private CANSparkMax getArmMotor(IntakeArm arm) {
    if (arm == IntakeArm.LeftArm) {
      return LeftArmMotor;
    } else {
      return RightArmMotor;
    }
  }

  public void deployIntake() {}

  public void setSpeed(double speed, IntakeArm arm) {
    var motor = this.getArmMotor(arm);
    motor.set(speed);
  }
}