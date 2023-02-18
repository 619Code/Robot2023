package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Hinge extends SubsystemBase {
    private CANSparkMax hingeMotor;
    private RelativeEncoder hingeEncoder;

    private DigitalInput lowSwitch;
    private DigitalInput highSwitch;

    public Hinge() {
        hingeMotor = new CANSparkMax(Constants.HINGE_MOTOR, MotorType.kBrushless);
        hingeMotor.restoreFactoryDefaults();
        hingeMotor.setIdleMode(IdleMode.kBrake);

        hingeEncoder = hingeMotor.getEncoder();
        hingeEncoder.setPosition(0); //zero positions

        lowSwitch = new DigitalInput(Constants.HINGE_LOW_SWITCH);
        highSwitch = new DigitalInput(Constants.HINGE_HIGH_SWITCH);
    }

    @Override
    public void periodic() {
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
}
