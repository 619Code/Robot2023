package frc.robot.commands.masters;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.GrabCommand;
import frc.robot.commands.GrabZeroCommand;
import frc.robot.commands.ReleaseCommand;
import frc.robot.subsystems.Grabber;

public class ZeroMasterCommand extends CommandBase {
    private Grabber grabber;
    Command myCommand;
    
    public ZeroMasterCommand(Grabber grabber) {
        this.grabber = grabber;

        addRequirements(grabber); 
    }

    @Override
    public void execute() {
        if(!grabber.zeroed) {
            myCommand = new GrabZeroCommand(grabber);
        } else {
            myCommand = new ReleaseCommand(grabber);
        }

        myCommand.execute();
    }

    @Override
    public void end(boolean interrupted) {
        grabber.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}