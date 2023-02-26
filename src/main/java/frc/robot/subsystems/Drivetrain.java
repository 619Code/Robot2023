package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.SparkErrorHelper;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;

public class Drivetrain extends SubsystemBase implements Loggable {
    DifferentialDrive drive;
    MotorControllerGroup leftMotors;
    MotorControllerGroup rightMotors;
    CANSparkMax leftLeader;
    CANSparkMax rightLeader;
    CANSparkMax leftFollower;
    CANSparkMax rightFollower;

    RelativeEncoder leftEncoder;
    RelativeEncoder rightEncoder;

    AHRS navx;
    DifferentialDriveKinematics kinematics; // converts rotation and velocity to wheel velocities
    DifferentialDriveOdometry odometry; // tracks robot position on the field

    public PIDController leftController;
    public PIDController rightController;

    double leftPos;
    double rightPos;

    double leftVel;
    double rightVel;

    double leftSetpoint;
    double rightSetpoint;

    double odomX;
    double odomY;

    double rotation;
    double pitch;

    private GenericEntry leftLeaderSpark;
    private GenericEntry rightLeaderSpark;
    private GenericEntry leftFollowerSpark;
    private GenericEntry rightFollowerSpark;


    public Drivetrain() {
        leftLeader = new CANSparkMax(Constants.LEFT_LEADER, MotorType.kBrushless);
        leftFollower = new CANSparkMax(Constants.LEFT_FOLLOWER_0, MotorType.kBrushless);
        CANSparkMax leftMotorArray[] = {
            leftLeader,
            leftFollower     
        }; 
        for(CANSparkMax spark : leftMotorArray) {
            spark.setIdleMode(IdleMode.kBrake);
            //spark.setSmartCurrentLimit(45);
        }

        rightLeader = new CANSparkMax(Constants.RIGHT_LEADER, MotorType.kBrushless);
        rightFollower = new CANSparkMax(Constants.RIGHT_FOLLOWER_0, MotorType.kBrushless);
        CANSparkMax rightMotorArray[] = {
            rightLeader,
            rightFollower    
        };
        for(CANSparkMax spark : rightMotorArray) {
            spark.setIdleMode(IdleMode.kBrake);
            //spark.setSmartCurrentLimit(45);
        }
        
        leftMotors = new MotorControllerGroup(leftMotorArray);
        rightMotors = new MotorControllerGroup(rightMotorArray);
        
        // invert right motor
        leftMotors.setInverted(true);

        // encoders
        leftEncoder = leftLeader.getEncoder();
        rightEncoder = rightLeader.getEncoder();
        //leftEncoder.setVelocityConversionFactor(Constants.RPM_TO_VELOCITY_CONVERSION_FACTOR);
        //rightEncoder.setVelocityConversionFactor(Constants.RPM_TO_VELOCITY_CONVERSION_FACTOR);

        // drive
        drive = new DifferentialDrive(leftMotors, rightMotors);
        drive.setSafetyEnabled(false);

        // sensors
        navx = new AHRS(SPI.Port.kMXP);
        
        int counter = 0; boolean timedOut = false;
        while(navx.isCalibrating()){  //wait until calibration finished
            if(counter > 4) {
                timedOut = true;
                break;
            }
            Timer.delay(0.5);
            counter++;
        }
        if(!timedOut) {
            resetGyro();
        }

        resetEncoders();
        kinematics = new DifferentialDriveKinematics(Constants.TRACK_WIDTH);

        odometry = new DifferentialDriveOdometry(
            new Rotation2d(-navx.getRotation2d().getRadians()), //navx.getRotation2d()
            getLeftPosition(),
            getRightPosition()
        );

        resetPIDs();
    }

    public void curve(double speed, double rotation) {
        drive.curvatureDrive(Constants.SPEED_ADJUST * speed, Constants.SPEED_ADJUST * -rotation, true);
    }

    @Override
    public void periodic() {
        odometry.update(new Rotation2d(-navx.getRotation2d().getRadians()), //navx.getRotation2d()
        getLeftPosition(),
        getRightPosition());
        
        Pose2d myPose = odometry.getPoseMeters();
        odomX = myPose.getX();
        odomY = myPose.getY();
        rotation = myPose.getRotation().getDegrees();

        leftPos = getLeftPosition();
        rightPos = getRightPosition();
        leftVel = getLeftVelocity();
        rightVel = getRightVelocity();
        leftSetpoint = leftController.getSetpoint();
        rightSetpoint = rightController.getSetpoint();

        pitch = navx.getPitch(); //will need adjustment

        leftLeaderSpark = Crashboard.toDashboard("Left Leader Spark", SparkErrorHelper.HasSensorError(leftLeader), Constants.SPARKS_TAB);
        rightLeaderSpark = Crashboard.toDashboard("Right Leader Spark", SparkErrorHelper.HasSensorError(rightLeader), Constants.SPARKS_TAB);
        leftFollowerSpark = Crashboard.toDashboard("Left Follower Spark", SparkErrorHelper.HasSensorError(leftFollower), Constants.SPARKS_TAB);
        rightFollowerSpark = Crashboard.toDashboard("Right Follower Spark", SparkErrorHelper.HasSensorError(rightFollower), Constants.SPARKS_TAB);
    }

    public void resetPIDs() {
        leftController = new PIDController(Constants.kPDriveVel, 0, 0);
        rightController = new PIDController(Constants.kPDriveVel, 0, 0);
    }

    public void resetGyro() {
        System.out.println("Resetting gyro");
        navx.reset();
    }

    public AHRS getNavx(){
        return navx;
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(getLeftVelocity(), getRightVelocity());
    }

    public void tankDriveVolts(double leftVolts, double rightVolts) {
        leftMotors.setVoltage(leftVolts);
        rightMotors.setVoltage(rightVolts);
        drive.feed();
    }

    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        resetGyro();
        odometry.resetPosition(navx.getRotation2d(), getLeftPosition(), getRightPosition(), pose);
    }

    public void resetEncoders() {
        leftEncoder.setPosition(0.0);
        rightEncoder.setPosition(0.0);
    }

    public double getLeftPosition() {
        return (leftEncoder.getPosition() * Constants.DRIVE_RATIO) * (Constants.WHEEL_DIAMETER * Math.PI);
    }

    public double getRightPosition() {
        return -(rightEncoder.getPosition() * Constants.DRIVE_RATIO) * (Constants.WHEEL_DIAMETER * Math.PI);
    }

    public double getLeftVelocity() {
        return (leftEncoder.getVelocity() * Constants.DRIVE_RATIO) * (Constants.WHEEL_DIAMETER * Math.PI) * (1/60.0);
    }

    public double getRightVelocity() {
        return -(rightEncoder.getVelocity() * Constants.DRIVE_RATIO) * (Constants.WHEEL_DIAMETER * Math.PI) * (1/60.0);
    }
}
