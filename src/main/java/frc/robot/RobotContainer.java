package frc.robot;

import frc.robot.commands.AutoLineupCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.arm.HoldArmCommand;
import frc.robot.commands.arm.MoveArmMasterCommand;
import frc.robot.commands.arm.MoveHingeCommand;
import frc.robot.commands.arm.MoveTelescopeCommand;
import frc.robot.commands.arm.TelescopeZeroCommand;
import frc.robot.commands.grabber.GrabMasterCommand;
import frc.robot.commands.grabber.GrabberZeroMasterCommand;
import frc.robot.commands.manuals.GrabManualCommand;
import frc.robot.commands.manuals.HingeManualCommand;
import frc.robot.commands.manuals.TelescopeManualCommand;
import frc.robot.helpers.AutoCommandSwitcher;
import frc.robot.helpers.Position;
import frc.robot.subsystems.Drivetrain;
import io.github.oblarg.oblog.annotations.Log;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.arm.Hinge;
import frc.robot.subsystems.arm.Telescope;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.IntakeSub;
import frc.robot.subsystems.LedStrip;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
	private CommandXboxController driver;
	private CommandXboxController operator;
	
	private DriveCommand driveCommand;
	private GrabManualCommand grabManualCommand;
	private HoldArmCommand holdArmCommand;
	private HingeManualCommand hingeManualCommand;
	private TelescopeManualCommand telescopeManualCommand;

	private Drivetrain drive;
	private IntakeSub intake;
	private Limelight limelight;
	private Grabber grabber;
	private Hinge hinge;
	private Telescope telescope;
	private LedStrip led;

	private String gameData;

	public RobotContainer() {
		driver = new CommandXboxController(0);
		operator = new CommandXboxController(1);

        /*drive = new Drivetrain();
        driveCommand = new DriveCommand(drive, driver);
        drive.setDefaultCommand(driveCommand);*/
		
		//limelight = new Limelight();*/

		//led = new LedStrip();
		intake = new IntakeSub();
		intake.setDefaultCommand(new IntakeDefaultCommand(intake));

		/*grabber = new Grabber();
		grabManualCommand = new GrabManualCommand(grabber, operator);
        grabber.setDefaultCommand(grabManualCommand);*/

		hinge = new Hinge();
		holdArmCommand = new HoldArmCommand(hinge);
		hinge.setDefaultCommand(holdArmCommand);
		/*hingeManualCommand = new HingeManualCommand(hinge, operator);
		hinge.setDefaultCommand(hingeManualCommand);*/

		telescope = new Telescope();
		telescopeManualCommand = new TelescopeManualCommand(telescope, operator);
		telescope.setDefaultCommand(telescopeManualCommand);

		configureBindings();
	}

	private void configureBindings() {
		armTesting();
		//lineupTesting();
		//grabberTesting();
	}

	public void armTesting() {
		Trigger zeroTelescopeButton = operator.a();
		zeroTelescopeButton.whileTrue(new TelescopeZeroCommand(telescope));

		Trigger positionOneButton = operator.x();
		positionOneButton.onTrue(new MoveArmMasterCommand(hinge, telescope, Position.START));

		Trigger positionTwoButton = operator.b();
		positionTwoButton.onTrue(new MoveArmMasterCommand(hinge, telescope, Position.GRID_MID));

		Trigger positionThreeButton = operator.y();
		positionThreeButton.onTrue(new MoveArmMasterCommand(hinge, telescope, Position.GRID_HIGH));
	}

	public void lineupTesting() {
		Trigger driveButton = driver.b();
		driveButton.whileTrue(new AutoLineupCommand(drive));
	}

	public void grabberTesting() {
		Trigger zeroGrabberButton = operator.a();
        zeroGrabberButton.onTrue(new GrabberZeroMasterCommand(grabber));

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