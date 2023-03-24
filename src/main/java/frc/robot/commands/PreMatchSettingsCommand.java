package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.arm.MoveArmMasterCommand;
import frc.robot.commands.arm.hinge.HoldHingeCommand;
import frc.robot.commands.grabber.GrabCommand;
import frc.robot.helpers.ArmLogicAssistant;
import frc.robot.helpers.ArmPositionHelper;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.arm.Hinge;
import frc.robot.subsystems.arm.Telescope;
import frc.robot.subsystems.arm.Wrist;

public class PreMatchSettingsCommand extends SequentialCommandGroup {
    Hinge hinge;
    Telescope telescope;
    Wrist wrist;
    Grabber grabber;

    public PreMatchSettingsCommand(Hinge hinge, Telescope telescope, Wrist wrist, Grabber grabber) {
        this.hinge = hinge;
        this.telescope = telescope;
        this.wrist = wrist;
        this.grabber = grabber;

        addCommands(new ZeroAllCommand(hinge, telescope));
        addCommands(moveArmMasterCommandFactory(ArmPosition.START));
    }

    public Command moveArmMasterCommandFactory(ArmPosition goal) {
		return new SequentialCommandGroup(
			new InstantCommand(() -> {
                ArmPositionHelper.hingeAdjustment = 0;
                ArmLogicAssistant.updatePositions(goal);
                ArmPositionHelper.currentPosition = goal;				
            }),
			(new MoveArmMasterCommand(hinge, telescope, wrist, grabber, goal))
			.until(ArmLogicAssistant::atWristPosition)
			.withTimeout(Constants.ARM_MOVEMENT_TIMEOUT)
		);
	}
}