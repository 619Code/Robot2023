package frc.robot.commands.grabber;

import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.ArmPositionHelper;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.subsystems.Grabber;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class ReleaseCommand extends CommandBase {
    private Grabber grabber;
    private boolean releasingCone;

    public ReleaseCommand(Grabber grabber) {
        this.grabber = grabber;

        addRequirements(grabber);
    }

    @Override
    public void initialize() {
        if(States.hasCone) {
            releasingCone = true;
        } else {
            releasingCone = false;
        }
    }

    @Override
    public void execute() {
        double speed = 0;

        if(releasingCone) {
            speed = Constants.RELEASE_SPEED_CONE;
        } else {
            if(ArmPositionHelper.currentPosition == ArmPosition.CUBE_HIGH) {
                speed = Constants.RELEASE_SPEED_CUBE_HIGH;
            } else {
                speed = Constants.RELEASE_SPEED_CUBE_MID;
            }
        }

        grabber.spin(speed);
    }

    @Override
    public void end(boolean interrupted) {
        grabber.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}