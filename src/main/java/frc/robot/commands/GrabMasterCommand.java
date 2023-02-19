package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Grabber;

public class GrabMasterCommand extends CommandBase {
    private Grabber grabber;
    boolean isCube;
    boolean startState;
    Command myCommand;
    
    public GrabMasterCommand(Grabber grabber, boolean isCube) {
        this.grabber = grabber;
        this.isCube = isCube;
        addRequirements(grabber); 
    }

    @Override
    public void initialize() {
        this.startState = grabber.grabbing;
    }

    @Override
    public void execute() {
        if(startState) { //release if it's grabbing
            myCommand = new ReleaseCommand(grabber);
        } else { //grab if it's not grabbing
            if(isCube) {
                myCommand = new GrabCommand(grabber, true);
            } else {
                myCommand = new GrabCommand(grabber, false);
            }
        }

        myCommand.execute();
    }

    @Override
    public void end(boolean interrupted) {
        grabber.stop();
    }

    @Override
    public boolean isFinished() {
        if(!grabber.zeroed) {
            return true;
        }

        return (startState != grabber.grabbing); //end when the state switches
    }
}