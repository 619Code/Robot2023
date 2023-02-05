package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {

    DifferentialDrive drive;
    MotorControllerGroup leftMotors;
    MotorControllerGroup rightMotors;
    CANSparkMax leftLeader;
    CANSparkMax rightLeader;

    RelativeEncoder leftEncoder;
    RelativeEncoder rightEncoder;

    AHRS navx;
    DifferentialDriveKinematics kinematics; // converts rotation and velocity to wheel velocities
    DifferentialDriveOdometry odometry; // tracks robot position on the field

    public Drivetrain() {
        leftLeader = new CANSparkMax(Constants.LEFT_LEADER, MotorType.kBrushless);
        CANSparkMax leftMotorArray[] = {
            leftLeader,
            new CANSparkMax(Constants.LEFT_FOLLOWER_0, MotorType.kBrushless)
        }; 
        for(CANSparkMax spark : leftMotorArray) {
            spark.setIdleMode(IdleMode.kCoast);
            //spark.setSmartCurrentLimit(45);
        }
        leftLeader.setIdleMode(IdleMode.kBrake); //experimental

        rightLeader = new CANSparkMax(Constants.RIGHT_LEADER, MotorType.kBrushless);
        CANSparkMax rightMotorArray[] = {
            rightLeader,
            new CANSparkMax(Constants.RIGHT_FOLLOWER_0, MotorType.kBrushless)
        };
        for(CANSparkMax spark : rightMotorArray) {
            spark.setIdleMode(IdleMode.kCoast);
            //spark.setSmartCurrentLimit(45);
        }
        rightLeader.setIdleMode(IdleMode.kBrake); //experimental
        
        leftMotors = new MotorControllerGroup(leftMotorArray);
        rightMotors = new MotorControllerGroup(rightMotorArray);
        
        // invert right motor
        rightMotors.setInverted(true);

        // encoders
        leftEncoder = leftLeader.getEncoder();
        rightEncoder = rightLeader.getEncoder();
        leftEncoder.setVelocityConversionFactor(Constants.RPM_TO_VELOCITY_CONVERSION_FACTOR);
        rightEncoder.setVelocityConversionFactor(Constants.RPM_TO_VELOCITY_CONVERSION_FACTOR);

        // drive
        drive = new DifferentialDrive(leftMotors, rightMotors);
        drive.setSafetyEnabled(false);
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        double leftVelocity = (leftEncoder.getVelocity() * Constants.DRIVE_RATIO_HIGH) * (Constants.WHEEL_DIAMETER * Math.PI) * (1/60);
        double rightVelocity = (rightEncoder.getVelocity() * Constants.DRIVE_RATIO_HIGH) * (Constants.WHEEL_DIAMETER * Math.PI) * (1/60);
        return new DifferentialDriveWheelSpeeds(leftVelocity, rightVelocity);
    }

    public void tankDriveVolts(double leftVolts, double rightVolts) {
        leftMotors.setVoltage(leftVolts);
        rightMotors.setVoltage(rightVolts);
        //System.out.println("Left volts: " + leftVolts);
        //System.out.println("Right volts: "+ rightVolts);
        drive.feed();
    }

    public void curve(double speed, double rotation, boolean isLowGear) {
        drive.curvatureDrive(Constants.SPEED_ADJUST * speed, Constants.SPEED_ADJUST * -rotation, true);
    }

}
