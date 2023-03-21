package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.helpers.Crashboard;

public class Wrist extends SubsystemBase {
    private CANSparkMax wristMotor;
    private DutyCycleEncoder wristAbsoluteEncoder;
    private RelativeEncoder wristRelativeEncoder;

    public Wrist() {
        wristMotor = new CANSparkMax(Constants.WRIST_MOTOR, MotorType.kBrushless);
        wristMotor.restoreFactoryDefaults();
        wristMotor.setIdleMode(IdleMode.kBrake);
        wristMotor.setSmartCurrentLimit(40);

        wristAbsoluteEncoder = new DutyCycleEncoder(Constants.WRIST_ABSOLUTE_ENCODER);
        wristRelativeEncoder = wristMotor.getEncoder();
        zero();
    }

    public void periodic() {
        Crashboard.toDashboard("Wrist Absolute Position", getAbsolutePosition(), Constants.ARM_TAB);
        Crashboard.toDashboard("Wrist Relative Position", getRelativePosition(), Constants.ARM_TAB);
    }

    public void move(double speed) {
        move(speed, false);
    }

    public void move(double speed, boolean zeroing) {
        boolean move = true;

        if(speed > 0) {
            if(!zeroing && getRelativePosition() > Constants.MAX_WRIST_POSITION) {
                stop(); move = false;
            }
        } else if(speed < 0) {
            if(!zeroing && getRelativePosition() < Constants.MIN_WRIST_POSITION) {
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

        double speed = Math.abs(getRelativePosition() - goal) * Constants.WRIST_P; //proportional control
        speed = Math.min(speed, Constants.WRIST_SPEED);

        if(getRelativePosition() < goal) {
            move(speed);
        } else {
            move(-speed);
        }

        if(Math.abs(getRelativePosition() - goal) < 1) {
            return true;
        } else {
            return false;
        }
    }

    public void stop() {
        wristMotor.set(0);
    }

    public void zero() {
        wristRelativeEncoder.setPosition(0);
    }

    public double getAbsolutePosition() {
        return wristAbsoluteEncoder.getAbsolutePosition() - Constants.POSITION_OFFSET;
    }

    public double getRelativePosition() {
        return wristRelativeEncoder.getPosition();
    }
}
