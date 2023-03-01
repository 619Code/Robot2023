package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.arm.HingeZeroCommand;
import frc.robot.commands.arm.TelescopeZeroCommand;
import frc.robot.commands.grabber.GrabZeroCommand;
import frc.robot.commands.grabber.ReleaseCommand;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.IntakeSub;
import frc.robot.subsystems.arm.Hinge;
import frc.robot.subsystems.arm.Telescope;

public class AutoZeroCommand extends ParallelCommandGroup {
    IntakeSub intake;
    Grabber grabber;
    Hinge hinge;
    Telescope telescope;

    public AutoZeroCommand(IntakeSub intake, Grabber grabber, Hinge hinge, Telescope telescope) {
        this.intake = intake;
        this.grabber = grabber;
        this.hinge = hinge;
        this.telescope = telescope;

        addCommands(new SequentialCommandGroup(
            new GrabZeroCommand(grabber),
            new ReleaseCommand(grabber)
        ));
        addCommands(new TelescopeZeroCommand(telescope));
        addCommands(new HingeZeroCommand(hinge));
    }
}