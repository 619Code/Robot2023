package frc.robot.helpers;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants;

public class ArmMotors {

    public CANSparkMax armMotor;
    public CANSparkMax wheelMotor;
    public DigitalInput limitSwitch;
    RelativeEncoder armEncoder;
    int intakeArmCanId;
    int wheelMotorCanId;
    int switchPort;

    public boolean loggingOn = false;
    boolean inverted = false;
    String name;
    public double ArmSpeed = .1;
    public double WheelSpeed = .1;
    private GenericEntry ArmPosEntry;
    private GenericEntry armSpark;
    private GenericEntry wheelSpark;
    private GenericEntry limitSwitchTrigged;


    public ArmMotors(int intakeArmCanId, int wheelMotorCanId, int switchPort, boolean inverted, String name) {
        this.intakeArmCanId = intakeArmCanId;
        this.wheelMotorCanId = wheelMotorCanId;
        this.switchPort = switchPort;
        this.inverted = inverted;
        this.name = name;
        this.Initialize();
    }

    public void Initialize() {
        armMotor = new CANSparkMax(intakeArmCanId, MotorType.kBrushless);
        wheelMotor = new CANSparkMax(wheelMotorCanId, MotorType.kBrushless);
        limitSwitch = new DigitalInput(switchPort);

        armMotor.restoreFactoryDefaults();
        wheelMotor.restoreFactoryDefaults();

        armMotor.setSmartCurrentLimit(30);
        wheelMotor.setSmartCurrentLimit(30);

        armMotor.setIdleMode(IdleMode.kBrake);
        wheelMotor.setIdleMode(IdleMode.kCoast);
        
        armEncoder = armMotor.getEncoder();
        armEncoder.setPosition(0);

        armMotor.setInverted(inverted);

        // Might need to track seperately
        wheelMotor.setInverted(inverted);
        
    }

    public void LogData() {
        if (loggingOn) {
            ArmPosEntry = Crashboard.toDashboard(name + " Arm Position", armEncoder.getPosition(), Constants.ARM_TAB );
                          Crashboard.toDashboard(name + " Arm Position", armEncoder.getPosition(), Constants.COMPETITON_TAB);
            armSpark = Crashboard.toDashboard(name + "Spark Status Arm", SparkErrorHelper.HasSensorError(armMotor), Constants.SPARKS_TAB);
            wheelSpark = Crashboard.toDashboard(name + "Spark Status Wheel", SparkErrorHelper.HasSensorError(wheelMotor), Constants.SPARKS_TAB);
            limitSwitchTrigged = Crashboard.toDashboard(name + "Switch Triggd?", limitSwitch.get(), Constants.OverallStatus);
        }
    }

    public void ActivateWheel(double speed)
    {
        this.wheelMotor.set(speed);
    }

    public void StopWheel()
    {
        this.wheelMotor.set(0);
    }

    public double moveArmBySpeed(double speed) {
        if (IsSafe(speed)) {
            this.armMotor.set(speed);
        }
        else {
            this.armMotor.set(0);
        }

        return this.armEncoder.getPosition();
    }

    public void SafetyCheck() {
        if (!IsSafe(this.armMotor.get())) {
            // System.out.println("Safety Check Called");
            this.armMotor.set(0);
        }
    }

    public boolean IsSafe(double speed)
    {
        // // Check position and limit switch to see if it is safe to still move
        // if (speed <= 0) {
        //     return this.armEncoder.getPosition() >= 0;
        // } 

        // if (speed > 0) {
        //     return this.armEncoder.getPosition() <= 50;
        // }

        // return false;
        
        //trolling
        return true;
    }

    public double GetPosition() {
        return this.armEncoder.getPosition();
    }

      // public void moveToPosition(double pos, IntakeArm arm) {
    //   CANSparkMax motor = getArmMotor(arm);
    //   SparkMaxPIDController PID = motor.getPIDController();

    //   PID.setOutputRange(-0.2, 0.2);
        

    //   if (PID.getP() != pVal) {
    //     PID.setP(pVal);
    //   }
    //   PID.setI(0);
    //   PID.setD(0);
        

    //   PID.setReference(pos, ControlType.kPosition);

        
        
    // }

    // public void moveMotor(double proportion, boolean isRight) {
  //   double targetPosition; CANSparkMax motor; RelativeEncoder armEncoder;
  //   targetPosition = proportion*maxRotation;

  //   if(isRight) {
  //     targetPositionRight = targetPosition;
  //     motor = RightArmMotor;
  //     armEncoder = RightArmEncoder;
  //   } else {
  //     targetPositionLeft = targetPosition;
  //     motor = LeftArmMotor;
  //     armEncoder = LeftArmEncoder;
  //   }

  //   inRange = Math.abs(targetPosition-armEncoder.getPosition()) <= range;

  //   if(!inRange) {
  //     if(targetPosition < armEncoder.getPosition()){
  //       //System.out.println("Moving in");
  //       motor.set(-ArmSpeed);
  //     } else {
  //       //System.out.println("Moving out");
  //       motor.set(ArmSpeed);
  //     }
  //   } else {
  //     //System.out.println("Staying in place");
  //     motor.set(defaultSpeed);
  //   }
  // }

}
