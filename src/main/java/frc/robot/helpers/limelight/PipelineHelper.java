package frc.robot.helpers.limelight;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.enums.Pipeline;
import frc.robot.subsystems.Limelight;

public class PipelineHelper {
    public static Limelight limelight;
    private static NetworkTable table;
    private static NetworkTableEntry pipeline;
    private static GenericEntry PipelineEntry;

    public static void SetLeftPipeline(){
        limelight.currentPipeline = Pipeline.LEFT_PIPELINE;
        limelight.setPipeline(Constants.LEFT_PIPELINE);
    }

    public static void SetCenterPipeline(){
        limelight.currentPipeline = Pipeline.CENTER_PIPELINE;
        limelight.setPipeline(Constants.CENTER_PIPELINE);
    }

    public static void SetRightPipeline(){
        limelight.currentPipeline = Pipeline.RIGHT_PIPELINE;
        limelight.setPipeline(Constants.RIGHT_PIPELINE);
    }

    public static void SetRRTPipeline() {
        limelight.currentPipeline = Pipeline.RRT_PIPELINE;
        limelight.setPipeline(Constants.RRT_PIPELINE);
    }

    public static void setCameraPipeline() {
        limelight.currentPipeline = Pipeline.CAMERA_PIPELINE;
        limelight.setPipeline(Constants.CAMERA_PIPELINE);
    }

    public static void PipelineToDashboard(){
        pipeline = table.getEntry("pipeline");

        PipelineEntry = Crashboard.toDashboard("Pipeline", pipeline.getDouble(0), Constants.LimelightTab);
    }
}