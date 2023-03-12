package frc.robot.commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.helpers.Crashboard;
import frc.robot.subsystems.Drivetrain;
import io.github.oblarg.oblog.Loggable;
import frc.robot.subsystems.Limelight;

public class DriveCommand extends CommandBase implements Loggable {
    private Drivetrain drive;
    private CommandXboxController controller;
    private double leftY, rightX;
    private double throttle;
    private double rotation;

    private SlewRateLimiter speedLimiter;

    GenericEntry slewRateLimit;
    GenericEntry driveSpeed;

    double lastSlewRateLimit;

    public DriveCommand(Drivetrain drive, CommandXboxController controller) {
        this.drive = drive;
        this.controller = controller;
        addRequirements(drive);

        //slewRateLimit = Crashboard.toDashboard("Slew Rate Limit", 0.1, Constants.COMPETITON_TAB);
        //driveSpeed = Crashboard.toDashboard("Drive Speed", Constants.DRIVE_SPEED, Constants.COMPETITON_TAB);
        //lastSlewRateLimit = 0.1;

        speedLimiter = new SlewRateLimiter(Constants.DRIVE_SLEW_RATE_LIMIT);
    }

    @Override
    public void execute() {
        /*if(lastSlewRateLimit != slewRateLimit.getDouble(0.1)) {
            lastSlewRateLimit = slewRateLimit.getDouble(0.1);
            speedLimiter = new SlewRateLimiter(lastSlewRateLimit);
        }*/

        leftY = -controller.getLeftY();
        rightX = controller.getRightX();

        leftY = leftY * Math.abs(leftY);
        
        setVals();
        drive.curve(throttle, rotation);
    }

    public void setVals() {
        throttle = (Math.abs(leftY) > Constants.JOYSTICK_DEADZONE) ? (Math.abs(leftY) * Math.abs(leftY)) * leftY : 0; //cubic scaling
        rotation = (Math.abs(rightX) > Constants.JOYSTICK_DEADZONE) ? rightX : 0;

        if(controller.getRightTriggerAxis() > 0.5) {
            throttle *= Constants.SLOW_MODE_SPEED;
            rotation *= Constants.SLOW_MODE_ROTATION;
            drive.setBrake();
        } else if (controller.rightBumper().getAsBoolean()) {
            throttle *= Constants.SUPER_SLOW_MODE_SPEED;
            rotation *= Constants.SUPER_SLOW_MODE_ROTATION;
            drive.setBrake();
        } else {
            throttle *= Constants.DRIVE_SPEED;
            rotation *= Constants.DRIVE_ROTATION;
            drive.setMixed();
        }

        throttle = speedLimiter.calculate(throttle);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
