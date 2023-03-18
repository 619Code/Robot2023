package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Wrist extends SubsystemBase {
    private CANSparkMax wristMotor;
    private RelativeEncoder wristEncoder;

    private DigitalInput lowSwitch;
    private DigitalInput highSwitch;

    public Wrist() {
        wristMotor = new CANSparkMax(Constants.WRIST_MOTOR, MotorType.kBrushless);
        wristMotor.restoreFactoryDefaults();
        wristMotor.setIdleMode(IdleMode.kBrake);
        wristMotor.setSmartCurrentLimit(40);

        wristEncoder = wristMotor.getEncoder();
        wristEncoder.setPosition(Constants.WRIST_START);

        lowSwitch = new DigitalInput(Constants.WRIST_SWITCH_LOW);
        highSwitch = new DigitalInput(Constants.WRIST_SWITCH_HIGH);
    }

    public void move(double speed) {
        move(speed, false);
    }

    public void move(double speed, boolean zeroing) {
        boolean move = true;

        if(speed > 0) {
            if(highSwitchIsPressed()) {
                stop(); move = false;
            }
            if(!zeroing && getPosition() > Constants.MAX_WRIST_POSITION) {
                stop(); move = false;
            }
        } else if(speed < 0) {
            if(lowSwitchIsPressed()) {
                stop(); move = false;
            }
            if(!zeroing && getPosition() < Constants.MIN_WRIST_POSITION) {
                stop(); move = false;
            }
        }

        if(move) {
            wristMotor.set(speed);
        }
    }

    //boolean return says if it's at that position
    public boolean moveToPosition(double goal) {
        goal = Math.min(goal,Constants.MAX_WRIST_POSITION);
        goal = Math.max(goal,Constants.MIN_WRIST_POSITION);

        double speed = Math.abs(getPosition() - goal) * Constants.WRIST_P; //proportional control
        speed = Math.min(speed, Constants.WRIST_SPEED);

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
        wristMotor.set(0);
    }

    public double getPosition() {
        return wristEncoder.getPosition();
    }

    public boolean highSwitchIsPressed() {
        return highSwitch.get();
    }

    public boolean lowSwitchIsPressed() {
        return lowSwitch.get();
    }

    public void zero() {
        wristEncoder.setPosition(0);
    }
}
