package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.arm.MoveArmMasterCommand;
import frc.robot.commands.arm.hinge.HoldHingeCommand;
import frc.robot.commands.arm.hinge.HoldHingeDefault;
import frc.robot.commands.arm.telescope.TelescopeZeroCommand;
import frc.robot.commands.arm.wrist.HoldWristCommand;
import frc.robot.commands.grabber.GrabDefaultCommand;
import frc.robot.commands.grabber.ReleaseCommand;
import frc.robot.helpers.ArmLogicAssistant;
import frc.robot.helpers.ArmPositionHelper;
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

        addCommands(moveArmMasterCommandFactory(ArmPosition.CUBE_HIGH));
        addCommands(new ParallelDeadlineGroup(
            new SequentialCommandGroup(
                new WaitCommand(1),
                new ReleaseCommand(grabber).withTimeout(1)
            ),
            new HoldHingeDefault(hinge, true),
            new HoldWristCommand(wrist)
        ));
        addCommands(moveArmMasterCommandFactory(ArmPosition.START));
    }

    public Command moveArmMasterCommandFactory(ArmPosition goal) {
		return new SequentialCommandGroup(
			new InstantCommand(() -> {
                ArmPositionHelper.hingeAdjustment = 0;
                ArmLogicAssistant.updatePositions(goal);				
            }),
			(new MoveArmMasterCommand(hinge, telescope, wrist, grabber, goal))
			.until(ArmLogicAssistant::atAllPositions)
			.withTimeout(Constants.ARM_MOVEMENT_TIMEOUT),
			new InstantCommand(() -> {
                ArmPositionHelper.currentPosition = goal;				
            })
		);
	}
}