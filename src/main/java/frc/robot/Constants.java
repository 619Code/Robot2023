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
    public static final int GRABBER_MOTOR_LEADER = 12; //left
    public static final int GRABBER_MOTOR_FOLLOWER = 14; //right

    //Arm CANs
    public static final int HINGE_MOTOR = 11;
    public static final int TELESCOPE_MOTOR = 10;
    public static final int WRIST_MOTOR = 13;

    //Sensor ports
    public static final int TELESCOPE_CONTRACTED_SWITCH = 1;
    public static final int TELESCOPE_EXTENDED_SWITCH = 0;

    public static final int GRABBER_PROXIMITY_SENSOR = 2;

    public static final int WRIST_ABSOLUTE_ENCODER = 3;

    public static final int HINGE_SWITCH = 6;

    //Drive constants
    public static final double WHEEL_DIAMETER = Units.inchesToMeters(5.827); //meters
    public static final int NEO_LIMIT = 45; //amps

    public static final double DRIVE_SPEED = 0.8;
    public static final double DRIVE_ROTATION = 0.3;

    public static final double SLOW_MODE_SPEED = 0.3;
    public static final double SLOW_MODE_ROTATION = 0.15;

    public static final double SUPER_SLOW_MODE_SPEED = 0.12;
    public static final double SUPER_SLOW_MODE_ROTATION = 0.12; //0.06

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
    public static final double CUBE_BLUE_THRESHOLD = 0.2;

    public static final double CUBE_TIMER = 1.5;
    public static final double CONE_TIMER = 1;

    public static final double GRAB_SPEED = 0.3;
    public static final double HOLD_SPEED = 0.3;
    public static final double GRAB_SPEED_DEFAULT = 0.1;

    public static final double DEFAULT_WAIT_TIME = 3;
    public static final double DEFAULT_PULSE_TIME = 0.5;

    public static final double RELEASE_SPEED_CUBE_HIGH = -0.15;
    public static final double RELEASE_SPEED_CUBE_MID = -0.05;
    public static final double RELEASE_SPEED_CONE = -0.1;

    //Hinge constants
    public static final double HINGE_MAX_SPEED = 0.4;
    public static final double HINGE_SLOW_SPEED = 0.075;
    public static final double HINGE_ZERO_SPEED = 0.1;

    public static final double HINGE_P = 0.05;
    public static final double HINGE_FF_EMPTY = 0.005;
    public static final double HINGE_FF_CONE = 0.009; //0.007
    public static final double SMALL_TOLERANCE = 0.5;
    public static final double BIG_TOLERANCE = 2.0; //1.5
    public static final double CLOSE_POSITION = 6;

    public static final double UP_POSITION = 32.4;
    public static final double FRONT_HORIZONTAL_POSITION = 6.2;
    public static final double DEGREES_PER_TICK = 90.0 / (Constants.UP_POSITION - Constants.FRONT_HORIZONTAL_POSITION);
    public static final double BASE_ANGLE = -21.3;

    public static final double ARM_MOVEMENT_TIMEOUT = 10.0;

    public static final double MIN_HINGE_POSITION = 0.0;
    public static final double MAX_HINGE_POSITION = UP_POSITION; //57.0

    //Telescope constants
    public static final double TELESCOPE_CONTRACTED_SWITCH_POSITION = 0.0;
    public static final double TELESCOPE_EXTENDED_SWITCH_POSITION = 184;
    public static final double EXTENSION_TOLERANCE = 5.0;
    public static final double MIN_TELESCOPE_EXTENSION = 0;
    public static final double MAX_TELESCOPE_EXTENSION = TELESCOPE_EXTENDED_SWITCH_POSITION - EXTENSION_TOLERANCE;
    public static final double TELESCOPE_SLOWDOWN_ZONE = 10;

    public static final double TELESCOPE_SPEED = 0.8; //0.9
    public static final double TELESCOPE_ZERO_SPEED = 0.1; //0.2 UNDO
    public static final double TELESCOPE_P = 0.04;

    public static final double INCHES_PER_TICK = 15.0 / 139.0;
    public static final double CONTRACTED_LENGTH = 24.0; //inches

    //Wrist constants
    public static final double POSITION_OFFSET = 0;

    public static final double MIN_WRIST_POSITION = -.5;
    public static final double MAX_WRIST_POSITION = 25.9;

    public static final double WRIST_SPEED = 0.2;
    public static final double WRIST_ZERO_SPEED = 0.1;
    public static final double WRIST_P = 0.04;

    public static final double INCREMENT_VALUE = 6;

    public static final double WRIST_FF_EMPTY = 0.1;
    public static final double WRIST_FF_CONE = 0.15;

    //Arm cases
    public static final double ARM_ADJUST_FACTOR = 1;

    public static final double START_POSITION_HINGE = 0;
    public static final double START_POSITION_TELESCOPE = 0.0;
    public static final double START_POSITION_WRIST = 1.0;
    public static final ArmPositionSide START_POSITION_SIDE = ArmPositionSide.FRONT;

    public static final double PICKUP_LOW_POSITION_HINGE = 0;
    public static final double PICKUP_LOW_POSITION_TELESCOPE = 14.0;
    public static final double PICKUP_LOW_POSITION_WRIST = 18.0; //20.0
    public static final ArmPositionSide PICKUP_LOW_POSITION_SIDE = ArmPositionSide.FRONT;

    public static final double PICKUP_HIGH_POSITION_HINGE = 20.0;
    public static final double PICKUP_HIGH_POSITION_TELESCOPE = 66.0;
    public static final double PICKUP_HIGH_POSITION_WRIST = 25.0;
    public static final ArmPositionSide PICKUP_HIGH_POSITION_SIDE = ArmPositionSide.FRONT;

    public static final double CONE_MID_POSITION_HINGE = 13.0;
    public static final double CONE_MID_POSITION_TELESCOPE = 161.0;
    public static final double CONE_MID_POSITION_WRIST = 12;
    public static final ArmPositionSide CONE_MID_POSITION_SIDE = ArmPositionSide.FRONT;

    public static final double CUBE_MID_POSITION_HINGE = 12.0;
    public static final double CUBE_MID_POSITION_TELESCOPE = 68.0;
    public static final double CUBE_MID_POSITION_WRIST = 18.0;
    public static final ArmPositionSide CUBE_MID_POSITION_SIDE = ArmPositionSide.FRONT;

    public static final double CUBE_HIGH_POSITION_HINGE = 13.0;
    public static final double CUBE_HIGH_POSITION_TELESCOPE = 175.0;
    public static final double CUBE_HIGH_POSITION_WRIST = 12.0;
    public static final ArmPositionSide CUBE_HIGH_POSITION_SIDE = ArmPositionSide.FRONT;

    public static final double CHUTE_POSITION_HINGE = 46.0;
    public static final double CHUTE_POSITION_WRIST = 0.0;
    public static final double CHUTE_POSITION_TELESCOPE = 0.0;
    public static final ArmPositionSide CHUTE_POSITION_SIDE = ArmPositionSide.FRONT;

    //Limelight constants
    public static final int CENTER_PIPELINE = 0;
    public static final int RIGHT_PIPELINE = 1;
    public static final int LEFT_PIPELINE = 2;
    public static final int RRT_PIPELINE = 3;
    public static final int CAMERA_PIPELINE = 4;

    //LED constants
    public static final int LED_PWM_PORT = 0;
    public static final int LED_STRIP_LENGTH = 300;

    public static final double TOGGLE_INTERVAL = 0.25;

    //Auto Constants
    public static final double AUTO_DRIVE_VELOCITY = -0.6;
    public static final double AUTO_DRIVE_DISTANCE = -5;

    public static final double HINGE_START = 0;
    public static final double TELESCOPE_START = START_POSITION_TELESCOPE;
    public static final double WRIST_START = 0;

    // Dashboard Tabs
    public static String DRIVE_TAB = "Drive";
    public static String GRABBER_TAB = "Grab";
    public static String LIMELIGHT_TAB = "Limelight";
    public static String ARM_TAB = "Arm";
    public static String WRIST_TAB = "Wrist";
    public static String MOTORCONTROLLERS_TAB = "Motor Controllers";
    public static String AUTOS_TAB = "Autos";
    public static String COMPETITON_TAB = "Competition";
    public static String SPARKS_TAB = "Sparks";
    public static String STATUS_TAB = "Status";

    //LEGACY INTAKE CONSTANTS

    //Intake CANs
    public static final int LEFT_ARM_MOTOR = -1; 
    public static final int RIGHT_ARM_MOTOR = -1;
    public static final int LEFT_WHEEL_MOTOR = -1;
    public static final int RIGHT_WHEEL_MOTOR = -1;

    //Intake switches
    public static final int INTAKE_LEFT_SWITCH = -1;
    public static final int INTAKE_RIGHT_SWITCH = -1;

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

    //Intake tabs
    public static String INTAKE_TAB = "Intake";
}