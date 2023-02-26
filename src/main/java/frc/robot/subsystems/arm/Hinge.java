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

public class Hinge extends SubsystemBase {
    private CANSparkMax hingeMotor;
    private RelativeEncoder hingeEncoder;

    //private DigitalInput lowSwitch;
    //private DigitalInput highSwitch;
    private DigitalInput magnetSwitch; 

    public boolean zeroed;
    public boolean lastMovingDown;

    public Hinge() {
        hingeMotor = new CANSparkMax(Constants.HINGE_MOTOR, MotorType.kBrushless);
        hingeMotor.restoreFactoryDefaults();
        hingeMotor.setIdleMode(IdleMode.kBrake);
        hingeMotor.setSmartCurrentLimit(40);
        hingeMotor.setInverted(true);

        hingeEncoder = hingeMotor.getEncoder();
        hingeEncoder.setPosition(40);
        zeroed = false; //undo
        lastMovingDown = false;
        //zero(); //zero positions

        magnetSwitch = new DigitalInput(Constants.HINGE_SWITCH);
        //lowSwitch = new DigitalInput(Constants.HINGE_LOW_SWITCH);
       // highSwitch = new DigitalInput(Constants.HINGE_HIGH_SWITCH);
    }

    /* (non-Javadoc)
     * @see edu.wpi.first.wpilibj2.command.Subsystem#periodic()
     */
    @Override
    public void periodic() {
        Crashboard.toDashboard("Hinge Position", getPosition(), Constants.ArmTab);
        Crashboard.toDashboard("Hinge Amps", hingeMotor.getOutputCurrent(), Constants.ArmTab);
    }

    public void move(double speed) {
        if(speed > 0 && switchIsPressed() && !lastMovingDown) {
            stop();
        } else if(speed < 0 && switchIsPressed() && lastMovingDown) {
            stop();
        } else {
            hingeMotor.set(speed);
        }
        if(speed > 0)
        {
            lastMovingDown = false;
        } else
        {
            lastMovingDown = true;
        }
    }

    //boolean return says if it's at that position
    public boolean moveToPosition(double goal) {
        /*if(!zeroed) {
            stop();
            return false;
        }*/

        goal = Math.min(goal,Constants.MAXIMUM_POSITION);
        goal = Math.max(goal,Constants.MINIMUM_POSITION);

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

    public boolean switchIsPressed()
    {
        return magnetSwitch.get();
    }
    /** 
    public boolean lowSwitchIsPressed() {
        return lowSwitch.get();
    }

    public boolean highSwitchIsPressed() {
        return highSwitch.get();
    }
    */

    public void zero() {
        hingeEncoder.setPosition(0);
    }
}
