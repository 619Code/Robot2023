package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.arm.HoldArmCommand;
import frc.robot.commands.arm.MoveArmMasterCommand;
import frc.robot.commands.grabber.GrabCommand;
import frc.robot.commands.grabber.GrabZeroCommand;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.IntakeSub;
import frc.robot.subsystems.arm.Hinge;
import frc.robot.subsystems.arm.Telescope;

public class PreMatchSettingsCommand extends SequentialCommandGroup {
    IntakeSub intake;
    Grabber grabber;
    Hinge hinge;
    Telescope telescope;

    public PreMatchSettingsCommand(IntakeSub intake, Grabber grabber, Hinge hinge, Telescope telescope) {
        this.intake = intake;
        this.grabber = grabber;
        this.hinge = hinge;
        this.telescope = telescope;

        addCommands(new AutoZeroCommand(intake, grabber, hinge, telescope));
        addCommands(new MoveArmMasterCommand(hinge, telescope, ArmPosition.START));
        //addCommands(new GrabCommand(grabber, false, 0.2));
    }
}