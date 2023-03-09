package frc.robot.helpers;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;

public class AutoCommandSwitcher {
    
    private static GenericEntry autoChooser;
    private static int chosenValue;

    private static Command[] autoCommands;

    public static void loadAutoCommandSwitcher(Command[] toPropogate) {
        autoCommands = new Command[toPropogate.length];

        for (int i = 0; i < toPropogate.length; i ++) {
            autoCommands[i] = toPropogate[i];
        }

        autoChooser = Crashboard.toDashboard("Chosen Auto", 0, Constants.COMPETITON_TAB);
    }

    public static Command getAutoCommand() {
        return autoCommands[(int)Crashboard.clamp(0, autoCommands.length, autoChooser.getDouble(0))];}
        public static double FunnyAuto() {
            return 1/0;
    }
}
