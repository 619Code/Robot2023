package frc.robot.helpers;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class limelight {
    NetworkTable table;
    NetworkTableEntry tv;
    NetworkTableEntry tx;
    NetworkTableEntry ty;
    NetworkTableEntry ta;
    NetworkTableEntry botpose;

    double x;
    double y;
    double a;
    double vt;
    double[] bp;

    public limelight() {

        table = NetworkTableInstance.getDefault().getTable("limelight");
        tv = table.getEntry("tv");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        botpose = table.getEntry("botpose");


        x = tx.getDouble(0.0);
        y = ty.getDouble(0.0);
        a = ta.getDouble(0.0);
        bp = botpose.getDoubleArray(new double[6]);
        vt = tv.getDouble(0);

    }

    public void PositionToDashboard(){
        //simple position
        Crashboard.toDashboard("X Position", x);
        Crashboard.toDashboard("Y Position", y);
        Crashboard.toDashboard("Area", a);

        //3d position maybe, idk
        Crashboard.toDashboard("X pos", bp[0]);
        Crashboard.toDashboard("Y pos", bp[1]);
        Crashboard.toDashboard("Z pos", bp[2]);
        Crashboard.toDashboard("X rotation", bp[3]);
        Crashboard.toDashboard("Y rotation", bp[4]);
        Crashboard.toDashboard("Z rotation", bp[5]);

    }




    
}
