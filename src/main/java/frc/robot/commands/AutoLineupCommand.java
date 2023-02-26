package frc.robot.commands;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.enums.LineupPosition;
import frc.robot.helpers.limelight.LimelightDataStorer;
import frc.robot.subsystems.Drivetrain;

public class AutoLineupCommand extends CommandBase {
    private Drivetrain drive;
    private GenericEntry rotationEntry;

    private LineupPosition position;

    private double tx;

    public AutoLineupCommand(Drivetrain drive, LineupPosition position) {
        this.drive = drive;
        this.position = position;

        addRequirements(drive);
    }

    @Override
    public void execute() {
        tx = LimelightDataStorer.txNew();
        double rotation = Math.abs(tx) * Constants.ROTATION_P;
        rotation = Math.min(rotation, Constants.ROTATION_MAX);
        rotation *= (tx > 1) ? -1 : 1;
        rotationEntry = Crashboard.toDashboard("Rotation", rotation, Constants.DriveTab);

        drive.curve(-Constants.APPROACH_SPEED, rotation);
    }

    @Override
    public void end(boolean interrupted) {
        
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}