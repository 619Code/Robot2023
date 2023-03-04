package frc.robot.commands;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import io.github.oblarg.oblog.Loggable;
import frc.robot.subsystems.Limelight;

public class DriveCommand extends CommandBase implements Loggable {
    private Drivetrain drive;
    private CommandXboxController controller;
    private double leftY, rightX;
    private double throttle;
    private double rotation;

    public DriveCommand(Drivetrain drive, CommandXboxController controller) {
        this.drive = drive;
        this.controller = controller;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        leftY = -controller.getLeftY();
        rightX = controller.getRightX();
        
        setVals();
        drive.curve(throttle, rotation);
    }

    public void setVals() {
        throttle = (Math.abs(leftY) > Constants.JOYSTICK_DEADZONE) ? leftY : 0;
        rotation = (Math.abs(rightX) > Constants.JOYSTICK_DEADZONE) ? rightX : 0;

        if(controller.getRightTriggerAxis() > 0.5) {
            throttle *= 0.5;
            rotation *= 0.5; //experimental for the time being
        //GIGA SLOW MODE
        } else if (controller.rightBumper().getAsBoolean()) {
            throttle *= 0.2;
            rotation *= 0.2;
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
