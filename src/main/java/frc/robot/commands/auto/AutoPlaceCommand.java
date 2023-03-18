package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.arm.MoveArmMasterCommand;
import frc.robot.commands.arm.hinge.HoldHingeCommand;
import frc.robot.commands.arm.telescope.TelescopeZeroCommand;
import frc.robot.commands.grabber.ReleaseCommand;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.arm.Hinge;
import frc.robot.subsystems.arm.Telescope;

public class AutoPlaceCommand extends SequentialCommandGroup {
    Grabber grabber;
    Hinge hinge;
    Telescope telescope;

    public AutoPlaceCommand(Grabber grabber, Hinge hinge, Telescope telescope) {
        this.grabber = grabber;
        this.hinge = hinge;
        this.telescope = telescope;

        addCommands(new MoveArmMasterCommand(hinge, telescope, ArmPosition.GRID_MID));
        addCommands(new HoldHingeCommand(hinge).withTimeout(5));
        addCommands(new ParallelCommandGroup(
            new HoldHingeCommand(hinge).withTimeout(3),
            new ReleaseCommand(grabber).withTimeout(3)
        ));
        addCommands(new MoveArmMasterCommand(hinge, telescope, ArmPosition.START));
    }
}