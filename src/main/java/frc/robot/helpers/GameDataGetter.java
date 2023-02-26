package frc.robot.helpers;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Constants;

public class GameDataGetter {
    private static String gameData;

    public static void getInfo() {
        gameData = DriverStation.getGameSpecificMessage();
    }

    public static char getAlliance(boolean pushtoCrashboard) {
        getInfo();
        if(gameData.length() > 0) {
            switch (gameData.charAt(0))
            {
                case 'B' :
                case 'R' :
                    if (pushtoCrashboard) {
                        Crashboard.toDashboard("Alliance", gameData.charAt(0), Constants.DRIVE_TAB);
                    }
                    return gameData.charAt(0);
                default :
                    System.out.println("Alliance Not Found");
                    return '#';
            }
        } else {
            System.out.println("Alliance Not Found");
            return '#';
        }
    }

}
