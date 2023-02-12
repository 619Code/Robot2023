package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public final class Constants {
    //Drive CANs
    public static final int LEFT_LEADER = 1;
    public static final int RIGHT_LEADER = 3;
    public static final int LEFT_FOLLOWER_0 = 2;
    public static final int RIGHT_FOLLOWER_0 = 4;

    //Drive constants
    public static final double WHEEL_DIAMETER = Units.inchesToMeters(5.827); //meters
    public static final int NEO_LIMIT = 45; //amps
    public static final double SPEED_ADJUST = 0.4; //how much to adjust speed of drive //0.5 //UNDO
    public static final double DRIVE_RATIO = (13.0/60.0) * (18.0/34.0); // gear ratio
    public static final double TRACK_WIDTH = Units.inchesToMeters(23); //REMEASURE
    public static final String SHUFFLEBOARD_DRIVE_TAB_NAME = "Drive";
    public static final double POSITION_CONVERSION_FACTOR = Constants.DRIVE_RATIO * WHEEL_DIAMETER * Math.PI; //conversion factor for position
    public static final double VELOCITY_CONVERSION_FACTOR = Constants.DRIVE_RATIO * WHEEL_DIAMETER * Math.PI / 60.0; //conversion factor for velocity

    //Drive solenoids
    public static final int PCM_CAN_ID = 0;
    public static final int DRIVE_SOLENOID_FORWARD = 0;
    public static final int DRIVE_SOLENOID_BACK = 7;

    //Controller constants
    public static final double JOYSTICK_DEADZONE = 0.075;

    // Kinematics/Auto Constants
    public static final double ksVolts = 0.34791;
    public static final double kvVoltSecondsPerMeter = 0.27259;
    public static final double kaVoltSecondsSquaredPerMeter = 0.060902;

    public static final double kPDriveVel = 0.38794;
    public static final DifferentialDriveKinematics kDriveKinematics =
        new DifferentialDriveKinematics(TRACK_WIDTH);

    public static final double kMaxSpeedMetersPerSecond = 0.5;
    public static final double kMaxAccelerationMetersPerSecondSquared = 1;

    public static final double kRamseteB = 2;
    //public static final double kRamseteZeta = 0.7;
}