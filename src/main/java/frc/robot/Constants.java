package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;
import frc.robot.helpers.enums.ArmPositionSide;

public final class Constants {
    //Drive CANs
    public static final int LEFT_LEADER = 1;
    public static final int RIGHT_LEADER = 3;
    public static final int LEFT_FOLLOWER_0 = 2;
    public static final int RIGHT_FOLLOWER_0 = 4;

    //Grabber CANs
    public static final int GRABBER_MOTOR_LEADER = 12;
    public static final int GRABBER_MOTOR_FOLLOWER = -1; //change

    //Arm CANs
    public static final int HINGE_MOTOR = 11;
    public static final int TELESCOPE_MOTOR = 10;
    public static final int WRIST_MOTOR = -1; //change

    //Intake CANs
    public static final int LEFT_ARM_MOTOR = 14; 
    public static final int RIGHT_ARM_MOTOR = 9;
    public static final int LEFT_WHEEL_MOTOR = 13;
    public static final int RIGHT_WHEEL_MOTOR = 8;

    //Sensor ports
    public static final int HINGE_SWITCH = 6;
    public static final int WRIST_SWITCH = -1;
    public static final int TELESCOPE_CONTRACTED_SWITCH = 3;
    public static final int TELESCOPE_EXTENDED_SWITCH = 5;
    public static final int INTAKE_LEFT_SWITCH = 2;
    public static final int INTAKE_RIGHT_SWITCH = 7;

    public static final int GRABBER_PROXIMITY_SENSOR = -1; //change

    //Drive constants
    public static final double WHEEL_DIAMETER = Units.inchesToMeters(5.827); //meters
    public static final int NEO_LIMIT = 45; //amps

    public static final double DRIVE_SPEED = 0.8;
    public static final double DRIVE_ROTATION = 0.3;

    public static final double SLOW_MODE_SPEED = 0.6 * 0.5;
    public static final double SLOW_MODE_ROTATION = 0.3 * 0.5;

    public static final double SUPER_SLOW_MODE_SPEED = 0.6 * 0.2;
    public static final double SUPER_SLOW_MODE_ROTATION = 0.3 *0.2;

    public static final double DRIVE_SLEW_RATE_LIMIT = 1.5;
    public static final double DRIVE_SLEW_RATE_LIMIT_HIGH = 2;
    
    public static final double DRIVE_RATIO = (13.0/60.0) * (18.0/34.0); // gear ratio
    public static final double TRACK_WIDTH = Units.inchesToMeters(23); //REMEASURE
    public static final String SHUFFLEBOARD_DRIVE_TAB_NAME = "Drive";
    public static final double POSITION_CONVERSION_FACTOR = Constants.DRIVE_RATIO * WHEEL_DIAMETER * Math.PI; //conversion factor for position
    public static final double VELOCITY_CONVERSION_FACTOR = Constants.DRIVE_RATIO * WHEEL_DIAMETER * Math.PI / 60.0; //conversion factor for velocity

    //Lineup constants
    public static final double ROTATION_P = 0.06;
    public static final double ROTATION_MAX = 0.2;
    public static final double APPROACH_SPEED = 0.25;

    //Controller constants
    public static final double JOYSTICK_DEADZONE = 0.05;
    
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
    public static final double CUBE_TIMER = 0;
    public static final double CONE_TIMER = 0;

    public static final double GRAB_SPEED = 0.4;
    public static final double RELEASE_SPEED = -0.4;

    //Hinge constants
    public static final double MIN_HINGE_POSITION = 1.0;
    public static final double MAX_HINGE_POSITION = 57.0;

    public static final double HINGE_SPEED = 0.4; //0.4
    public static final double HINGE_ZERO_SPEED = 0.1;
    public static final double HINGE_P = 0.12;

    //Telescope constants
    public static final double TELESCOPE_CONTRACTED_SWITCH_POSITION = 0.0;
    public static final double TELESCOPE_EXTENDED_SWITCH_POSITION = 78;
    public static final double EXTENSION_TOLERANCE = 5.0;
    public static final double MIN_HINGE_EXTENSION = TELESCOPE_CONTRACTED_SWITCH_POSITION + EXTENSION_TOLERANCE;
    public static final double MAX_HINGE_EXTENSION = TELESCOPE_EXTENDED_SWITCH_POSITION - EXTENSION_TOLERANCE;

