package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.arm.HingeZeroCommand;
import frc.robot.commands.arm.TelescopeZeroCommand;
import frc.robot.commands.grabber.ReleaseCommand;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.arm.Hinge;
import frc.robot.subsystems.arm.Telescope;
import frc.robot.unused.intake.IntakeSub;
import frc.robot.unused.intake.IntakeZeroCommand;

public class AutoZeroCommand extends SequentialCommandGroup {
    Grabber grabber;
    Hinge hinge;
    Telescope telescope;

    public AutoZeroCommand(Grabber grabber, Hinge hinge, Telescope telescope) {
        this.grabber = grabber;
        this.hinge = hinge;
        this.telescope = telescope;

        //addCommands(new InstantCommand(() -> hinge.zero(),hinge));
        addCommands(new TelescopeZeroCommand(telescope));
        addCommands(new HingeZeroCommand(hinge));
    }
}