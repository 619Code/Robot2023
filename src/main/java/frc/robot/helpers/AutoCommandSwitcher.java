package frc.robot.helpers;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;

public class AutoCommandSwitcher {
    
    private static boolean[] autoword;
    private static Command[] commandList;

    private static GenericEntry OnesEntry;
    private static GenericEntry TwosEntry;


    
    public static void setAutoCommands(Command[] commands) {
        commandList = new Command[commands.length];
        for (int i = 0; i < commands.length; i ++) {
            commandList[i] = commands[i];
        }

    }

    public static void setAutoword() {
        autoword = new boolean[2];
        OnesEntry = Crashboard.toDashboard("Ones AutoSelect", false, Constants.AUTOS_TAB);
        TwosEntry = Crashboard.toDashboard("Twos AutoSelect", false, Constants.AUTOS_TAB);
        autoword[0] = OnesEntry.getBoolean(false);
        autoword[1] = TwosEntry.getBoolean(false);
    }
  
    public static void setAutoword(boolean[] in) {
        autoword = new boolean[in.length];
        for (int i = 0; i < in.length; i ++) {
            autoword[i] = in[i];
        }
    }

    public static int binaryDecoder(boolean[] num) {
        int newNum = 0;
        for (int i = 0; i < num.length; i ++) {
            int currentval = 0;
            if (num[i]) {
                currentval = 1;
            }
            newNum += (Math.pow(2, i) * currentval);
        }
        return newNum;
    }

    public static Command getAutoCommand() {
        return commandList[binaryDecoder(autoword)];
    }




    public static double FunnyAuto() {
        return 1/0;
    }
}
