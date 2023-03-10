package frc.robot.helpers.limelight;

//import edu.wpi.first.networktables.GenericEntry;
import frc.robot.Constants;
import frc.robot.helpers.Crashboard;

public class LimelightDataStorer {
    private static int archived = 0;
    private static final int ARCHIVE_MAX = 10;

    private static boolean validTarget;
    private static double[] simplePosition;

    private static boolean[] validTargetArchive = new boolean[ARCHIVE_MAX];
    private static double[][] simplePoseArchive = new double[ARCHIVE_MAX][3];

    private static boolean validTargetAverage;
    private static double[] simplePoseAverage = new double[3];

    //uncomment these to pull data back from dashboard
    //private static GenericEntry txEntry, xPosEntry, yPosEntry, zPosEntry;
    
    public static void update(boolean cameraMode, boolean validTarget, double[] simplePosition) {
        LimelightDataStorer.validTarget = validTarget;
        LimelightDataStorer.simplePosition = simplePosition;

        if(cameraMode) {
            Crashboard.toDashboard("Camera Mode", true, Constants.LIMELIGHT_TAB);

            for(int a = 0; a < ARCHIVE_MAX; a++) {
                validTargetArchive[a] = false;

                for(int b = 0; b < 3; b++) {
                    simplePoseArchive[a][b] = 0.0;
                }
            }
        } else {
            Crashboard.toDashboard("Camera Mode", false, Constants.LIMELIGHT_TAB);

            if(archived != 0 && validTarget && !isDifferent()) { //discards repeat data
                return;
            }

            if(archived < ARCHIVE_MAX) { //if not full yet, increment
                archived++;
            }

            //shift the data
            for(int a = archived-1; a > 0; a--) {
                validTargetArchive[a] = validTargetArchive[a-1];
                for(int b = 0; b < 3; b++) {
                    simplePoseArchive[a][b] = simplePoseArchive[a-1][b];
                }
            }

            //update with new data
            validTargetArchive[0] = validTarget;
            for(int i = 0; i < 3; i++) {
                simplePoseArchive[0][i] = simplePosition[i];
            }

            updateAverages();
        }

        postData();
    }

    //ADD MEDIAN FILTERING
    private static void updateAverages() {
        double[] simplePoseTotal = new double[3];

        int count = 0;
        for(int a = 0; a < archived; a++) {
            if(validTargetArchive[a]) {
                for(int b = 0; b < 3; b++) {
                    simplePoseTotal[b] += simplePoseArchive[a][b];
                }
                count++;
            }
        }

        if((double) count / (double) archived < 0.2) {
            validTargetAverage = false;
        } else {
            validTargetAverage = true;

            for(int i = 0; i < 3; i++) {
                simplePoseAverage[i] = simplePoseTotal[i] / count;
            }
        }
    }

    private static boolean isDifferent() {
        if(validTargetArchive[0] != validTarget) {
            return true;
        }

        for(int i = 0; i < 3; i++) {
            if(simplePoseArchive[0][i] != simplePosition[i]) {
                return true;
            }
        }

        return false;
    }

    private static void postData() {
        /*txEntry = */ Crashboard.toDashboard("tx avg", simplePoseAverage[0], Constants.LIMELIGHT_TAB);
        Crashboard.toDashboard("tx new", simplePosition[0], Constants.LIMELIGHT_TAB);
        /*Crashboard.toDashboard("ty", simplePoseAverage[1]);
        Crashboard.toDashboard("Area", simplePoseAverage[2]);*/
    }

    //average variable getters
    public static boolean hasValidTarget() { return validTargetAverage; }

    public static double tx() { return simplePoseAverage[0]; }
    public static double ty() { return simplePoseAverage[1]; }
    public static double area() { return simplePoseAverage[2]; }

    //new variable getters
    public static boolean hasValidTargetNew() { return validTarget; }

    public static double txNew() { return simplePosition[0]; }
    public static double tyNew() { return simplePosition[1]; }
    public static double areaNew() { return simplePosition[2]; }
}
