package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.LedStrip;

public class AlternateColorCommand extends CommandBase {
    private LedStrip led;

    private Timer toggleTimer;

    private boolean startingColorYellow;

    public AlternateColorCommand(LedStrip led) {
        this.led = led;

        toggleTimer = new Timer();

        addRequirements(led);
    }

    @Override
    public void initialize() {
        toggleTimer.reset();
        toggleTimer.start();

        startingColorYellow = led.isYellow;
    }

    @Override
    public void execute() {
        if(toggleTimer.hasElapsed(Constants.TOGGLE_INTERVAL)) {
            if(led.isYellow) {
                led.setColorPurple();
            } else {
                led.setColorYellow();
            }

            toggleTimer.reset();
            toggleTimer.start();
        }
    }

    @Override
    public void end(boolean interrupted) {
        if(startingColorYellow) {
            led.setColorYellow();
        } else {
            led.setColorPurple();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}