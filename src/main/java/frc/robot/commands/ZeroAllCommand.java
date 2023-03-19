package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.arm.hinge.HingeZeroCommand;
import frc.robot.commands.arm.telescope.TelescopeZeroCommand;
import frc.robot.commands.grabber.ReleaseCommand;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.arm.Hinge;
import frc.robot.subsystems.arm.Telescope;

public class ZeroAllCommand extends SequentialCommandGroup {
    Hinge hinge;
    Telescope telescope;

    public ZeroAllCommand(Hinge hinge, Telescope telescope) {
        this.hinge = hinge;
        this.telescope = telescope;

        addCommands(new TelescopeZeroCommand(telescope));
        addCommands(new HingeZeroCommand(hinge));
    }
}