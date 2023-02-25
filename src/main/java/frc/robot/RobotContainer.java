package frc.robot;

import frc.robot.commands.AutoLineupCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ToggleDeployIntakeCommand;
import frc.robot.commands.grabber.GrabManualCommand;
import frc.robot.commands.grabber.GrabZeroCommand;
import frc.robot.commands.intake.IntakeCommand;
import frc.robot.commands.intake.IntakeDefaultCommand;
import frc.robot.commands.intake.IntakeHolderCommand;
import frc.robot.commands.PipelineSwitchingCommand;
import frc.robot.commands.ReleaseCommand;
import frc.robot.commands.SetColorCommand;
import frc.robot.commands.masters.GrabMasterCommand;
import frc.robot.helpers.AutoCommandSwitcher;
import frc.robot.subsystems.Drivetrain;
import io.github.oblarg.oblog.annotations.Log;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.IntakeSub;
import frc.robot.subsystems.LedStrip;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
	private CommandXboxController driver;
	private CommandXboxController operator;
	
	private DriveCommand driveCommand;
	private GrabManualCommand grabManualCommand;

	private Drivetrain drive;
	private IntakeSub intake;
	private Limelight limelight;
	private Grabber grabber;
	private LedStrip led;

	private String gameData;

	public RobotContainer() {
		driver = new CommandXboxController(0);
		operator = new CommandXboxController(1);

        /*drive = new Drivetrain();
        driveCommand = new DriveCommand(drive, driver);
        drive.setDefaultCommand(driveCommand);
		
		limelight = new Limelight();*/

		//led = new LedStrip();
		intake = new IntakeSub();
		intake.setDefaultCommand(new IntakeDefaultCommand(intake));

		/*grabber = new Grabber();
		grabManualCommand = new GrabManualCommand(grabber, operator);
        grabber.setDefaultCommand(grabManualCommand);*/
		
		configureBindings();
	}

	private void configureBindings() {
		/*Trigger driveButton = driver.b();
		driveButton.whileTrue(new AutoLineupCommand(drive));*/

		Trigger zeroButton = operator.a();
		zeroButton.onTrue(new SequentialCommandGroup(
			new GrabZeroCommand(grabber),
			new ReleaseCommand(grabber)));

        Trigger grabButton = operator.y();
        grabButton.onTrue(new GrabMasterCommand(grabber));
		
		/*Trigger lefTrigger = operator.x();
		lefTrigger.toggleOnTrue(new PipelineSwitchingCommand(0));

		Trigger cenTrigger = operator.y();
		cenTrigger.toggleOnTrue(new PipelineSwitchingCommand(1));

		Trigger righTrigger = operator.b();
		righTrigger.toggleOnTrue(new PipelineSwitchingCommand(2));*/

		/*Trigger toggleLed = operator.x();
		toggleLed.onTrue(new SetColorCommand(led)).debounce(1);*/
		Trigger swingtake = operator.b();
		//swingtake.onTrue(new ToggleDeployIntakeCommand());
		swingtake.onTrue(new ToggleDeployIntakeCommand());
		Trigger intakeMovement = operator.axisGreaterThan(2, 0.15);
		intakeMovement.onTrue(new IntakeHolderCommand(intake, operator));

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