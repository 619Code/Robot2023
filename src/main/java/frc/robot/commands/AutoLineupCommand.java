package frc.robot.commands;

import edu.wpi.first.math.trajectory.constraint.CentripetalAccelerationConstraint;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.enums.LineupPosition;
import frc.robot.helpers.limelight.LimelightDataStorer;
import frc.robot.helpers.limelight.PipelineHelper;
import frc.robot.subsystems.Drivetrain;

public class AutoLineupCommand extends CommandBase {
    private Drivetrain drive;
    private GenericEntry rotationEntry;

    private LineupPosition position;

    private double tx;
    private boolean hasValidTarget;

    private boolean usingRRT;

    public AutoLineupCommand(Drivetrain drive, LineupPosition position) {
        this.drive = drive;
        this.position = position;

        addRequirements(drive);
    }

    @Override
    public void initialize() {
        switch (position) {
            case LEFT:
                PipelineHelper.SetLeftPipeline();
                usingRRT = false;
                break;   
            case CENTER:
                PipelineHelper.SetCenterPipeline();
                usingRRT = false;
                break;
            case RIGHT:
                PipelineHelper.SetRightPipeline();
                usingRRT = false;
                break;
        }
    }

    @Override
    public void execute() {
        hasValidTarget = LimelightDataStorer.hasValidTarget();

        if(hasValidTarget) {
            tx = LimelightDataStorer.txNew();
            double rotation = Math.abs(tx) * Constants.ROTATION_P;
            rotation = Math.min(rotation, Constants.ROTATION_MAX);
            rotation *= (tx > 1) ? -1 : 1;

            drive.curve(-Constants.APPROACH_SPEED, -rotation);
        } else {
            if(!usingRRT && (position == LineupPosition.LEFT || position == LineupPosition.RIGHT)) {
                PipelineHelper.SetRRTPipeline();
            }

            drive.curve(0, 0);
        }
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