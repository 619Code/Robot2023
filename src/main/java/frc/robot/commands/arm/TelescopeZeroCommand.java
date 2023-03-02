package frc.robot.commands.arm;

import frc.robot.Constants;
import frc.robot.subsystems.arm.Telescope;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class TelescopeZeroCommand extends CommandBase {
    private Telescope telescope;
    private int counter;

    public TelescopeZeroCommand(Telescope telescope) {
        this.telescope = telescope;
        counter = 0;

        addRequirements(telescope);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        counter ++;
        if(telescope.contractedSwitchIsPressed() && counter <= 500) {
            telescope.stop();
            telescope.zero();
            telescope.zeroed = true;
        } else {
            telescope.move(-Constants.TELESCOPE_ZERO_SPEED);
        }
    }

    @Override
    public void end(boolean interrupted) {
        telescope.stop();
    }

    @Override
    public boolean isFinished() {
        return telescope.zeroed;
    }
}