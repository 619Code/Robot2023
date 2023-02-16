package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.helpers.Crashboard;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class DriveCommand extends CommandBase {
    private Drivetrain drive;
    private XboxController controller;
    private double leftY, rightX;
    //@Log
    private double throttle;
    //@Log
    private double rotation;
    private boolean isLowGear;

    public DriveCommand(Drivetrain drive, XboxController controller) {
        this.drive = drive;
        this.controller = controller;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        leftY = -controller.getLeftY();
        rightX = controller.getRightX();

        //dashboard read/write works!
        Crashboard.toDashboard("Forward Speed", leftY);
        Crashboard.toDashboard("Turn Speed", Crashboard.snagDouble("Forward Speed"));
        
        setVals();
        //System.out.println("Speed: " + throttle);
        //System.out.println("Rotation: " + rotation);
        drive.curve(throttle, rotation, isLowGear);
    }

    public void setVals() {
        throttle = (Math.abs(leftY) > Constants.JOYSTICK_DEADZONE) ? leftY : 0;
        throttle = throttle;
        rotation = (Math.abs(rightX) > Constants.JOYSTICK_DEADZONE) ? rightX : 0;
        rotation = -rotation;

        if(controller.getRightTriggerAxis() > 0.5) { //UNDO
            throttle *= 0.5;
        }

        isLowGear = false;
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
