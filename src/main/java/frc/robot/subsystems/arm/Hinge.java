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

    private DigitalInput lowSwitch;
    private DigitalInput highSwitch;

    public Hinge() {
        hingeMotor = new CANSparkMax(Constants.HINGE_MOTOR, MotorType.kBrushless);
        hingeMotor.restoreFactoryDefaults();
        hingeMotor.setIdleMode(IdleMode.kBrake);
        hingeMotor.setSmartCurrentLimit(40);

        hingeEncoder = hingeMotor.getEncoder();
        zero(); //zero positions

        //lowSwitch = new DigitalInput(Constants.HINGE_LOW_SWITCH);
        //highSwitch = new DigitalInput(Constants.HINGE_HIGH_SWITCH);
    }

    @Override
    public void periodic() {
        Crashboard.toDashboard("Amps", hingeMotor.getOutputCurrent());
    }

    public void move(double speed) {
        hingeMotor.set(speed);
    }

    public void stop() {
        hingeMotor.set(0);
    }

    public double getPosition() {
        return hingeEncoder.getPosition();
    }

    public boolean lowSwitchIsPressed() {
        return lowSwitch.get();
    }

    public boolean highSwitchIsPressed() {
        return highSwitch.get();
    }

    public void zero() {
        hingeEncoder.setPosition(0);
    }
}
