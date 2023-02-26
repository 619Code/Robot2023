package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants;
import frc.robot.helpers.ArmPositionHelper;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.arm.Hinge;
import frc.robot.subsystems.arm.Telescope;

public class MoveArmMasterCommand extends CommandBase {
    Hinge hinge;
    Telescope telescope;
    ArmPosition goalPosition;
    Command myHingeCommand;
    Command myTelescopeCommand;

    boolean movingToFront;
    
    public MoveArmMasterCommand(Hinge hinge, Telescope telescope, ArmPosition position) {
        this.hinge = hinge;
        this.telescope = telescope;
        this.goalPosition = position;

        addRequirements(hinge, telescope); 
    }

    @Override
    public void initialize() {
        ArmPositionHelper.currentPosition = goalPosition;
        ArmPositionHelper.retracted = false;
        ArmPositionHelper.atHingePosition = false;
        ArmPositionHelper.atTelescopePosition = false;

        movingToFront = ArmPositionHelper.fetchHingeValue(goalPosition) < hinge.getPosition();
    }

    @Override
    public void execute() {
        Crashboard.toDashboard("Moving to Front", movingToFront, Constants.ArmTab);
        Crashboard.toDashboard("At Hinge Goal", ArmPositionHelper.atHingePosition, Constants.ArmTab);
        Crashboard.toDashboard("At Telescope Goal", ArmPositionHelper.atTelescopePosition, Constants.ArmTab);

        if(!ArmPositionHelper.atHingePosition) {
            myHingeCommand = new MoveHingeCommand(hinge);
            if(movingToFront) {
                myTelescopeCommand = new MoveTelescopeCommand(telescope, false);
            } else {
                myTelescopeCommand = new MoveTelescopeCommand(telescope, true);
            }
        } else {
            myHingeCommand = new HoldArmCommand(hinge);
            myTelescopeCommand = new MoveTelescopeCommand(telescope, false);
        }

        myHingeCommand.execute();
        myTelescopeCommand.execute();
    }

    @Override
    public void end(boolean interrupted) {
        hinge.stop();
        telescope.stop();
    }

    @Override
    public boolean isFinished() {
        //(!hinge.zeroed || !telescope.zeroed) || 
        return (ArmPositionHelper.atHingePosition && ArmPositionHelper.atTelescopePosition);
    }
}