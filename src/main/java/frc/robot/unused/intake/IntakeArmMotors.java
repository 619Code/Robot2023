package frc.robot.unused.intake;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.SparkErrorHelper;

public class IntakeArmMotors {

    public CANSparkMax armMotor;
    public CANSparkMax wheelMotor;
    public DigitalInput limitSwitch;
    RelativeEncoder armEncoder;
    int intakeArmCanId;
    int wheelMotorCanId;
    int switchPort;

    public boolean loggingOn = true;
    boolean inverted = false;
    String name;



    private GenericEntry ArmPosEntry;
    private GenericEntry armSpark;
    private GenericEntry wheelSpark;
    private GenericEntry limitSwitchTrigged;


    public IntakeArmMotors(int intakeArmCanId, int wheelMotorCanId, int switchPort, boolean inverted, String name) {
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

        armMotor.setSmartCurrentLimit(Constants.INTAKE_CURRENT_LIMIT);
        wheelMotor.setSmartCurrentLimit(Constants.INTAKE_CURRENT_LIMIT);

        armMotor.setIdleMode(IdleMode.kBrake);

        // Luke wants to try brake mode on the wheels
        wheelMotor.setIdleMode(IdleMode.kBrake);
        
        armEncoder = armMotor.getEncoder();
        armEncoder.setPosition(0);

        armMotor.setInverted(inverted);

        // Might need to track seperately
        wheelMotor.setInverted(inverted);
    }

    public boolean getZeroSwitch() {
        return !limitSwitch.get();
    }

    public void LogData() {
        if (loggingOn) {
            ArmPosEntry = Crashboard.toDashboard(name + " Arm Position", armEncoder.getPosition(), Constants.INTAKE_TAB );
            armSpark = Crashboard.toDashboard(name + "Spark Status Arm", SparkErrorHelper.HasSensorError(armMotor), Constants.SPARKS_TAB);
            wheelSpark = Crashboard.toDashboard(name + "Spark Status Wheel", SparkErrorHelper.HasSensorError(wheelMotor), Constants.SPARKS_TAB);
            limitSwitchTrigged = Crashboard.toDashboard(name + "Switch Triggd?", getZeroSwitch(), Constants.STATUS_TAB);
        }
    }

    public void ActivateWheel(double speed)
    {
        this.wheelMotor.set(speed);
    }

    public void setPosition(double pos) {
        armEncoder.setPosition(pos);
    }

    public void StopWheel()
    {
        this.wheelMotor.set(0);
    }

    public double moveArmBySpeed(double speed) {
        return moveArmBySpeed(speed, false);
    }

    public double moveArmBySpeed(double speed, boolean zeroing) {
        if(speed < 0 && getZeroSwitch()) {
            this.armMotor.set(0);
        } else if(!zeroing && !IsSafe(speed)) {
            this.armMotor.set(0);
        } else {
            this.armMotor.set(speed);
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

        // Check position and limit switch to see if it is safe to still move
        if (speed < 0 && armEncoder.getPosition() < 0) {
            return false;
        }

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
