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
import frc.robot.helpers.SparkMaxPIDInfo;
import frc.robot.helpers.enums.ArmPosition;

public class Hinge extends SubsystemBase {
    public CANSparkMax hingeMotor;
    private RelativeEncoder hingeEncoder;

    private DigitalInput magnetSwitch; 

    private GenericEntry hingeSpark;
    private GenericEntry hingeSwitch;

    private PIDController hingePID;
    private double minSpeed = .075;
    private double maxSpeed = .3;
    private double ffBaseValue = .04;
    private double tolernace = .5;
    private double closePosition = 6;
    private GenericEntry toleranceEntry;
    private GenericEntry minSpeedEntry;
    private GenericEntry ffEntry;
    private GenericEntry maxSpeedEntry;
    private GenericEntry closePostionEntry;
    private double UpPosition = 35.09;
    private double HorizontalFrontPosition = 8.09;

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

        hingePID = new PIDController(Constants.HINGE_P, 0, Constants.HINGE_D);

        this.maxSpeedEntry = Crashboard.toDashboard("Max Speed", maxSpeed , Constants.ARM_TAB);
        this.minSpeedEntry = Crashboard.toDashboard("Min Speed", minSpeed , Constants.ARM_TAB);
        this.ffEntry = Crashboard.toDashboard("FF", ffBaseValue , Constants.ARM_TAB);
        this.toleranceEntry = Crashboard.toDashboard("Tolerance", tolernace , Constants.ARM_TAB);
        this.closePostionEntry = Crashboard.toDashboard("Close Position", closePosition , Constants.ARM_TAB);
    }

    public void GetFromDashboard() {
        // this.maxSpeed = this.maxSpeedEntry.getDouble(this.maxSpeed);
        // this.minSpeed = this.minSpeedEntry.getDouble(this.minSpeed);
        // this.tolernace = this.toleranceEntry.getDouble(this.tolernace);
        // this.ffBaseValue = this.ffEntry.getDouble(this.ffBaseValue);
        // this.closePosition = this.closePostionEntry.getDouble(closePosition);
    }

    @Override
    public void periodic() {

        this.GetFromDashboard();

        Crashboard.toDashboard("Hinge Position", getPosition(), Constants.ARM_TAB);
        Crashboard.toDashboard("Hinge Amps", hingeMotor.getOutputCurrent(), Constants.ARM_TAB);
        hingeSpark = Crashboard.toDashboard("Hinge Spark", SparkErrorHelper.HasSensorError(hingeMotor), Constants.SPARKS_TAB);
        hingeSwitch = Crashboard.toDashboard("Hinge Switch Triggd?", switchIsPressed(), Constants.STATUS_TAB);
        
        Crashboard.toDashboard("Hinge Amps", hingeMotor.getAppliedOutput(), Constants.ARM_TAB);

        if(this.hingeMotor.get() > 0) {
            if(getPosition() > Constants.MAX_HINGE_POSITION) {
                stop();
            }
        }

        if(this.hingeMotor.get() <= 0) {
            if(getPosition() <= Constants.MIN_HINGE_POSITION) {
                stop();
            }
        }
        
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
                stop(); move = false;
            }
        } else if(speed < 0) {
            /*if(switchIsPressed()) { //UNDO
                stop(); move = false;
            }*/
            if(!zeroing && getPosition() <= Constants.MIN_HINGE_POSITION) {
                stop(); move = false;
            }
        }

        if(move) {
            hingeMotor.set(speed);
        }
    }

    //boolean return says if it's at that position
    public boolean moveToPosition(double goal) {

        goal = Math.min(goal,Constants.MAX_HINGE_POSITION);
        goal = Math.max(goal,Constants.MIN_HINGE_POSITION);

        if(Math.abs(getPosition() - goal) < tolernace) 
            return true;

        hingePID.calculate(Math.abs(getPosition() - goal));
        double speed = Math.abs(hingePID.calculate(Math.abs(getPosition() - goal))); //PID control
        speed = Math.min(speed, Constants.HINGE_SPEED);

        if(getPosition() < goal) {
            move(speed);
        } else {
            move(-speed);
        }
        
        return false;
    }

    // Calculate speed based 
    protected double calculateSpeed(double diff)
    {
        if (Math.abs(diff) <= this.closePosition)
        {
            //return Math.min(minSpeed , Math.abs(diff) / closePosition * Math.abs(diff)/ closePosition * maxSpeed);            return Math.min(minSpeed , Math.abs(diff) / closePosition * Math.abs(diff)/ closePosition * maxSpeed);
            return Math.min(minSpeed , Math.abs(diff) / closePosition * maxSpeed);
        }
        else
        {
            return maxSpeed;
        }
    }

    // Calculate FF based on side, length, and angle of arm
    public double calculateFF() {

        double angleForCalc = 0.0;
        if (this.getAngle() > 90)
        {
            angleForCalc = this.getAngle() - 90.0;
        }
        else
        {
            angleForCalc = Math.abs(this.getAngle());
        }

        // Not using this yet
        var angleFactor = (Math.abs(Math.sin(angleForCalc) * States.ArmLength)/States.ArmLength)*ffBaseValue;
        
        //Cut angle factor in half
        angleFactor = angleFactor/2;

        var ff = ffBaseValue + Math.max(ffBaseValue,(ffBaseValue * (States.ArmLength-24)/States.ArmLength)); 
        
        Crashboard.toDashboard("FF Calculated Value", ff, Constants.ARM_TAB);

        if (this.getPosition() > this.UpPosition + 3)
            return ff * -1;
        else
            return ff;
    }

    public double getAngle() {

        var degreesPerTick = (UpPosition - HorizontalFrontPosition) / 90;

        if (this.getPosition() < HorizontalFrontPosition )
        {
            return -(this.getPosition() * degreesPerTick);
        }
        else
        {
            return this.getPosition() * degreesPerTick;
        }
    }

    //Front to Back is at 35
    public boolean moveToPositionSimple(double goal) {

        goal = Math.min(goal,Constants.MAX_HINGE_POSITION);
        goal = Math.max(goal,Constants.MIN_HINGE_POSITION);

        double diff = goal - this.getPosition();
        double speed = calculateSpeed(diff);
        double ff = calculateFF();

        if (Math.abs(diff) > this.tolernace )
        {
            if (diff > 0)
            {
                this.move(speed + ff, false);
            }
            else
            {
                this.move(-speed + ff);
            }
            return false;
        }
        else
        {
            this.move(ff);
            return true;
        }

    }

    public void stop() {
        hingeMotor.set(0);
    }

    public double getPosition() {
        return hingeEncoder.getPosition();
    }

    public boolean switchIsPressed() {
        return !magnetSwitch.get();
    }

    public void zero() {
        hingeEncoder.setPosition(0);
    }
}

