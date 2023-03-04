package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.ColorDetector;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.SparkErrorHelper;

public class Grabber extends SubsystemBase {
    private CANSparkMax grabberMotor;
    private RelativeEncoder grabberEncoder;
    private DigitalInput limitSwitch;

    public boolean grabbing;

    private GenericEntry grabberSpark;
    private GenericEntry switchTrigged;

    public Grabber() {
        grabberMotor = new CANSparkMax(Constants.GRABBER_MOTOR, MotorType.kBrushless);
        grabberMotor.restoreFactoryDefaults();
        grabberMotor.setSmartCurrentLimit(35);
        grabberMotor.setIdleMode(IdleMode.kBrake);
        grabberMotor.setInverted(true);

        grabbing = true;

        grabberEncoder = grabberMotor.getEncoder();
        grabberEncoder.setPosition(Constants.GRABBER_START); //zero position

        limitSwitch = new DigitalInput(Constants.GRABBER_SWITCH);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Velocity", grabberEncoder.getVelocity());
        SmartDashboard.putBoolean("Switch", switchIsPressed());
        grabberSpark = Crashboard.toDashboard("Grabber Spark", SparkErrorHelper.HasSensorError(grabberMotor), Constants.SPARKS_TAB);
        switchTrigged = Crashboard.toDashboard("Grabber Switch Triggd?", switchIsPressed(), Constants.STATUS_TAB);

        Crashboard.toDashboard("Position", grabberEncoder.getPosition(), Constants.GRABBER_TAB);

        ColorDetector.update();
    }

    public void spinMotor(double speed) {
        spinMotor(speed, Constants.MAX_GRABBER_SPEED);
    }

    public void spinMotor(double speed, double speedMax) {
        if(speed > 0 && switchIsPressed()) {
            stop();
        } else {
            grabberMotor.set(speed * speedMax);
        }
        
        SmartDashboard.putNumber("Position", getPosition());
    }

    //boolean return says if it's at that position
    public boolean moveToPosition(double goal) {
        goal = Math.min(goal,0.0);
        goal = Math.max(goal,Constants.GRABBER_ZERO_POSITION);

        double speed = Math.abs(getPosition() - goal) * Constants.GRABBER_P; //proportional control
        speed = Math.min(speed, Constants.MAX_GRABBER_SPEED);
        
        if(Math.abs(getPosition() - goal) < 1) {
            stop();
            return true;
        } else if(getPosition() < goal) {
            spinMotor(speed);
            return false;
        } else {
            spinMotor(-speed);
            return false;
        }
    }

    public void stop() {
        grabberMotor.set(0);
    }

    public double getPosition() {
        return grabberEncoder.getPosition();
    }

    public boolean switchIsPressed() {
        return limitSwitch.get();
    }

    public void zeroAtPosition(double position) {
        grabberEncoder.setPosition(position);
    }
}
