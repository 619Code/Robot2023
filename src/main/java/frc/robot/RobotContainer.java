package frc.robot;

import frc.robot.Constants;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {


	private XboxController driver;
	private DriveCommand driveCommand;
	private Drivetrain drive;

	public RobotContainer() {
		driver = new XboxController(0);

        drive = new Drivetrain();
        driveCommand = new DriveCommand(drive, driver);
        drive.setDefaultCommand(driveCommand);
		configureBindings();
	}

	private void configureBindings() {

	}

	public Command getAutonomousCommand() {
		return null;
	}
}