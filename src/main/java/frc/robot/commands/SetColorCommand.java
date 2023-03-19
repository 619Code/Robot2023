package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LedStripArm;

public class SetColorCommand extends CommandBase {
    private LedStripArm led;

    public SetColorCommand(LedStripArm led) {
        this.led = led;

        addRequirements(led);
    }

    @Override
    public void initialize() {
        led.isYellow = !led.isYellow;
    }

    @Override
    public void execute() {
        //led.setColorPurple();
    }

    @Override
    public void end(boolean interrupted) {
        //led.setColorYellow();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
