package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.ArmPositionHelper;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.helpers.enums.ArmPositionSide;
import frc.robot.subsystems.arm.Hinge;
import frc.robot.subsystems.arm.Telescope;

public class MoveArmMasterCommand extends CommandBase {
    Hinge hinge;
    Telescope telescope;
    ArmPosition goalPosition;
    Command myHingeCommand;
    Command myTelescopeCommand;

    boolean movingToBack;
    
    public MoveArmMasterCommand(Hinge hinge, Telescope telescope, ArmPosition position) {
        this.hinge = hinge;
        this.telescope = telescope;
        this.goalPosition = position;

        addRequirements(hinge, telescope); 
    }

    @Override
    public void initialize() {
        //moving from front to back
        movingToBack = ArmPositionHelper.fetchSide(ArmPositionHelper.currentPosition) == ArmPositionSide.FRONT && 
        ArmPositionHelper.fetchSide(goalPosition) == ArmPositionSide.BACK;

        ArmPositionHelper.retracted = false;
        ArmPositionHelper.atHingePosition = false;
        ArmPositionHelper.atTelescopePosition = false;
        ArmPositionHelper.hingeAdjustment = 0;
    }

    @Override
    public void execute() {
        Crashboard.toDashboard("Moving to Back", movingToBack, Constants.ARM_TAB);
        Crashboard.toDashboard("At Hinge Goal", ArmPositionHelper.atHingePosition, Constants.ARM_TAB);
        Crashboard.toDashboard("At Telescope Goal", ArmPositionHelper.atTelescopePosition, Constants.ARM_TAB);

        if(movingToBack && !ArmPositionHelper.retracted) {
            myTelescopeCommand = new MoveTelescopeCommand(telescope, true);

            myHingeCommand = new MoveHingeCommand(hinge, goalPosition);
        } else {
            ArmPositionHelper.currentPosition = goalPosition;

            if(!ArmPositionHelper.atHingePosition) {
                myHingeCommand = new MoveHingeCommand(hinge);
                
                if(movingToBack) {
                    myTelescopeCommand = new MoveTelescopeCommand(telescope, true);
                } else {
                    myTelescopeCommand = new MoveTelescopeCommand(telescope, false);
                }
            } else {
                myHingeCommand = new HoldArmCommand(hinge);
                myTelescopeCommand = new MoveTelescopeCommand(telescope, false);
            }
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
        if(ArmPositionHelper.atHingePosition && ArmPositionHelper.atTelescopePosition) {
            return true;
        }

        return false;
    }
}