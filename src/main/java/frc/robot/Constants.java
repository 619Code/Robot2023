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

    //Grabber CANS
    public static final int GRABBER_MOTOR = 12;

    //Arm CANS
    public static final int HINGE_LEADER_MOTOR = 11;
    public static final int HINGE_FOLLOWER_MOTOR = 15;
    public static final int TELESCOPE_MOTOR = 10;

    //Sensor ports
    public static final int GRABBER_SWITCH = 1;
    public static final int HINGE_SWITCH = 6;
    //public static final int HINGE_LOW_SWITCH = -1;
    //public static final int HINGE_HIGH_SWITCH = -1;
    public static final int TELESCOPE_CONTRACTED_SWITCH = 3;
    public static final int TELESCOPE_EXTENDED_SWITCH = 5;
    public static final int INTAKE_LEFT_SWITCH = 2;
    public static final int INTAKE_RIGHT_SWITCH = 7;

    //Drive constants
    public static final double WHEEL_DIAMETER = Units.inchesToMeters(5.827); //meters
    public static final int NEO_LIMIT = 45; //amps
    public static final double SPEED_ADJUST = 0.4; //how much to adjust speed of drive
    public static final double DRIVE_RATIO = (13.0/60.0) * (18.0/34.0); // gear ratio
    public static final double TRACK_WIDTH = Units.inchesToMeters(23); //REMEASURE
    public static final String SHUFFLEBOARD_DRIVE_TAB_NAME = "Drive";
    public static final double POSITION_CONVERSION_FACTOR = Constants.DRIVE_RATIO * WHEEL_DIAMETER * Math.PI; //conversion factor for position
    public static final double VELOCITY_CONVERSION_FACTOR = Constants.DRIVE_RATIO * WHEEL_DIAMETER * Math.PI / 60.0; //conversion factor for velocity

    //Lineup constants
    public static final double ROTATION_P = 0.1;
    public static final double ROTATION_MAX = 0.3;
    public static final double APPROACH_SPEED = 0.4;

    //Controller constants
    public static final double JOYSTICK_DEADZONE = 0.075;
    
    // Kinematics Constants
    public static final double ksVolts = 0.34791;
    public static final double kvVoltSecondsPerMeter = 0.27259;
    public static final double kaVoltSecondsSquaredPerMeter = 0.060902;

    public static final double kPDriveVel = 0.38794;
    public static final DifferentialDriveKinematics kDriveKinematics =
        new DifferentialDriveKinematics(TRACK_WIDTH);

    public static final double kMaxSpeedMetersPerSecond = 0.5;
    public static final double kMaxAccelerationMetersPerSecondSquared = 1;

    public static final double kRamseteB = 2;
    public static final double kRamseteZeta = 0.7;

    //Grabber constants
    public static final double MAX_GRABBER_SPEED = 0.3; //0.6

	// public static final double CUBE_POSITION = 21.0;
	// public static final double CONE_POSITION = 60.0;
	// public static final double ZERO_POSITION = 70.0;

    // Temp new grabber constants, had to cut grabber arm shorter
    public static final double CUBE_POSITION = 5.0;
	public static final double CONE_POSITION = 55.0;
	public static final double GRABBER_ZERO_POSITION = 63.0;

    //Hinge constants
    public static final double MINIMUM_POSITION = 0.0;
    public static final double MAXIMUM_POSITION = 80.0;

    public static final double HINGE_SPEED = 0.2; //0.4
    public static final double HINGE_ZERO_SPEED = 0.04;
    public static final double HINGE_P = 0.08;

    //Telescope constants
    public static final double TELESCOPE_CONTRACTED_SWITCH_POSITION = 0.0;
    public static final double TELESCOPE_EXTENDED_SWITCH_POSITION = 78;
    public static final double EXTENSION_TOLERANCE = 5.0;
    public static final double MINIMUM_EXTENSION = TELESCOPE_CONTRACTED_SWITCH_POSITION + EXTENSION_TOLERANCE;
    public static final double MAXIMUM_EXTENSION = TELESCOPE_EXTENDED_SWITCH_POSITION - EXTENSION_TOLERANCE;

    public static final double TELESCOPE_SPEED = 0.4; //0.8
    public static final double TELESCOPE_ZERO_SPEED = 0.08;
    public static final double TELESCOPE_P = 0.04;

    //Arm cases
    public static final double START_POSITION_HINGE = 25.0;
    public static final double START_POSITION_TELESCOPE = 5.0;

    public static final double PICKUP_LOW_POSITION_HINGE = 0.0;
    public static final double PICKUP_LOW_POSITION_TELESCOPE = 5.0;

    public static final double PICKUP_HIGH_POSITION_HINGE = 19.0;
    public static final double PICKUP_HIGH_POSITION_TELESCOPE = 20.0;

    public static final double GRID_MID_POSITION_HINGE = 16.0;
    public static final double GRID_MID_POSITION_TELESCOPE = 61.0;

    //retune this value
    public static final double GRID_HIGH_POSITION_HINGE = 51.0;
    public static final double GRID_HIGH_POSITION_TELESCOPE = 62.0;

    //Intake constants
    public static final int LEFT_ARM = 14; 
    public static final int RIGHT_ARM = 9;
    public static final int LEFT_WHEEL = 13;
    public static final int RIGHT_WHEEL = 8;
    public static final double INTAKE_RETRACTED_POSITION = 0;
    public static final double LEFT_INTAKE_DEPLOYED_POSITION = 39;
    public static final double RIGHT_INTAKE_DEPLOYED_POSITION = 41;    
    public static final int LEFT_LIMIT_SWITCH_ID = -1;
    public static final int RIGHT_LIMIT_SWITCH_ID = -1;

    //Limelight constants
    public static final int CENTER_PIPELINE = 0;
    public static final int RIGHT_PIPELINE = 1;
    public static final int LEFT_PIPELINE = 2;
    public static final int RRT_PIPELINE = 3;
    public static final int CAMERA_PIPELINE = 4;

    //LED constants
    public static final int LED_PWM_PORT = 1;
    public static final int LED_STRIP_LENGTH = 100;

    //Auto Constants
    public static final double AUTO_DRIVE_SPEED = 0.4;
    public static final double AUTO_DRIVE_DISTANCE = 1;

    public static final double GRABBER_START = CONE_POSITION;
    public static final double HINGE_START = 0; //UNDO
    public static final double TELESCOPE_START = START_POSITION_TELESCOPE;

    // Dashboard Tabs
    public static String DRIVE_TAB = "Drive";
    public static String GRABBER_TAB = "Grab";
    public static String LIMELIGHT_TAB = "Limelight";
    public static String ARM_TAB = "Arm";
    public static String MOTORCONTROLLERS_TAB = "Motor Controllers";
    public static String INTAKE_TAB = "Intake";
    public static String AUTOS_TAB = "Autos";
    public static String COMPETITON_TAB = "Competition";
    public static String SPARKS_TAB = "Sparks";
    public static String STATUS_TAB = "Status";
}