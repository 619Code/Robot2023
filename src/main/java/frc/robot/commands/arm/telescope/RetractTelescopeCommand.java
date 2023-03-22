package frc.robot.commands.arm.telescope;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.helpers.ArmLogicAssistant;
import frc.robot.subsystems.arm.Telescope;

public class RetractTelescopeCommand extends CommandBase {
    private Telescope telescope;
    private boolean retracted;

    public RetractTelescopeCommand(Telescope telescope) {
        this.telescope = telescope;

        retracted = false;

        addRequirements(telescope);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        System.out.println("Retracting");
        retracted = telescope.retractFull();
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Stopped");
        telescope.stop();
    }

    @Override
    public boolean isFinished() {
        return ArmLogicAssistant.atHingePosition();
    }
}
