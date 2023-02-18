package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.helpers.PipelineHelper;

public class PipelineSwitchingCommand extends CommandBase {
    
    private int pipeline;

    public PipelineSwitchingCommand(int line) {
        pipeline = line;
        switch (pipeline) {
            case 0:
                PipelineHelper.SetLeftPipeline();
                System.out.println("weeeeee");
                break;
            case 1:
                PipelineHelper.SetCenterPipeline();
                System.out.println("wooooooooo");
                break;
            case 2:
                PipelineHelper.SetRightPipeline();
                System.out.println("waaaaaaaa");
                break;
            default:
                System.out.println(":3c");
                System.out.println("swigglehhust");
                break;
        }
        
    }

}
