package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.helpers.PipelineHelper;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private RobotContainer robotContainer;

  // private int counter;

  @Override
  public void robotInit() {
    robotContainer = new RobotContainer();

    //CameraServer.startAutomaticCapture();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = robotContainer.getAutonomousCommand();

    

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

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
if(gameData.length() > 0)
{
  switch (gameData.charAt(0))
  {
    case 'B' :
      System.out.println("Blue Team");
      break;
    case 'G' :
      System.out.println("Oopsies! (>/////<) Something bwoke :'{ Check yuow cOwOde and twy again~! (^w^)");
      break;
    case 'R' :
      //System.out.println("Red Team");
      break;
    case 'Y' :
      System.out.println("Oopsies! (>/////<) Something bwoke :'{ Check yuow cOwOde and twy again~! (^w^)");
      break;
    default :
      System.out.println("Oopsies! (>/////<) Something bwoke :'{ Check yuow cOwOde and twy again~! (^w^)");
      break;
  }
} else {
  //System.out.println("no cowde wecieved!!11!!1 twy again lateww (>//////<)");
}
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}
}
