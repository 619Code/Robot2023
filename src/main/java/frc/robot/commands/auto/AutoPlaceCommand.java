package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.arm.HingeZeroCommand;
import frc.robot.commands.arm.MoveArmMasterCommand;
import frc.robot.commands.arm.TelescopeZeroCommand;
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

        addCommands(new MoveArmMasterCommand(hinge, telescope, ArmPosition.GRID_HIGH));
        addCommands(new TelescopeZeroCommand(telescope));
        addCommands(new HingeZeroCommand(hinge));
    }
}