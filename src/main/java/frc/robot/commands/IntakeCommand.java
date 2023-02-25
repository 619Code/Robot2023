package frc.robot.commands;

import frc.robot.helpers.IntakeArm;
import frc.robot.subsystems.IntakeSub;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class IntakeCommand extends CommandBase {
  private final IntakeSub subsystem;
  private CommandXboxController controller;

  public IntakeCommand(IntakeSub subsystem, CommandXboxController controller) {
    this.subsystem = subsystem;
    this.controller = controller;

    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    double leftRaw = controller.getLeftTriggerAxis();
    double rightRaw = controller.getRightTriggerAxis();

    subsystem.setSpeed(leftRaw, IntakeArm.LeftArm);
    subsystem.setSpeed(rightRaw,IntakeArm.RightArm);
    
    if (this.controller.getRightX() > 0.15) {
      this.subsystem.ActivateWheels(0.2);
    } else { 
      this.subsystem.ActivateWheels(0);
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}