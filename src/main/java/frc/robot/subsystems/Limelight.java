package frc.robot.subsystems;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.helpers.Crashboard;

public class Limelight extends SubsystemBase {
    NetworkTable table;
    NetworkTableEntry pipeline;
    NetworkTableEntry tv;
    NetworkTableEntry tx;
    NetworkTableEntry ty;
    NetworkTableEntry ta;
    NetworkTableEntry botpose;
    NetworkTableEntry camtran;

    double pipe;
    double x;
    double y;
    double a;
    double vt;
    double[] bp;
    double[] ct;

    public Limelight() {

        table = NetworkTableInstance.getDefault().getTable("limelight");
        

    }

    @Override
    public void periodic() {
        // checks for valid target
        tv = table.getEntry("tv");
        vt = tv.getInteger(0);
        Crashboard.toDashboard("valit target", vt);

        while (vt == 1) {
            RelativeToDashboard();
        }

    }

    public void SimplePositionToDashboard(){

            tx = table.getEntry("tx");
            ty = table.getEntry("ty");
            ta = table.getEntry("ta");

            x = tx.getDouble(0.0);
            y = ty.getDouble(0.0);
            a = ta.getDouble(0.0);
 
            //simple position
            Crashboard.toDashboard("X Position", x);
            Crashboard.toDashboard("Y Position", y);
            Crashboard.toDashboard("Area", a);

    }

    public void RelativeToDashboard() {

        camtran = table.getEntry("camtran");
        ct = camtran.getDoubleArray(new double[6]);

        //relative 3d position to april tag
        Crashboard.toDashboard("X pos", ct[0]);
        Crashboard.toDashboard("Y pos", ct[1]);
        Crashboard.toDashboard("Z pos", ct[2]);
        Crashboard.toDashboard("X rotation", ct[3]);
        Crashboard.toDashboard("Y rotation", ct[4]);
        Crashboard.toDashboard("Z rotation", ct[5]);     
    }

    public void ExactToDashboard() {
        
        botpose = table.getEntry("botpose");
        bp = botpose.getDoubleArray(new double[6]);

        //exact 3d position
        Crashboard.toDashboard("X pos", bp[0]);
        Crashboard.toDashboard("Y pos", bp[1]);
        Crashboard.toDashboard("Z pos", bp[2]);
        Crashboard.toDashboard("X rotation", bp[3]);
        Crashboard.toDashboard("Y rotation", bp[4]);
        Crashboard.toDashboard("Z rotation", bp[5]);

    }




    
}
