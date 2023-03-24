package frc.robot;

import frc.robot.commands.AlternateColorCommand;
import frc.robot.commands.AutoLineupCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.PreMatchSettingsCommand;
import frc.robot.commands.arm.MoveArmMasterCommand;
import frc.robot.commands.arm.hinge.HingeAdjustCommand;
import frc.robot.commands.arm.hinge.HingeZeroCommand;
import frc.robot.commands.arm.hinge.HoldHingeCommand;
import frc.robot.commands.arm.telescope.TelescopeZeroCommand;
import frc.robot.commands.arm.wrist.HoldWristCommand;
import frc.robot.commands.auto.AutoDriveCommand;
import frc.robot.commands.auto.AutoPlaceCommand;
import frc.robot.commands.grabber.GrabCommand;
import frc.robot.commands.grabber.GrabDefaultCommand;
import frc.robot.commands.grabber.ReleaseCommand;
import frc.robot.commands.manuals.HingeManualCommand;
import frc.robot.commands.manuals.HingeManualDashboardCommand;
import frc.robot.commands.manuals.TelescopeManualCommand;
import frc.robot.commands.manuals.WristManualCommand;
import frc.robot.commands.manuals.WristManualDashboardCommand;
import frc.robot.helpers.ArmLogicAssistant;
import frc.robot.helpers.ArmPositionHelper;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.helpers.enums.AutoOption;
import frc.robot.helpers.enums.LineupPosition;
import frc.robot.helpers.limelight.PipelineHelper;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.arm.Hinge;
import frc.robot.subsystems.arm.Telescope;
import frc.robot.subsystems.arm.Wrist;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.LedStrip;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.commands.ToggleColorCommand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
	private CommandXboxController driver;
	private CommandXboxController operator;
	
	private DriveCommand driveCommand;
	private GrabDefaultCommand grabDefaultCommand;
	private HingeAdjustCommand hingeAdjustCommand;
	private TelescopeManualCommand telescopeManualCommand;
	private HoldWristCommand holdWristCommand;

	private Drivetrain drive;
	private Limelight limelight;
	private Grabber grabber;
	private Hinge hinge;
	private Telescope telescope;
	private Wrist wrist;
	private LedStrip led;

	private boolean TurnOnGrabber = true;
	private boolean TurnOnArm = true;
	private boolean TurnOnDrive = false;
	private boolean IsTesting = true;

	private SendableChooser<String> autoOptions = new SendableChooser<String>();
	ComplexWidget optionEntry;

	public RobotContainer() {
		driver = new CommandXboxController(3);
		operator = new CommandXboxController(1);

		// Log Initial Status
		LogInitialStatus();

		limelight = new Limelight();
		PipelineHelper.limelight = limelight;
		PipelineHelper.setCameraPipeline();

		//led = new LedStrip();

		if (TurnOnDrive) {
			drive = new Drivetrain();
			driveCommand = new DriveCommand(drive, driver);
			drive.setDefaultCommand(driveCommand);
		}

		if (TurnOnGrabber) {
			grabber = new Grabber();
			/*grabDefaultCommand = new GrabDefaultCommand(grabber);
			grabber.setDefaultCommand(grabDefaultCommand);*/
		}

		if (TurnOnArm) {
			hinge = new Hinge();
			/*HingeManualDashboardCommand hingeManualDashboardCommand = new HingeManualDashboardCommand(hinge);
			hinge.setDefaultCommand(hingeManualDashboardCommand);*/
			HoldHingeCommand holdHingeCommand = new HoldHingeCommand(hinge, true);
			hinge.setDefaultCommand(holdHingeCommand);

			telescope = new Telescope();
			telescopeManualCommand = new TelescopeManualCommand(telescope, operator);
			telescope.setDefaultCommand(telescopeManualCommand);

			wrist = new Wrist();
			/*holdWristCommand = new HoldWristCommand(wrist);
			wrist.setDefaultCommand(holdWristCommand);*/
			//WristManualCommand wristManualCommand = new WristManualCommand(wrist, driver);
			//var wristManualCommand = new WristManualNewCommand(wrist);
			WristManualDashboardCommand wristManualDashboardCommand = new WristManualDashboardCommand(wrist);
			wrist.setDefaultCommand(wristManualDashboardCommand);
		}

		configureBindings();
		configureAutoSettings();
	}

	private void LogInitialStatus()
	{
		Crashboard.toDashboard("Is Testing", IsTesting, Constants.STATUS_TAB);
		Crashboard.toDashboard("Arm On", this.TurnOnArm, Constants.STATUS_TAB);
		Crashboard.toDashboard("Drive On", this.TurnOnDrive, Constants.STATUS_TAB);
		Crashboard.toDashboard("Grabber On", this.TurnOnGrabber, Constants.STATUS_TAB);	
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
		grabberBindings();
		lineupTesting();
		preMatchZeroing();
	}

	private void BindTests() {
		armBindings();

		Trigger grabButton = operator.leftBumper();
        grabButton.whileTrue(new GrabCommand(grabber));

		Trigger releaseButton = operator.rightBumper();
        releaseButton.whileTrue(new ReleaseCommand(grabber));
	}

	public void armBindings() {
		Trigger startPositionButton = operator.start();
		startPositionButton.onTrue(moveArmMasterCommandFactory(ArmPosition.START));

		Trigger pickupLowButton = operator.a();
		pickupLowButton.onTrue(moveArmMasterCommandFactory(ArmPosition.PICKUP_LOW));

		Trigger pickupHighButton = operator.y();
		pickupHighButton.onTrue(moveArmMasterCommandFactory(ArmPosition.PICKUP_HIGH));

		Trigger placeMidButton = operator.x();
		placeMidButton.onTrue(moveArmMasterCommandFactory(ArmPosition.GRID_MID));

		Trigger placeHighButton = operator.b();
		placeHighButton.onTrue(moveArmMasterCommandFactory(ArmPosition.GRID_HIGH));
	}

	public Command moveArmMasterCommandFactory(ArmPosition goal) {
		return new SequentialCommandGroup(
			new InstantCommand(() -> Crashboard.toDashboard("Moving", true, Constants.ARM_TAB)),
			new InstantCommand(() -> {
                ArmPositionHelper.hingeAdjustment = 0;
                ArmLogicAssistant.updatePositions(goal);
                ArmPositionHelper.currentPosition = goal;
            }),
			(new MoveArmMasterCommand(hinge, telescope, wrist, grabber, goal)).until(ArmLogicAssistant::atBothPositions),
			new InstantCommand(() -> Crashboard.toDashboard("Moving", false, Constants.ARM_TAB))
		);
	}

	public void grabberBindings() {
        Trigger grabButton = operator.leftBumper();
        grabButton.whileTrue(new ParallelDeadlineGroup(
			new GrabCommand(grabber),
			new AlternateColorCommand(led)
		));

		Trigger releaseButton = operator.rightBumper();
        releaseButton.whileTrue(new ReleaseCommand(grabber));

		Trigger toggleLed = operator.back();
		toggleLed.onTrue(new ToggleColorCommand(led)).debounce(0.5);
	}

	public void preMatchZeroing() {
		Trigger zeroAllButton = driver.back();
		zeroAllButton.onTrue(new PreMatchSettingsCommand(hinge, telescope, wrist, grabber));
	}

	public void lineupTesting() {
		Trigger leftButton = driver.x();
		leftButton.whileTrue(new AutoLineupCommand(drive, LineupPosition.LEFT));

		Trigger centerButton = driver.y();
		centerButton.whileTrue(new AutoLineupCommand(drive, LineupPosition.CENTER));

		Trigger rightButton = driver.b();
		rightButton.whileTrue(new AutoLineupCommand(drive, LineupPosition.RIGHT));
	}

	private void configureAutoSettings() {
		autoOptions.addOption("Place", "Place");
		autoOptions.addOption("Drive", "Drive");
		autoOptions.addOption("Null", "Null");
		autoOptions.setDefaultOption("Null", "Null");
		optionEntry = Crashboard.AddChooser("Selected Auto", autoOptions, Constants.COMPETITON_TAB, BuiltInWidgets.kComboBoxChooser);
	}

	public Command getAutonomousCommand() {
		AutoOption chosenAuto = AutoOption.valueOf(autoOptions.getSelected());

		switch (chosenAuto) {
			case Place: 
				return new SequentialCommandGroup(
					new AutoPlaceCommand(grabber, hinge, telescope, wrist),
					new AutoDriveCommand(drive, Constants.AUTO_DRIVE_DISTANCE)
				);
			case Drive:
				return new AutoDriveCommand(drive, Constants.AUTO_DRIVE_DISTANCE);
			case Null:
				return null;
			default:
				return null;
		}
	}

	public void startupActions() {
		if(telescope != null) {
			new TelescopeZeroCommand(telescope).schedule();
		}

		if(hinge != null) {
			hinge.zero();
		}

		if (this.wrist != null)
		{
			this.wrist.zero();
		}
	}
}