package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.helpers.enums.LineupPosition;
import frc.robot.helpers.limelight.LimelightDataStorer;
import frc.robot.helpers.limelight.PipelineHelper;
import frc.robot.subsystems.Drivetrain;

public class AutoLineupCommand extends CommandBase {
    private Drivetrain drive;

    private LineupPosition position;

    private double tx;

    public AutoLineupCommand(Drivetrain drive, LineupPosition position) {
        this.drive = drive;
        this.position = position;

        addRequirements(drive);
    }

    @Override
    public void initialize() {
        switch (position) {
            case LEFT:
                PipelineHelper.SetRRTPipeline();    
                break;   
            case CENTER:
                PipelineHelper.SetCenterPipeline();
                break;
            case RIGHT:
                PipelineHelper.SetRRTPipeline();
                break;
        }
    }

    @Override
    public void execute() {
        tx = LimelightDataStorer.txNew();
        double rotation = Math.abs(tx) * Constants.ROTATION_P;
        rotation = Math.min(rotation, Constants.ROTATION_MAX);
        rotation *= (tx > 1) ? -1 : 1;

        drive.curve(-Constants.APPROACH_SPEED, -rotation);
    }

    @Override
    public void end(boolean interrupted) {
        drive.curve(0, 0);
        PipelineHelper.setCameraPipeline();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}