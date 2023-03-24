package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

public final class Main {
  private Main() {}

  public static void main(String... args) {
    //System.out.println("YOUVE BEEN TROLLED!!!!111!111!111!1");
    RobotBase.startRobot(Robot::new);
  }
}
