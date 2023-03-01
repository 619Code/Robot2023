package frc.robot;

import frc.robot.commands.AutoLineupCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.PipelineSwitchingCommand;
import frc.robot.commands.ToggleDeployIntakeCommand;
import frc.robot.commands.arm.HingeZeroCommand;
import frc.robot.commands.arm.HoldArmCommand;
import frc.robot.commands.arm.MoveArmMasterCommand;
import frc.robot.commands.arm.MoveHingeCommand;
import frc.robot.commands.arm.MoveTelescopeCommand;
import frc.robot.commands.arm.TelescopeZeroCommand;
import frc.robot.commands.auto.AutoDriveCommand;
import frc.robot.commands.auto.AutoPlaceCommand;
import frc.robot.commands.auto.AutoZeroCommand;
import frc.robot.commands.grabber.GrabCommand;
import frc.robot.commands.grabber.GrabMasterCommand;
import frc.robot.commands.grabber.GrabZeroCommand;
import frc.robot.commands.grabber.ReleaseCommand;
import frc.robot.commands.intake.IntakeDefaultCommand;
import frc.robot.commands.intake.IntakeHolderCommand;
import frc.robot.commands.manuals.GrabManualCommand;
import frc.robot.commands.manuals.HingeManualCommand;
import frc.robot.commands.manuals.TelescopeManualCommand;
import frc.robot.helpers.AutoCommandSwitcher;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.helpers.enums.LineupPosition;
import frc.robot.helpers.enums.Pipeline;
import frc.robot.helpers.limelight.PipelineHelper;
import frc.robot.subsystems.Drivetrain;
// import io.github.oblarg.oblog.annotations.Log;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.arm.Hinge;
import frc.robot.subsystems.arm.Telescope;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.IntakeSub;
import frc.robot.subsystems.LedStrip;
import frc.robot.commands.SetColorCommand;
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

	private boolean TurnOnGrabber = false;
	private boolean TurnOnIntake = false;
	private boolean TurnOnArm = false;
	private boolean TurnOnDrive = true;
	private boolean IsTesting = true;

	public RobotContainer() {
		driver = new CommandXboxController(0);
		operator = new CommandXboxController(1);

		// Log Initial Status
		this.LogInitialStatus();

		if (TurnOnDrive) {
			drive = new Drivetrain();
			driveCommand = new DriveCommand(drive, driver);
			drive.setDefaultCommand(driveCommand);
		}
		
		limelight = new Limelight();
		PipelineHelper.limelight = limelight;
		//led = new LedStrip();

		if (TurnOnIntake) {
			intake = new IntakeSub();
			intake.setDefaultCommand(new IntakeDefaultCommand(intake));
		}

		if (TurnOnGrabber) {
			grabber = new Grabber();
			grabManualCommand = new GrabManualCommand(grabber, operator);
			grabber.setDefaultCommand(grabManualCommand);
		}

		if (TurnOnArm) {
			hinge = new Hinge();
			holdArmCommand = new HoldArmCommand(hinge);
			hinge.setDefaultCommand(holdArmCommand);
			/*hingeManualCommand = new HingeManualCommand(hinge, operator);
			hinge.setDefaultCommand(hingeManualCommand);*/

			telescope = new Telescope();
			telescopeManualCommand = new TelescopeManualCommand(telescope, operator);
			telescope.setDefaultCommand(telescopeManualCommand);
		}

		configureBindings();
	}

	private void LogInitialStatus()
	{
		Crashboard.toDashboard("Is Testing", IsTesting, Constants.STATUS_TAB);
		Crashboard.toDashboard("Arm On", this.TurnOnArm, Constants.STATUS_TAB);
		Crashboard.toDashboard("Drive On", this.TurnOnDrive, Constants.STATUS_TAB);
		Crashboard.toDashboard("Grabber On", this.TurnOnGrabber, Constants.STATUS_TAB);
		Crashboard.toDashboard("Intake On", this.TurnOnIntake, Constants.STATUS_TAB);		
	}

	private void configureBindings() {
		if (IsTesting)
			BindTests();
	}

	private void BindTests() {
		if (TurnOnArm)
			armTesting();

		if (TurnOnGrabber)
			grabberTesting();

		if (TurnOnIntake)
			intakeTesting();

		lineupTesting();
		limeLightPipelineTesting();
	}

	public void armTesting() {
		Trigger zeroTelescopeButton = operator.a();
		zeroTelescopeButton.whileTrue(new TelescopeZeroCommand(telescope));

		Trigger positionOneButton = operator.x();
		positionOneButton.onTrue(new MoveArmMasterCommand(hinge, telescope, ArmPosition.START));

		Trigger positionTwoButton = operator.b();
		positionTwoButton.onTrue(new MoveArmMasterCommand(hinge, telescope, ArmPosition.GRID_MID));

		Trigger positionThreeButton = operator.y();
		positionThreeButton.onTrue(new MoveArmMasterCommand(hinge, telescope, ArmPosition.GRID_HIGH));
	}

	public void lineupTesting() {
		Trigger driveButton = driver.start();
		driveButton.whileTrue(new AutoLineupCommand(drive, LineupPosition.CENTER));
	}

	public void limeLightPipelineTesting() {
		Trigger lefTrigger = driver.x();
		lefTrigger.onTrue(new PipelineSwitchingCommand(Pipeline.LEFT_PIPELINE)).debounce(1);

		Trigger cenTrigger = driver.y();
		cenTrigger.onTrue(new PipelineSwitchingCommand(Pipeline.CENTER_PIPELINE)).debounce(1);

		Trigger righTrigger = driver.b();
		righTrigger.onTrue(new PipelineSwitchingCommand(Pipeline.RRT_PIPELINE)).debounce(1);

		Trigger cameraTrigger = driver.a();
		cameraTrigger.onTrue(new PipelineSwitchingCommand(Pipeline.CAMERA_PIPELINE)).debounce(1);
	}

	public void intakeTesting() {
		
		// Deploy and undeploy intake
		Trigger swingtake = operator.b();
		swingtake.onTrue(new ToggleDeployIntakeCommand());

		// Once intake is deployed activate movement based on axis
		Trigger intakeMovement = operator.axisGreaterThan(2, 0.15);
		intakeMovement.onTrue(new IntakeHolderCommand(intake, operator));
	}

	public void grabberTesting() {

		Trigger zeroButton = operator.a();
		zeroButton.onTrue(new SequentialCommandGroup(
			new GrabZeroCommand(grabber),
			new ReleaseCommand(grabber)));

        Trigger grabButton = operator.y();
        grabButton.onTrue(new GrabMasterCommand(grabber));
		
		Trigger toggleLed = operator.x();
		toggleLed.onTrue(new SetColorCommand(led)).debounce(1);
	}

	public Command getAutonomousCommand() {
		/*
		AutoCommandSwitcher.setAutoCommands(new Command[] {
			//PUT COMMANDS HERE
			null //MAKE SURE OPTION 0 IS NULL.
		});
		//return AutoCommandSwitcher.getAutoCommand(); //UNCOMMENT WHEN COMMANDS INTRODUCED. THANKS.
		*/
		
		//return new AutoDriveCommand(drive, Constants.AUTO_DRIVE_DISTANCE);

		/*return new SequentialCommandGroup(
			new AutoPlaceCommand(grabber, hinge, telescope),
			new AutoZeroCommand(intake, grabber, hinge, telescope),
			new AutoDriveCommand(drive, Constants.AUTO_DRIVE_DISTANCE)
		);*/

		return null;
	}
}