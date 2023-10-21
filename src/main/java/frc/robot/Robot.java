package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.helpers.Crashboard;
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private RobotContainer robotContainer;
  private PowerDistribution pdh = new PowerDistribution(1, ModuleType.kRev);

  // private int counter;

  @Override
  public void robotInit() {
    robotContainer = new RobotContainer();

    // For Oblog uncomment, othewise use the Crashboard
    //Logger.configureLoggingAndConfig(robotContainer, false);
    States.isEnabled = false;
    States.inAuto = false;
    CameraServer.startAutomaticCapture();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
    States.isEnabled = false;
    States.inAuto = false;
  }

  @Override
  public void disabledPeriodic() {
    
  }

  @Override
  public void autonomousInit() {
    States.isEnabled = true;
    States.inAuto = true;
    Crashboard.toDashboard("Autonomous", States.inAuto, Constants.STATUS_TAB);

    m_autonomousCommand = robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
    
  }

  @Override
  public void teleopInit() {
    States.isEnabled = true;
    States.inAuto = false;
    
    Crashboard.toDashboard("Autonomous", States.inAuto, Constants.STATUS_TAB);

    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }

    robotContainer.startupActions();
  }

  @Override
  public void teleopPeriodic() {
    States.isEnabled = true;
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }
}
