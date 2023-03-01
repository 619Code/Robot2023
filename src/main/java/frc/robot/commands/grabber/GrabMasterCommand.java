package frc.robot.commands.grabber;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.States;
import frc.robot.helpers.ArmPositionHelper;
import frc.robot.helpers.ColorDetector;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.LedStrip;

public class GrabMasterCommand extends CommandBase {
    private Grabber grabber;
    private LedStrip ledStrip;

    boolean isCube;
    boolean startState;
    Command myCommand;
    
    public GrabMasterCommand(Grabber grabber, LedStrip ledStrip) {
        this.grabber = grabber;
        this.ledStrip = ledStrip;

        addRequirements(grabber); 
    }

    @Override
    public void initialize() {
        startState = grabber.grabbing;
        isCube = ColorDetector.isCube;
    }

    @Override
    public void execute() {
        if(startState) { //release if it's grabbing
            myCommand = new ReleaseCommand(grabber);
        } else { //grab if it's not grabbing
            if(ArmPositionHelper.currentPosition == ArmPosition.PICKUP_LOW) { //use color sensor
                if(isCube) {
                    myCommand = new GrabCommand(grabber, true);
                } else {
                    myCommand = new GrabCommand(grabber, false);
                }
            } else if(ArmPositionHelper.currentPosition == ArmPosition.PICKUP_HIGH) { //use signal light indicator
                if(ledStrip.isYellow) {
                    myCommand = new GrabCommand(grabber, false);
                } else {
                    myCommand = new GrabCommand(grabber, true);
                }
            } else { //do nothing
                return;
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
        if(!grabber.movable()) {
            return true;
        }

        return (startState != grabber.grabbing); //end when the state switches
    }
}