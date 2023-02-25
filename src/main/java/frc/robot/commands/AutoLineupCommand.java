package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.limelight.LimelightDataStorer;
import frc.robot.subsystems.Drivetrain;

public class AutoLineupCommand extends CommandBase {
    private Drivetrain drive;

    private double tx;

    public AutoLineupCommand(Drivetrain drive) {
        this.drive = drive;

        addRequirements(drive);
    }

    @Override
    public void execute() {
        tx = LimelightDataStorer.txNew();
        double rotation = Math.abs(tx) * 0.08;
        rotation = Math.min(rotation,0.3);
        rotation *= (tx > 1) ? -1 : 1;
        Crashboard.toDashboard("Rotation", rotation, Constants.DriveTab);

        drive.curve(0.4, rotation);
    }

    @Override
    public void end(boolean interrupted) {
        
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}