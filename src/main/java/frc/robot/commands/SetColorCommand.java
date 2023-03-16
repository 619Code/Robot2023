package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LedStrip;

public class SetColorCommand extends CommandBase {
    private LedStrip led;

    public SetColorCommand(LedStrip led) {
        this.led = led;

        addRequirements(led);
    }

    @Override
    public void initialize() {
        if(led.isYellow) {
            led.setColorPurple();
        } else {
            led.setColorYellow();
        }
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}