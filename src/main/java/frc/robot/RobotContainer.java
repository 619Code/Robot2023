package frc.robot;

import frc.robot.commands.AutoLineupCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.GrabMasterCommand;
import frc.robot.commands.manuals.GrabManualCommand;
import frc.robot.commands.manuals.HingeManualCommand;
import frc.robot.commands.manuals.TelescopeManualCommand;
import frc.robot.commands.zeroers.GrabberZeroMasterCommand;
import frc.robot.commands.zeroers.TelescopeZeroCommand;
import frc.robot.helpers.AutoCommandSwitcher;
import frc.robot.subsystems.Drivetrain;
import io.github.oblarg.oblog.annotations.Log;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.arm.Hinge;
import frc.robot.subsystems.arm.Telescope;
import frc.robot.subsystems.Grabber;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
	private CommandXboxController driver;
	private CommandXboxController operator;
	
	private DriveCommand driveCommand;
	private GrabManualCommand grabManualCommand;
	private HingeManualCommand hingeManualCommand;
	private TelescopeManualCommand telescopeManualCommand;

	private Drivetrain drive;
	private Limelight limelight;
	private Grabber grabber;
	private Hinge hinge;
	private Telescope telescope;

	private String gameData;

	public RobotContainer() {
		driver = new CommandXboxController(0);
		operator = new CommandXboxController(1);

        /*drive = new Drivetrain();
        driveCommand = new DriveCommand(drive, driver);
        drive.setDefaultCommand(driveCommand);*/
		
		//limelight = new Limelight(driver);

		/*grabber = new Grabber();
		grabManualCommand = new GrabManualCommand(grabber, operator);
        grabber.setDefaultCommand(grabManualCommand);*/

		hinge = new Hinge();
		hingeManualCommand = new HingeManualCommand(hinge, operator);
		hinge.setDefaultCommand(hingeManualCommand);

		telescope = new Telescope();
		telescopeManualCommand = new TelescopeManualCommand(telescope, operator);
		telescope.setDefaultCommand(telescopeManualCommand);

		configureBindings();
	}

	private void configureBindings() {
		Trigger zeroTelescopeButton = operator.a();
		zeroTelescopeButton.whileTrue(new TelescopeZeroCommand(telescope));

		/*Trigger driveButton = driver.b();
		driveButton.whileTrue(new AutoLineupCommand(drive));*/

		/*Trigger zeroGrabberButton = operator.a();
        zeroGrabberButton.onTrue(new ZeroMasterCommand(grabber));
        Trigger coneButton = operator.y();
        coneButton.onTrue(new GrabMasterCommand(grabber, false));
        Trigger cubeButton = operator.x();
        cubeButton.onTrue(new GrabMasterCommand(grabber, true));*/
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