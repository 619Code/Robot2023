package frc.robot.commands.arm;

import frc.robot.Constants;
import frc.robot.subsystems.arm.Telescope;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TelescopeZeroCommand extends CommandBase {
    private Telescope telescope;

    private boolean zeroed;

    public TelescopeZeroCommand(Telescope telescope) {
        this.telescope = telescope;

        addRequirements(telescope);
    }

    public void initialize() {
        zeroed = false;
    }

    @Override
    public void execute() {
        if(telescope.contractedSwitchIsPressed()) {
            telescope.stop();
            telescope.zero();
            zeroed = true;
        } else {
            telescope.move(-Constants.TELESCOPE_ZERO_SPEED, true);
        }
    }

    @Override
    public void end(boolean interrupted) {
        telescope.stop();
    }

    @Override
    public boolean isFinished() {
        return zeroed;
    }
}