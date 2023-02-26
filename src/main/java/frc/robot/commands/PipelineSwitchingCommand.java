package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.helpers.enums.Pipeline;
import frc.robot.helpers.limelight.PipelineHelper;

public class PipelineSwitchingCommand extends CommandBase {
    private Pipeline pipeline;

    public PipelineSwitchingCommand(Pipeline pipeline) {
        this.pipeline = pipeline;     
    }

    @Override
    public void initialize() {
        switch (pipeline) {
            case LEFT_PIPELINE:
                PipelineHelper.SetLeftPipeline();
                break;
            case CENTER_PIPELINE:
                PipelineHelper.SetCenterPipeline();
                break;
            case RIGHT_PIPELINE:
                PipelineHelper.SetRightPipeline();
                break;
            case RRT_PIPELINE:
                PipelineHelper.SetRRTPipeline();
                break;
            case CAMERA_PIPELINE:
                PipelineHelper.setCameraPipeline();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}