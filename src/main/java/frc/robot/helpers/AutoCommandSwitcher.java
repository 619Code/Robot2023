package frc.robot.helpers;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;

public class AutoCommandSwitcher {
    
    private static GenericEntry autoChooser;
    private static int chosenValue;
    private static SendableChooser chooser = new SendableChooser<String>();

    private static Command[] autoCommands;

    public static void loadAutoCommandSwitcher(Command[] toPropogate) {
        // autoCommands = new Command[toPropogate.length];

        // for (int i = 0; i < toPropogate.length; i ++) {
        //     autoCommands[i] = toPropogate[i];
        // }

        chooser.addOption("TEst", "test");
        chooser.addOption("TSESTENISt", "testing");
        chooser.setDefaultOption("TEst", "test");

        //autoChooser = Crashboard.toDashboard("Chosen Auto", chooser, Constants.COMPETITON_TAB, BuiltInWidgets.kComboBoxChooser);
        System.out.println("your mother");
    }

    public static Command getAutoCommand() {
        return autoCommands[(int)Crashboard.clamp(0, autoCommands.length, autoChooser.getDouble(0))];}
        public static double FunnyAuto() {
            return 1/0;
    }
}
