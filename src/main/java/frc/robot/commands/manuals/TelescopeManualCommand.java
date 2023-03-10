package frc.robot.commands.manuals;

import frc.robot.Constants;
import frc.robot.helpers.Crashboard;
import frc.robot.subsystems.arm.Telescope;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class TelescopeManualCommand extends CommandBase {
    private Telescope telescope;
    private CommandXboxController controller;

    double telescopeSpeed;

    public TelescopeManualCommand(Telescope telescope, CommandXboxController controller) {
        this.telescope = telescope;
        this.controller = controller;

        addRequirements(telescope);
    }

    @Override
    public void execute() {
        telescopeSpeed = -controller.getRightY();
        Crashboard.toDashboard("Telescope Speed", telescopeSpeed, Constants.ARM_TAB);
        if(Math.abs(telescopeSpeed) > 0.05) {
            telescope.move(telescopeSpeed * Constants.TELESCOPE_SPEED);
        } else {
            telescope.stop();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}