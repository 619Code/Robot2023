package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.PipelineHelper;
import io.github.oblarg.oblog.Logger;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private RobotContainer robotContainer;
  private PowerDistribution pdh = new PowerDistribution(1, ModuleType.kRev);

  // private int counter;

  @Override
  public void robotInit() {
    robotContainer = new RobotContainer();
    Logger.configureLoggingAndConfig(robotContainer, false);

    // CameraServer.startAutomaticCapture();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    Logger.updateEntries();
    Crashboard.toDashboard("Total Current Draw", pdh.getTotalCurrent(), Constants.GameTab);
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void autonomousInit() {
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
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {

    // counter ++;

    // if (counter == 0) {
    //   PipelineHelper.SetCenterPipeline();
    // } else if (counter == 50) {
    //   PipelineHelper.SetRightPipeline();
    // } else if (counter == 100) {
    //   PipelineHelper.SetLeftPipeline();
    // } else if (counter == 150) {
    //   PipelineHelper.SetCenterPipeline();
    //   counter = 0;
    // }
    

    String gameData;
    gameData = DriverStation.getGameSpecificMessage();

    if (gameData.length() > 0) {
      switch (gameData.charAt(0)) {
        case 'B':
          // System.out.println("Blue Team");
          break;
        case 'G':
          // System.out.println("Error");
          break;
        case 'R':
          // System.out.println("Red Team");
          break;
        case 'Y':
          // System.out.println("Error");
          break;
        default:
          // System.out.println("Error");
          break;
      }
    } else {
      // System.out.println("No code received");
    }
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }
}
