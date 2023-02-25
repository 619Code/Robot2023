package frc.robot.helpers;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants;
import frc.robot.subsystems.Limelight;

public class PipelineHelper {

    private static Limelight limelight = new Limelight();
    private static NetworkTable table;
    private static NetworkTableEntry pipeline;


    public static void SetLeftPipeline(){
        limelight.setPipeline(Constants.LEFT_PIPELINE);
    }
    public static void SetCenterPipeline(){
        limelight.setPipeline(Constants.CENTER_PIPELINE);
    }
    public static void SetRightPipeline(){
        limelight.setPipeline(Constants.RIGHT_PIPELINE);
    }
    public static void PipelineToDashboard(){
        pipeline = table.getEntry("pipeline");

        Crashboard.toDashboard("Pipeline", pipeline.getDouble(0), Constants.LimelightTab);
    }
}