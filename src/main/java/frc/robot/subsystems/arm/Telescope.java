package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.helpers.Crashboard;

public class Telescope extends SubsystemBase {
    private CANSparkMax telescopeMotor;
    private RelativeEncoder telescopeEncoder;

    private DigitalInput contractedSwitch;
    private DigitalInput extendedSwitch;

    public boolean zeroed;

    public Telescope() {
        telescopeMotor = new CANSparkMax(Constants.TELESCOPE_MOTOR, MotorType.kBrushless);
        telescopeMotor.restoreFactoryDefaults();
        telescopeMotor.setIdleMode(IdleMode.kBrake);
        telescopeMotor.setSmartCurrentLimit(40);
        telescopeMotor.setInverted(true);

        telescopeEncoder = telescopeMotor.getEncoder();
        zero();

        contractedSwitch = new DigitalInput(Constants.TELESCOPE_CONTRACTED_SWITCH);
        extendedSwitch = new DigitalInput(Constants.TELESCOPE_EXTENDED_SWITCH);

        zeroed = false;
    }

    @Override
    public void periodic() {
        checkSafety();

        //Crashboard.toDashboard("Contracted Switch", contractedSwitchIsPressed());
        //Crashboard.toDashboard("Extended Switch", extendedSwitchIsPressed());
        Crashboard.toDashboard("Telescope Position", getPosition(), Constants.ArmTab);
        Crashboard.toDashboard("Zeroed", zeroed, Constants.ArmTab);
    }

    public void move(double speed) {
        boolean move = true;
        if(speed > 0) {
            if(extendedSwitchIsPressed()) {
                stop(); move = false;
            }
            if(zeroed && getPosition() > Constants.MAXIMUM_EXTENSION) {
                stop(); move = false;
            }
        } else if(speed < 0) {
            if(contractedSwitchIsPressed()) {
                stop(); move = false;
            }
            if(zeroed && getPosition() < Constants.MINIMUM_EXTENSION) {
                stop(); move = false;
            }
        }

        if(move) {
            telescopeMotor.set(speed);
        }
    }

    //boolean return says if it's at that position
    public boolean moveToPosition(double goal) {
        if(!zeroed) {
            stop();
            return false;
        }

        goal = Math.min(goal,Constants.MAXIMUM_EXTENSION);
        goal = Math.max(goal,Constants.MINIMUM_EXTENSION);

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
        return moveToPosition(Constants.MINIMUM_EXTENSION);
    }

    public void checkSafety() {
        if(getVelocity() < 0) {
            if(contractedSwitchIsPressed()) {
                stop();
            }
            if(zeroed && getPosition() < Constants.MINIMUM_EXTENSION) {
                stop();
            }
        } else if(getVelocity() > 0) {
            if(extendedSwitchIsPressed()) {
                stop();
            }
            if(zeroed && getPosition() > Constants.MAXIMUM_EXTENSION) {
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