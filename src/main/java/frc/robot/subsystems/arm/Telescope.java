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

        telescopeEncoder = telescopeMotor.getEncoder();
        telescopeEncoder.setPosition(0); //zero positions

        contractedSwitch = new DigitalInput(Constants.TELESCOPE_CONTRACTED_SWITCH);
        extendedSwitch = new DigitalInput(Constants.TELESCOPE_EXTENDED_SWITCH);
    }

    @Override
    public void periodic() {
    }

    public void move(double speed) {
        if(speed > 0 && extendedSwitchIsPressed()) {
            stop();
        } else if(speed < 0 && contractedSwitchIsPressed()) {
            stop();
        } else {
            telescopeMotor.set(speed);
        }
    }

    public void stop() {
        telescopeMotor.set(0);
    }

    public double getPosition() {
        return telescopeEncoder.getPosition();
    }

    public boolean contractedSwitchIsPressed() {
        return contractedSwitch.get();
    }

    public boolean extendedSwitchIsPressed() {
        return extendedSwitch.get();
    }
}