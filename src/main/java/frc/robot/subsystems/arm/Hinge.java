package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.ArmLogicAssistant;
import frc.robot.helpers.ArmPositionHelper;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.SparkErrorHelper;
import frc.robot.helpers.enums.ArmPosition;

public class Hinge extends SubsystemBase {
    public CANSparkMax hingeMotor;
    private RelativeEncoder hingeEncoder;

    private DigitalInput magnetSwitch; 

    private GenericEntry hingeSpark;
    private GenericEntry hingeSwitch;

    private double slowSpeed = Constants.SLOW_MODE_SPEED;
    private double maxSpeed = Constants.HINGE_MAX_SPEED;
    private double ffBaseValue = Constants.HINGE_FF;
    private double tolerance = Constants.BIG_TOLERANCE;
    private double closePosition = Constants.CLOSE_POSITION;

    private GenericEntry toleranceEntry;
    private GenericEntry minSpeedEntry;
    private GenericEntry ffEntry;
    private GenericEntry maxSpeedEntry;
    private GenericEntry closePostionEntry;

    public Hinge() {
        hingeMotor = new CANSparkMax(Constants.HINGE_MOTOR, MotorType.kBrushless);
        hingeMotor.restoreFactoryDefaults();
        hingeMotor.setIdleMode(IdleMode.kBrake);
        hingeMotor.setSmartCurrentLimit(30);
        hingeMotor.setInverted(true);

        hingeEncoder = hingeMotor.getEncoder();
        hingeEncoder.setPosition(Constants.HINGE_START);

        magnetSwitch = new DigitalInput(Constants.HINGE_SWITCH);

        ArmPositionHelper.currentPosition = ArmPosition.START;
        ArmPositionHelper.hingeAdjustment = 0;
        ArmLogicAssistant.startPosition = ArmPosition.START;
        ArmLogicAssistant.endPosition = ArmPosition.START;

        maxSpeedEntry = Crashboard.toDashboard("Max Speed", maxSpeed , Constants.ARM_TAB);
        minSpeedEntry = Crashboard.toDashboard("Min Speed", slowSpeed , Constants.ARM_TAB);
        ffEntry = Crashboard.toDashboard("FF", ffBaseValue , Constants.ARM_TAB);
        toleranceEntry = Crashboard.toDashboard("Tolerance", tolerance , Constants.ARM_TAB);
        closePostionEntry = Crashboard.toDashboard("Close Position", closePosition , Constants.ARM_TAB);
    }

    public void GetFromDashboard() {
        // maxSpeed = maxSpeedEntry.getDouble(this.maxSpeed);
        // minSpeed = minSpeedEntry.getDouble(this.minSpeed);
        // tolerance = toleranceEntry.getDouble(this.tolernace);
        // ffBaseValue = ffEntry.getDouble(this.ffBaseValue);
        // closePosition = closePostionEntry.getDouble(closePosition);
    }

    @Override
    public void periodic() {
        GetFromDashboard();
        checkSafety();

        States.ArmAngleInDegrees = this.getAngle();
        States.ArmAngleInDegreesFromStart = this.getPosition() * Constants.DEGREES_PER_TICK;
        Crashboard.toDashboard("Hinge Angle", getAngle(), Constants.WRIST_TAB);
        Crashboard.toDashboard("Hinge Position", getPosition(), Constants.ARM_TAB);
        hingeSpark = Crashboard.toDashboard("Hinge Spark", SparkErrorHelper.HasSensorError(hingeMotor), Constants.SPARKS_TAB);
        hingeSwitch = Crashboard.toDashboard("Hinge Switch", switchIsPressed(), Constants.STATUS_TAB);
        Crashboard.toDashboard("Hinge Amps", hingeMotor.getAppliedOutput(), Constants.ARM_TAB);

        Crashboard.toDashboard("Hinge Angle", getAngle(), Constants.ARM_TAB);
    }

    public void move(double speed) {
        move(speed, false);
    }

    public void move(double speed, boolean zeroing) {

        if (speed == 0)
        {
            stop();
            return;
        }

        boolean move = true;

        if(speed > 0) {
            if(!zeroing && getPosition() > Constants.MAX_HINGE_POSITION) {
                move = false;
            }
        } else if(speed < 0) {
            /*if(switchIsPressed()) { //UNDO
                stop(); move = false;
            }*/
            if(!zeroing && getPosition() <= Constants.MIN_HINGE_POSITION) {
                move = false;
            }
        }

        if(move) {
            hingeMotor.set(speed);
        } else {
            stop();
        }
    }

    // Calculate speed based off of position difference
    protected double calculateSpeed(double diff) {
        if (Math.abs(diff) <= closePosition) { //slow speed if we're close enough
            return Math.min(slowSpeed, Constants.HINGE_P * Math.abs(diff));
        } else { //otherwise, move with full speed
            return maxSpeed;
        }
    }

    // Calculate FF based on side, length, and angle of arm
    public double calculateFF() {
        double angleFactor = Math.cos(Math.toRadians(getAngle()));
        double ff = Math.abs(ffBaseValue * (States.ArmLength * angleFactor)); 

        //invert feedforward based on side
        if (getPosition() > Constants.UP_POSITION) //invert feedforward if we're in the back
            return ff * -1;
        else
            return ff;
    }

    public double getAngle() {
        return getPosition() * Constants.DEGREES_PER_TICK + Constants.BASE_ANGLE;
    }

    public boolean moveToPosition(double goal) {
        goal = Math.min(goal,Constants.MAX_HINGE_POSITION);
        goal = Math.max(goal,Constants.MIN_HINGE_POSITION);

        double diff = goal - getPosition();
        double speed = calculateSpeed(diff);
        double ff = calculateFF();

        if (Math.abs(diff) > Constants.SMALL_TOLERANCE) {
            if (diff > 0) {
                move(speed + ff);
            } else {
                move(-speed + ff);
            }
        } else {
            move(ff);
        }

        if(Math.abs(diff) > Constants.BIG_TOLERANCE) {
            return false;
        } else {
            return true;
        }
    }

    public void checkSafety() {
        if(getDirectVelocity() > 0) {
            if(getPosition() > Constants.MAX_HINGE_POSITION) {
                stop();
            }
        } else {
            if(getPosition() <= Constants.MIN_HINGE_POSITION) {
                stop();
            }
        }
    }

    public void stop() {
        hingeMotor.set(0);
    }

    public double getPosition() {
        return hingeEncoder.getPosition();
    }

    //directly fetches the percent speed of the motor
    public double getDirectVelocity() {
        return hingeMotor.get();
    }

    public boolean switchIsPressed() {
        return !magnetSwitch.get();
    }

    public void zero() {
        hingeEncoder.setPosition(0);
    }
}

