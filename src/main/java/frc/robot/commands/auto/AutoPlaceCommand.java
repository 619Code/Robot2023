package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.arm.MoveArmMasterCommand;
import frc.robot.commands.arm.hinge.HoldHingeCommand;
import frc.robot.commands.arm.telescope.TelescopeZeroCommand;
import frc.robot.commands.grabber.ReleaseCommand;
import frc.robot.helpers.ArmLogicAssistant;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.arm.Hinge;
import frc.robot.subsystems.arm.Telescope;
import frc.robot.subsystems.arm.Wrist;

public class AutoPlaceCommand extends SequentialCommandGroup {
    Grabber grabber;
    Hinge hinge;
    Telescope telescope;
    Wrist wrist;

    public AutoPlaceCommand(Grabber grabber, Hinge hinge, Telescope telescope, Wrist wrist) {
        this.grabber = grabber;
        this.hinge = hinge;
        this.telescope = telescope;
        this.wrist = wrist;

        addCommands(moveArmMasterCommandFactory(ArmPosition.GRID_MID));
        addCommands(new ParallelDeadlineGroup(
            new SequentialCommandGroup(
                new WaitCommand(5),
                new ReleaseCommand(grabber).withTimeout(3)
            ),
            new HoldHingeCommand(hinge,true)
        ));
        addCommands(moveArmMasterCommandFactory(ArmPosition.START));
    }

    public Command moveArmMasterCommandFactory(ArmPosition position) {
		return (new MoveArmMasterCommand(hinge, telescope, wrist, grabber, ArmPosition.START)).until(ArmLogicAssistant::atBothPositions);
	}
}