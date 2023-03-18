package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.arm.hinge.HoldHingeCommand;
import frc.robot.commands.arm.hinge.MoveHingeCommand;
import frc.robot.commands.arm.telescope.MoveTelescopeCommand;
import frc.robot.commands.arm.telescope.RetractTelescopeCommand;
import frc.robot.helpers.ArmLogicAssistant;
import frc.robot.helpers.ArmPositionHelper;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.subsystems.arm.Hinge;
import frc.robot.subsystems.arm.Telescope;

public class MoveArmMasterCommand extends ParallelCommandGroup {
    Hinge hinge;
    Telescope telescope;

    public MoveArmMasterCommand(Hinge hinge, Telescope telescope, ArmPosition goal) {
        this.hinge = hinge;
        this.telescope = telescope;

        ArmPositionHelper.hingeAdjustment = 0;
        ArmLogicAssistant.updatePositions(goal);

        addCommands(new SequentialCommandGroup(
            new MoveHingeCommand(hinge, goal),
            new HoldHingeCommand(hinge).until(ArmLogicAssistant::atBothPositions)
        ));

        if(ArmLogicAssistant.movingToBack) {
            addCommands(new SequentialCommandGroup(
                new RetractTelescopeCommand(telescope).until(ArmLogicAssistant::atHingePosition),
                new MoveTelescopeCommand(telescope, goal)
            ));
        } else {
            addCommands(new MoveTelescopeCommand(telescope, goal));
        }
    }
}