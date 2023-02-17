package frc.robot.helpers.limelight;

import frc.robot.helpers.Crashboard;

public class LimelightDataStorer {
    private static int archived = 0;
    private static final int ARCHIVE_MAX = 10;

    private static boolean validTarget;
    private static double[] simplePosition;
    private static double[] relativePosition;

    private static boolean[] validTargetArchive = new boolean[ARCHIVE_MAX];
    private static double[][] simplePoseArchive = new double[ARCHIVE_MAX][3];
    private static double[][] relativePoseArchive = new double[ARCHIVE_MAX][6];

    private static boolean validTargetAverage;
    private static double[] simplePoseAverage = new double[3];
    private static double[] relativePoseAverage = new double[6];
    
    public static void update(boolean validTarget, double[] simplePosition, double[] relativePosition) {
        LimelightDataStorer.validTarget = validTarget;
        LimelightDataStorer.simplePosition = simplePosition;
        LimelightDataStorer.relativePosition = relativePosition;

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
            for(int b = 0; b < 6; b++) {
                relativePoseArchive[a][b] = relativePoseArchive[a-1][b];
            }
        }

        //update with new data
        validTargetArchive[0] = validTarget;
        for(int i = 0; i < 3; i++) {
            simplePoseArchive[0][i] = simplePosition[i];
        }
        for(int i = 0; i < 6; i++) {
            relativePoseArchive[0][i] = relativePosition[i];
        }

        updateAverages();
        postData();
    }

    //ADD MEDIAN FILTERING
    private static void updateAverages() {
        double[] simplePoseTotal = new double[3];
        double[] relativePoseTotal = new double[6];

        int count = 0;
        for(int a = 0; a < archived; a++) {
            if(validTargetArchive[a]) {
                for(int b = 0; b < 3; b++) {
                    simplePoseTotal[b] += simplePoseArchive[a][b];
                }
                for(int b = 0; b < 6; b++) {
                    relativePoseTotal[b] += relativePoseArchive[a][b];
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
            for(int i = 0; i < 6; i++) {
                relativePoseAverage[i] = relativePoseTotal[i] / count;
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

        for(int i = 0; i < 6; i++) {
            if(relativePoseArchive[0][i] != relativePosition[i]) {
                return true;
            }
        }

        return false;
    }

    private static void postData() {
        Crashboard.toDashboard("tx", simplePoseAverage[0]);
        Crashboard.toDashboard("ty", simplePoseAverage[1]);
        Crashboard.toDashboard("Area", simplePoseAverage[2]);

        Crashboard.toDashboard("X pos", relativePoseAverage[0]);
        Crashboard.toDashboard("Y pos", relativePoseAverage[1]);
        Crashboard.toDashboard("Z pos", relativePoseAverage[2]);
        Crashboard.toDashboard("Roll", relativePoseAverage[5]);
        Crashboard.toDashboard("Pitch", relativePoseAverage[3]);
        Crashboard.toDashboard("Yaw", relativePoseAverage[4]);
    }

    //lots of getters
    public static boolean hasValidTarget() { return validTargetAverage; }

    public static double tx() { return simplePoseAverage[0]; }
    public static double ty() { return simplePoseAverage[1]; }
    public static double area() { return simplePoseAverage[2]; }

    public static double x() { return relativePoseAverage[0]; }
    public static double y() { return relativePoseAverage[1]; }
    public static double z() { return relativePoseAverage[2]; }

    public static double roll() { return relativePoseAverage[5]; }
    public static double pitch() { return relativePoseAverage[3]; }
    public static double yaw() { return relativePoseAverage[4]; }
}
