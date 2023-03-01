package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.subsystems.Drivetrain;

public class AutoDriveCommand extends CommandBase {
    Drivetrain drive;
    double goal;

    public AutoDriveCommand(Drivetrain drive, double goal) {
        this.drive = drive;
        this.goal = goal;

        drive.resetEncoders();
        addRequirements(drive);
    }

    public void initialize(){
        drive.resetEncoders();
    }

    @Override
    public void execute() {
        drive.curve(Constants.AUTO_DRIVE_SPEED, 0);
    }

    @Override
    public boolean isFinished() {
        return -drive.getLeftPosition() >= goal;
    }

    @Override
    public void end(boolean isInterrupted) {
        drive.curve(0, 0);
    }
}