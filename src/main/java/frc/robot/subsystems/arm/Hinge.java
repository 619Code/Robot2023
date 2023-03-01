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
    private CANSparkMax hingeMotor;
    private RelativeEncoder hingeEncoder;

    //private DigitalInput highSwitch;
    private DigitalInput magnetSwitch; 

    public boolean zeroed;
    public boolean lastMovingDown;

    private GenericEntry hingeSpark;
    private GenericEntry hingeSwtich;

    public Hinge() {
        hingeMotor = new CANSparkMax(Constants.HINGE_MOTOR, MotorType.kBrushless);
        hingeMotor.restoreFactoryDefaults();
        hingeMotor.setIdleMode(IdleMode.kBrake);
        hingeMotor.setSmartCurrentLimit(40);
        hingeMotor.setInverted(true);

        hingeEncoder = hingeMotor.getEncoder();
        hingeEncoder.setPosition(Constants.HINGE_START);

        zeroed = false;
        lastMovingDown = true;

        magnetSwitch = new DigitalInput(Constants.HINGE_SWITCH);
    }

    /* (non-Javadoc)
     * @see edu.wpi.first.wpilibj2.command.Subsystem#periodic()
     */
    @Override
    public void periodic() {
        Crashboard.toDashboard("Hinge Position", getPosition(), Constants.ARM_TAB);
        Crashboard.toDashboard("Hinge Position", getPosition(), Constants.COMPETITON_TAB);
        Crashboard.toDashboard("Hinge Amps", hingeMotor.getOutputCurrent(), Constants.ARM_TAB);
        hingeSpark = Crashboard.toDashboard("Hinge Spark", SparkErrorHelper.HasSensorError(hingeMotor), Constants.SPARKS_TAB);
        hingeSwtich = Crashboard.toDashboard("Hinge Switch Triggd?", magnetSwitch.get(), Constants.STATUS_TAB);

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
        if(!movable()) {
            stop();
            return false;
        }

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

    public boolean movable() {
        return States.inAuto || zeroed;
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

    public void zero() {
        hingeEncoder.setPosition(0);
    }
}
