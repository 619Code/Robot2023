package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.SparkErrorHelper;

public class Hinge extends SubsystemBase {
    public CANSparkMax hingeMotor;
    private RelativeEncoder hingeEncoder;

    private DigitalInput magnetSwitch; 

    public boolean lastMovingDown;

    private GenericEntry hingeSpark;
    private GenericEntry hingeSwitch;

    public Hinge() {
        hingeMotor = new CANSparkMax(Constants.HINGE_MOTOR, MotorType.kBrushless);

        hingeMotor.restoreFactoryDefaults();
        hingeMotor.setIdleMode(IdleMode.kBrake);
        hingeMotor.setSmartCurrentLimit(40);
        hingeMotor.setInverted(true);

        hingeEncoder = hingeMotor.getEncoder();
        hingeEncoder.setPosition(Constants.HINGE_START);

        lastMovingDown = true;

        magnetSwitch = new DigitalInput(Constants.HINGE_SWITCH);
    }

    @Override
    public void periodic() {
        Crashboard.toDashboard("Hinge Position", getPosition(), Constants.ARM_TAB);
        Crashboard.toDashboard("Hinge Position", getPosition(), Constants.COMPETITON_TAB);
        Crashboard.toDashboard("Hinge Amps", hingeMotor.getOutputCurrent(), Constants.ARM_TAB);
        hingeSpark = Crashboard.toDashboard("Hinge Spark", SparkErrorHelper.HasSensorError(hingeMotor), Constants.SPARKS_TAB);
        hingeSwitch = Crashboard.toDashboard("Hinge Switch Triggd?", switchIsPressed(), Constants.STATUS_TAB);

        Crashboard.toDashboard("Hinge Velocity", hingeEncoder.getVelocity(), Constants.ARM_TAB);
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
            if(switchIsPressed()) {
                stop(); move = false;
            }
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

        double speed = Math.abs(getPosition() - goal) * Constants.HINGE_P; //proportional control
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
