package frc.robot;

import frc.robot.commands.DriveCommand;
import frc.robot.commands.GrabManualCommand;
import frc.robot.commands.masters.GrabMasterCommand;
import frc.robot.commands.masters.ZeroMasterCommand;
import frc.robot.helpers.AutoCommandSwitcher;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Grabber;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {


	private XboxController driver;
	private CommandXboxController operator;
	
	private DriveCommand driveCommand;
	private GrabManualCommand grabManualCommand;

	private Drivetrain drive;
	private Grabber grabber;

	private String gameData;

	public RobotContainer() {
		driver = new XboxController(0);
		operator = new CommandXboxController(1);

        drive = new Drivetrain();
        driveCommand = new DriveCommand(drive, driver);
        drive.setDefaultCommand(driveCommand);

		/*grabber = new Grabber();
		grabManualCommand = new GrabManualCommand(grabber, operator);
        grabber.setDefaultCommand(grabManualCommand);*/

		configureBindings();
	}

	private void configureBindings() {
		/*
		Trigger zeroButton = operator.a();
        zeroButton.onTrue(new ZeroMasterCommand(grabber));
        Trigger coneButton = operator.y();
        coneButton.onTrue(new GrabMasterCommand(grabber, false));
        Trigger cubeButton = operator.x();
        cubeButton.onTrue(new GrabMasterCommand(grabber, true));
		*/
	}

	public Command getAutonomousCommand() {
		AutoCommandSwitcher.setAutoCommands(new Command[] {
			//PUT COMMANDS HERE
			null //MAKE SURE OPTION 0 IS NULL.
		});
		//return AutoCommandSwitcher.getAutoCommand(); //UNCOMMENT WHEN COMMANDS INTRODUCED. THANKS.
		return null;

	}
}