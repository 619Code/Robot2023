package frc.robot.helpers;

import edu.wpi.first.wpilibj2.command.Command;

public class AutoCommandSwitcher {
    
    private static boolean[] autoword;
    private static Command[] commandList;

    
    public static void setAutoCommands(Command[] commands) {
        commandList = new Command[commands.length];
        for (int i = 0; i < commands.length; i ++) {
            commandList[i] = commands[i];
        }

    }

    public static void setAutoword() {
        autoword = new boolean[2];
        autoword[0] = Crashboard.snagBoolean("Ones - AutoSelect");
        autoword[1] = Crashboard.snagBoolean("Twos - AutoSelect");
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
