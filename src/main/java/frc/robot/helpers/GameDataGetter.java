package frc.robot.helpers;

import edu.wpi.first.wpilibj.DriverStation;

public class GameDataGetter {
    private static String gameData;

    public static void getInfo() {
        gameData = DriverStation.getGameSpecificMessage();
    }

    public static char getAlliance(boolean pushtoCrashboard) {
        getInfo();
        if(gameData.length() > 0)
        {
        switch (gameData.charAt(0))
        {
            case 'B' :
            case 'R' :
            if (pushtoCrashboard) Crashboard.toDashboard("Alliance", gameData.charAt(0));
            return gameData.charAt(0);
            default :
            System.out.println("Sowwy, no awwiance hewwe,,, (>//////<)");
            return '#';
        }
        } else {
            System.out.println("Sowwy, no awwiance hewwe,,, (>//////<)");
            return '#';
        }
    }

}
