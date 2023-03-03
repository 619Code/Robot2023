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
    private CANSparkMax hingeLeaderMotor;
    private CANSparkMax hingeFollowerMotor;
    private RelativeEncoder hingeEncoder;

    //private DigitalInput highSwitch;
    private DigitalInput magnetSwitch; 

    public boolean lastMovingDown;

    private GenericEntry hingeSpark;
    private GenericEntry hingeSwitch;

    public Hinge() {
        hingeLeaderMotor = new CANSparkMax(Constants.HINGE_LEADER_MOTOR, MotorType.kBrushless);
        hingeFollowerMotor = new CANSparkMax(Constants.HINGE_FOLLOWER_MOTOR, MotorType.kBrushless);

        hingeLeaderMotor.restoreFactoryDefaults();
        hingeLeaderMotor.setIdleMode(IdleMode.kBrake);
        hingeLeaderMotor.setSmartCurrentLimit(40);
        hingeLeaderMotor.setInverted(true);

        hingeFollowerMotor.restoreFactoryDefaults();
        hingeFollowerMotor.setIdleMode(IdleMode.kBrake);
        hingeFollowerMotor.setSmartCurrentLimit(40);
        hingeLeaderMotor.setInverted(true);

        hingeEncoder = hingeLeaderMotor.getEncoder();
        hingeEncoder.setPosition(Constants.HINGE_START);

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
        Crashboard.toDashboard("Hinge Amps", hingeLeaderMotor.getOutputCurrent(), Constants.ARM_TAB);
        hingeSpark = Crashboard.toDashboard("Hinge Spark", SparkErrorHelper.HasSensorError(hingeLeaderMotor), Constants.SPARKS_TAB);
        hingeSwitch = Crashboard.toDashboard("Hinge Switch Triggd?", magnetSwitch.get(), Constants.STATUS_TAB);

        Crashboard.toDashboard("Hinge Position", hingeEncoder.getPosition(), Constants.ARM_TAB);
    }

    public void move(double speed) {
        if(speed > 0 && switchIsPressed() && !lastMovingDown) {
            stop();
        } else if(speed < 0 && switchIsPressed() && lastMovingDown) {
            stop();
        } else {
            hingeLeaderMotor.set(speed);
            hingeFollowerMotor.set(speed);
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
        hingeLeaderMotor.set(0);
        hingeFollowerMotor.set(0);
    }

    public double getPosition() {
        return hingeEncoder.getPosition();
    }

    public boolean switchIsPressed()
    {
        //return magnetSwitch.get();
        return false; //UNDO
    }

    public void zero() {
        hingeEncoder.setPosition(0);
    }
}
