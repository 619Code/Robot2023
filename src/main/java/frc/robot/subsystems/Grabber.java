package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.SparkErrorHelper;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

public class Grabber extends SubsystemBase {
    private CANSparkMax grabberLeaderMotor;
    private CANSparkMax grabberFollowerMotor;
    private DigitalInput proximitySensor;

    boolean checkForCube = false;

    private GenericEntry grabberSpark;

    public Grabber() {
        grabberLeaderMotor = new CANSparkMax(Constants.GRABBER_MOTOR_LEADER, MotorType.kBrushless);
        grabberLeaderMotor.restoreFactoryDefaults();
        grabberLeaderMotor.setSmartCurrentLimit(35);
        grabberLeaderMotor.setIdleMode(IdleMode.kBrake);
        grabberLeaderMotor.setInverted(true);

        grabberFollowerMotor = new CANSparkMax(Constants.GRABBER_MOTOR_FOLLOWER, MotorType.kBrushless);
        grabberFollowerMotor.restoreFactoryDefaults();
        grabberFollowerMotor.setSmartCurrentLimit(35);
        grabberFollowerMotor.setIdleMode(IdleMode.kBrake);

        grabberFollowerMotor.follow(grabberLeaderMotor, true);

        proximitySensor = new DigitalInput(Constants.GRABBER_PROXIMITY_SENSOR);
    }

    private void UpdateGlobalStates(boolean cubeSensed, boolean coneSensed) 
    {
        if (cubeSensed)
            States.hasCube = true;
        else
            States.hasCube = false;


        if (coneSensed)
            States.hasCone = true;
        else
            States.hasCone = false;
    }

    public void startSensingCube() {
        checkForCube = true;
    }

    public void stopSensingCube() {
        checkForCube = false;
    }

    @Override
    public void periodic() {

        boolean cubeSensedValue = false;
        /*if (checkForCube)
        {
            cubeSensedValue = cubeSensed();
        }*/

        var coneSensedValue = coneSensed();

        this.UpdateGlobalStates(cubeSensedValue, coneSensedValue);

        /*grabberSpark = Crashboard.toDashboard("Grabber Spark", SparkErrorHelper.HasSensorError(grabberLeaderMotor), Constants.SPARKS_TAB);
        Crashboard.toDashboard("Cube Sensed", cubeSensed(), Constants.GRABBER_TAB);
        Crashboard.toDashboard("Cone Sensed", coneSensed(), Constants.GRABBER_TAB);

        Crashboard.toDashboard("Leader Amps", grabberLeaderMotor.getAppliedOutput(), Constants.GRABBER_TAB);
        Crashboard.toDashboard("Follower Amps", grabberFollowerMotor.getAppliedOutput(), Constants.GRABBER_TAB);*/
    }

    public void spin(double speed) {
        grabberLeaderMotor.set(speed);
    }

    public boolean coneSensed() {
        return !proximitySensor.get();
    }

    public void stop() {
        grabberLeaderMotor.set(0);
    }
}
