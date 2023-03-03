package frc.robot.commands.arm;

import frc.robot.Constants;
import frc.robot.subsystems.arm.Hinge;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class HingeZeroCommand extends CommandBase {
    private Hinge hinge;
    private Timer timer;
    private double zeroTimerMaxTime = 10;

    public HingeZeroCommand(Hinge hinge) {
        this.hinge = hinge;
        timer = new Timer();
        timer.start();

        addRequirements(hinge);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(!timer.hasElapsed(zeroTimerMaxTime)) {
            if(hinge.switchIsPressed()) {
                hinge.stop();
                hinge.zero();
                hinge.zeroed = true;
            } else {
                hinge.move(-Constants.HINGE_ZERO_SPEED);
            }
        } else {
            hinge.stop();
            end(true);
        }
        
    }

    @Override
    public void end(boolean interrupted) {
        hinge.stop();
    }

    @Override
    public boolean isFinished() {
        return hinge.zeroed;
    }
}