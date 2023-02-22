package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LedStrip;

public class SetColorCommand extends CommandBase {
    private LedStrip led;
    private boolean done;

    public SetColorCommand(LedStrip led) {
        this.led = led;
        done = false;

        addRequirements(led);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(led.isYellow) {
            led.setColorPurple();
        } else {
            led.setColorYellow();
        }
        done = true;
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return done;
    }
}
