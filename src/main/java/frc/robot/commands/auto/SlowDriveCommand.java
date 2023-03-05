package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.subsystems.Drivetrain;

public class SlowDriveCommand extends CommandBase {
    Drivetrain drive;

    public SlowDriveCommand(Drivetrain drive) {
        this.drive = drive;

        drive.resetEncoders();
        addRequirements(drive);
    }

    public void initialize(){
        drive.resetEncoders();
    }

    @Override
    public void execute() {
        drive.curve(-0.5, 0);
    }

    @Override
    public boolean isFinished() {
        return -drive.getLeftPosition() <= -33;
    }

    @Override
    public void end(boolean isInterrupted) {
        drive.curve(0, 0);
    }
}