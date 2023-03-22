package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
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

    private PIDController hingePID;

    public Hinge() {
        hingeMotor = new CANSparkMax(Constants.HINGE_MOTOR, MotorType.kBrushless);
        hingeMotor.restoreFactoryDefaults();
        hingeMotor.setIdleMode(IdleMode.kBrake);
        hingeMotor.setSmartCurrentLimit(40);
        hingeMotor.setInverted(true);

        hingeEncoder = hingeMotor.getEncoder();
        hingeEncoder.setPosition(Constants.HINGE_START);

        magnetSwitch = new DigitalInput(Constants.HINGE_SWITCH);

        ArmPositionHelper.currentPosition = ArmPosition.START;
        ArmPositionHelper.hingeAdjustment = 0;
        ArmLogicAssistant.startPosition = ArmPosition.START;
        ArmLogicAssistant.endPosition = ArmPosition.START;

        hingePID = new PIDController(Constants.HINGE_P, 0, Constants.HINGE_D);
    }

    @Override
    public void periodic() {
        Crashboard.toDashboard("Hinge Position", getPosition(), Constants.ARM_TAB);
        Crashboard.toDashboard("Hinge Amps", hingeMotor.getOutputCurrent(), Constants.ARM_TAB);
        hingeSpark = Crashboard.toDashboard("Hinge Spark", SparkErrorHelper.HasSensorError(hingeMotor), Constants.SPARKS_TAB);
        hingeSwitch = Crashboard.toDashboard("Hinge Switch Triggd?", switchIsPressed(), Constants.STATUS_TAB);
        
        Crashboard.toDashboard("Hinge Amps", hingeMotor.getAppliedOutput(), Constants.ARM_TAB);
    }

    public void move(double speed) {
        move(speed, false);
    }

    public void move(double speed, boolean zeroing) {
        boolean move = true;

        if(speed > 0) {
            if(!zeroing && getPosition() > Constants.MAX_HINGE_POSITION) {
                stop(); move = false;
            }
        } else if(speed < 0) {
            /*if(switchIsPressed()) { //UNDO
                stop(); move = false;
            }*/
            if(!zeroing && getPosition() < Constants.MIN_HINGE_POSITION) {
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

        hingePID.calculate(Math.abs(getPosition() - goal));
        double speed = Math.abs(hingePID.calculate(Math.abs(getPosition() - goal))); //PID control
        speed = Math.min(speed, Constants.HINGE_SPEED);

        if(getPosition() < goal) {
            move(speed);
        } else {
            move(-speed);
        }

        if(Math.abs(getPosition() - goal) < 1) {
            return true;
        } else {
            return false;
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
