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

public class Telescope extends SubsystemBase {
    private CANSparkMax telescopeMotor;
    private RelativeEncoder telescopeEncoder;

    private DigitalInput contractedSwitch;
    private DigitalInput extendedSwitch;

    private GenericEntry telescopeSpark;
    private GenericEntry contractedSwitchTrigged;
    private GenericEntry extendedSwitchTrigged;

    public Telescope() {
        telescopeMotor = new CANSparkMax(Constants.TELESCOPE_MOTOR, MotorType.kBrushless);
        telescopeMotor.restoreFactoryDefaults();
        telescopeMotor.setIdleMode(IdleMode.kBrake);
        telescopeMotor.setSmartCurrentLimit(40);
        telescopeMotor.setInverted(false);

        telescopeEncoder = telescopeMotor.getEncoder();
        telescopeEncoder.setPosition(Constants.TELESCOPE_START);

        contractedSwitch = new DigitalInput(Constants.TELESCOPE_CONTRACTED_SWITCH);
        extendedSwitch = new DigitalInput(Constants.TELESCOPE_EXTENDED_SWITCH);
    }

    @Override
    public void periodic() {
        checkSafety();

        //Crashboard.toDashboard("Contracted Switch", contractedSwitchIsPressed());
        //Crashboard.toDashboard("Extended Switch", extendedSwitchIsPressed());
        Crashboard.toDashboard("Telescope Position", getPosition(), Constants.ARM_TAB);
        telescopeSpark = Crashboard.toDashboard("Telescope Spark", SparkErrorHelper.HasSensorError(telescopeMotor), Constants.SPARKS_TAB);
        contractedSwitchTrigged = Crashboard.toDashboard("Contracted Switch Triggd?", contractedSwitchIsPressed(), Constants.STATUS_TAB);
        extendedSwitchTrigged = Crashboard.toDashboard("Extended Swtich Triggd?", extendedSwitchIsPressed(), Constants.STATUS_TAB);

        Crashboard.toDashboard("Telescope Position", telescopeEncoder.getPosition(), Constants.ARM_TAB);
    }

    public void move(double speed) {
        move(speed, false);
    }

    public void move(double speed, boolean zeroing) {
        boolean move = true;

        if(speed > 0) {
            if(extendedSwitchIsPressed()) {
                stop(); move = false;
            }
            if(!zeroing && getPosition() > Constants.MAX_HINGE_EXTENSION) {
                stop(); move = false;
            }
        } else if(speed < 0) {
            if(contractedSwitchIsPressed()) {
                stop(); move = false;
            }
            if(!zeroing && getPosition() < Constants.MIN_HINGE_EXTENSION) {
                stop(); move = false;
            }
        }

        if(move) {
            telescopeMotor.set(speed);
        }
    }

    //boolean return says if it's at that position
    public boolean moveToPosition(double goal) {
        if(0 <= goal && goal < Constants.MIN_HINGE_EXTENSION) {
            if(Math.abs(getPosition() - goal) < 1) {
                return true;
            }
        }

        goal = Math.min(goal,Constants.MAX_HINGE_EXTENSION);
        goal = Math.max(goal,Constants.MIN_HINGE_EXTENSION);

        double speed = Math.abs(getPosition() - goal) * Constants.TELESCOPE_P; //proportional control
        speed = Math.min(speed, Constants.TELESCOPE_SPEED);
        
        if(Math.abs(getPosition() - goal) < 1) {
            stop();
            return true;
        } else if(getPosition() < goal) {
            move(speed);
            return false;
        } else {
            move(-speed);
            return false;
        }
    }

    public boolean retractFull() {
        return moveToPosition(Constants.MIN_HINGE_EXTENSION);
    }

    public void checkSafety() {
        if(getVelocity() < 0) {
            if(contractedSwitchIsPressed()) {
                stop();
            }
            if(getPosition() < Constants.MIN_HINGE_EXTENSION) {
                stop();
            }
        } else if(getVelocity() > 0) {
            if(extendedSwitchIsPressed()) {
                stop();
            }
            if(getPosition() > Constants.MAX_HINGE_EXTENSION) {
                stop();
            }
        }
    }

    public void stop() {
        telescopeMotor.set(0);
    }

    public double getPosition() {
        return telescopeEncoder.getPosition();
    }

    public double getVelocity() {
        return telescopeEncoder.getVelocity();
    }

    public boolean contractedSwitchIsPressed() {
        return contractedSwitch.get();
    }

    public boolean extendedSwitchIsPressed() {
        return extendedSwitch.get();
    }

    public void zero() {
        telescopeEncoder.setPosition(0);
    }
}