package frc.robot;

import frc.robot.commands.AutoLineupCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.arm.HingeAdjustCommand;
import frc.robot.commands.arm.HingeZeroCommand;
import frc.robot.commands.arm.HoldArmCommand;
import frc.robot.commands.arm.MoveArmMasterCommand;
import frc.robot.commands.auto.AutoDriveCommand;
import frc.robot.commands.auto.PreMatchSettingsCommand;
import frc.robot.commands.grabber.GrabMasterCommand;
import frc.robot.commands.intake.IntakeDefaultCommand;
import frc.robot.commands.intake.IntakeHolderCommand;
import frc.robot.commands.intake.ToggleDeployIntakeCommand;
import frc.robot.commands.manuals.GrabManualCommand;
import frc.robot.commands.manuals.HingeManualCommand;
import frc.robot.commands.manuals.TelescopeManualCommand;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.helpers.enums.LineupPosition;
import frc.robot.helpers.limelight.PipelineHelper;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.arm.Hinge;
import frc.robot.subsystems.arm.Telescope;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.IntakeSub;
import frc.robot.subsystems.LedStrip;
import frc.robot.commands.SetColorCommand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
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
	private HingeAdjustCommand hingeAdjustCommand;
	private TelescopeManualCommand telescopeManualCommand;

	private Drivetrain drive;
	private IntakeSub intake;
	private Limelight limelight;
	private Grabber grabber;
	private Hinge hinge;
	private Telescope telescope;
	private LedStrip led;

	private boolean TurnOnGrabber = true;
	private boolean TurnOnIntake = true;
	private boolean TurnOnArm = true;
	private boolean TurnOnDrive = true;
	private boolean IsTesting = true;

	public RobotContainer() {
		driver = new CommandXboxController(3);
		operator = new CommandXboxController(1);

		// Log Initial Status
		this.LogInitialStatus();

		limelight = new Limelight();
		PipelineHelper.limelight = limelight;
		PipelineHelper.setCameraPipeline();

		led = new LedStrip();

		if (TurnOnDrive) {
			drive = new Drivetrain();
			driveCommand = new DriveCommand(drive, driver);
			drive.setDefaultCommand(driveCommand);
		}

		if (TurnOnIntake) {
			intake = new IntakeSub();
			intake.setDefaultCommand(new IntakeDefaultCommand(intake));
		}

		if (TurnOnGrabber) {
			grabber = new Grabber();

			/*grabManualCommand = new GrabManualCommand(grabber, operator);
			grabber.setDefaultCommand(grabManualCommand);*/
		}

		if (TurnOnArm) {
			hinge = new Hinge();
			hingeAdjustCommand = new HingeAdjustCommand(hinge, operator);
			hinge.setDefaultCommand(hingeAdjustCommand);

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
		if (IsTesting) {
			BindTests();
		} else {
			competitionBindings();
		}
	}

	private void competitionBindings() {
		armBindings();
		intakeBindings();
		grabberBindings();
		lineupTesting();
		preMatchZeroing();
	}

	private void BindTests() {
		/*Trigger zeroAllButton = operator.back();
		zeroAllButton.onTrue(new PreMatchSettingsCommand(intake, grabber, hinge, telescope));

		armBindings();*/
	}

	public void armBindings() {
		Trigger startPositionButton = operator.start();
		startPositionButton.onTrue(new MoveArmMasterCommand(hinge, telescope, ArmPosition.START));

		Trigger pickupLowButton = operator.a();
		pickupLowButton.onTrue(new MoveArmMasterCommand(hinge, telescope, ArmPosition.PICKUP_LOW));

		Trigger pickupHighButton = operator.y();
		pickupHighButton.onTrue(new MoveArmMasterCommand(hinge, telescope, ArmPosition.PICKUP_HIGH));

		Trigger placeMidButton = operator.x();
		placeMidButton.onTrue(new MoveArmMasterCommand(hinge, telescope, ArmPosition.GRID_MID));

		Trigger placeHighButton = operator.b();
		placeHighButton.onTrue(new MoveArmMasterCommand(hinge, telescope, ArmPosition.GRID_HIGH));

		/*Trigger placeHighButton = operator.b();
		placeHighButton.onTrue(new MoveArmMasterCommand(hinge, telescope, ArmPosition.GRID_HIGH));*/
	}

	public void intakeBindings() {
		// Deploy and undeploy intake
		Trigger deployIntakeButton = operator.leftBumper();
		deployIntakeButton.onTrue(new ToggleDeployIntakeCommand());

		// Once intake is deployed activate movement based on axis
		Trigger intakeButton = operator.leftTrigger(0.15);
		intakeButton.whileTrue(new IntakeHolderCommand(intake, operator));
	}

	public void grabberBindings() {
        Trigger grabButton = operator.rightBumper();
        grabButton.onTrue(new GrabMasterCommand(grabber, led));

		Trigger toggleLed = operator.back();
		toggleLed.onTrue(new SetColorCommand(led)).debounce(0.5);
	}

	public void preMatchZeroing() {
		Trigger zeroAllButton = driver.back();
		zeroAllButton.onTrue(new PreMatchSettingsCommand(intake, grabber, hinge, telescope));

		//UNDO
		/*Trigger zeroAllButton = driver.back();
		zeroAllButton.onTrue(new AutoZeroCommand(intake, grabber, hinge, telescope));*/
	}

	public void lineupTesting() {
		Trigger leftButton = driver.x();
		leftButton.whileTrue(new AutoLineupCommand(drive, LineupPosition.LEFT));

		Trigger centerButton = driver.y();
		centerButton.whileTrue(new AutoLineupCommand(drive, LineupPosition.CENTER));

		Trigger rightButton = driver.b();
		rightButton.whileTrue(new AutoLineupCommand(drive, LineupPosition.RIGHT));
	}

	public Command getAutonomousCommand() {
		/*return new SequentialCommandGroup(
			new RunCommand(() -> hinge.move(-Constants.HINGE_ZERO_SPEED, true),hinge).withTimeout(7),
			new InstantCommand(() -> hinge.zero(),hinge),
			new MoveArmMasterCommand(hinge, telescope, ArmPosition.START)//,
			//new AutoDriveCommand(drive, Constants.AUTO_DRIVE_DISTANCE)
		);*/

		return new SequentialCommandGroup(
			new HingeZeroCommand(hinge),
			new MoveArmMasterCommand(hinge, telescope, ArmPosition.START)//,
			//new AutoDriveCommand(drive, Constants.AUTO_DRIVE_DISTANCE)
		);

		/*return new SequentialCommandGroup(
			new AutoPlaceCommand(grabber, hinge, telescope),
			new AutoDriveCommand(drive, Constants.AUTO_DRIVE_DISTANCE)
		);*/

		//return new AutoDriveCommand(drive, Constants.AUTO_DRIVE_DISTANCE);

		//return null;
	}
}