    public static final double TELESCOPE_SPEED = 0.9; //0.8
    public static final double TELESCOPE_ZERO_SPEED = 0.2;
    public static final double TELESCOPE_P = 0.04;

    //Wrist constants
    public static final double MIN_WRIST_POSITION = 0;
    public static final double MAX_WRIST_POSITION = 100;

    public static final double WRIST_SPEED = 0.5;
    public static final double WRIST_ZERO_SPEED = 0.1;
    public static final double WRIST_P = 0.04;

    //Arm cases
    public static final double ARM_ADJUST_FACTOR = 1.15;

    public static final double START_POSITION_HINGE = 26.0 * ARM_ADJUST_FACTOR;
    public static final double START_POSITION_TELESCOPE = 0.0;
    public static final double START_POSITION_WRIST = 0.0;
    public static final ArmPositionSide START_POSITION_SIDE = ArmPositionSide.FRONT;

    public static final double PICKUP_LOW_POSITION_HINGE = 3;
    public static final double PICKUP_LOW_POSITION_TELESCOPE = 5.0;
    public static final double PICKUP_LOW_POSITION_WRIST = 0.0;
    public static final ArmPositionSide PICKUP_LOW_POSITION_SIDE = ArmPositionSide.FRONT;

    public static final double PICKUP_HIGH_POSITION_HINGE = 19.5 * ARM_ADJUST_FACTOR;
    public static final double PICKUP_HIGH_POSITION_TELESCOPE = 20.0;
    public static final double PICKUP_HIGH_POSITION_WRIST = 0.0;
    public static final ArmPositionSide PICKUP_HIGH_POSITION_SIDE = ArmPositionSide.FRONT;

    public static final double GRID_MID_POSITION_HINGE = 18.0 * ARM_ADJUST_FACTOR;
    public static final double GRID_MID_POSITION_TELESCOPE = 74.0;
    public static final double GRID_MID_POSITION_WRIST = 0.0;
    public static final ArmPositionSide GRID_MID_POSITION_SIDE = ArmPositionSide.FRONT;

    public static final double GRID_HIGH_POSITION_HINGE = 46.0;
    public static final double GRID_HIGH_POSITION_TELESCOPE = 72.0;
    public static final double GRID_HIGH_POSITION_WRIST = 0.0;
    public static final ArmPositionSide GRID_HIGH_POSITION_SIDE = ArmPositionSide.BACK;

    public static final double PARALLEL_POSITION_HINGE = 8.0;
    public static final double PARALLEL_POSITION_TELESCOPE = 5.0;
    public static final double PARALLEL_POSITION_WRIST = 0.0;
    public static final ArmPositionSide PARALLEL_POSITION_SIDE = ArmPositionSide.FRONT;

    //Intake constants
    public static final double INTAKE_RETRACTED_POSITION = 0;
    public static final double LEFT_INTAKE_DEPLOYED_POSITION = 37;
    public static final double RIGHT_INTAKE_DEPLOYED_POSITION = 41;  
    public static final double INTAKE_PADDLE_RANGE_LEFT = 6;
    public static final double INTAKE_PADDLE_RANGE_RIGHT  = 6;
    public static final double INTAKE_TOLERANCE = .5;
    public static final double INTAKE_ALLOWED_POSITION_ERROR = .5;

    public static final double INTAKE_MINSPEED = .1;
    public static final double INTAKE_MAXSPEED = .3;
    public static final double INTAKE_WHEEL_SPEED = 0.1;
    public static final double INTAKE_ZERO_SPEED = -0.1;

    public static final int INTAKE_CURRENT_LIMIT = 30;
    public static final int INTAKE_ZERO_TIMEOUT = 10;

    //Limelight constants
    public static final int CENTER_PIPELINE = 0;
    public static final int RIGHT_PIPELINE = 1;
    public static final int LEFT_PIPELINE = 2;
    public static final int RRT_PIPELINE = 3;
    public static final int CAMERA_PIPELINE = 4;

    //LED constants
    public static final int LED_PWM_PORT = 0;
    public static final int LED_STRIP_LENGTH = 192;

    //Auto Constants
    public static final double AUTO_DRIVE_VELOCITY = 0.5;
    public static final double AUTO_DRIVE_DISTANCE = 5;

    public static final double HINGE_START = START_POSITION_HINGE;
    public static final double TELESCOPE_START = START_POSITION_TELESCOPE;
    public static final double WRIST_START = 0;

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