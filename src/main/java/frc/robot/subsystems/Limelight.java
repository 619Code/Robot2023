package frc.robot.subsystems;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.LimelightInformation;
import frc.robot.helpers.LimelightFiducial;

public class Limelight extends SubsystemBase {
    LimelightHelpers limelight;
    NetworkTable table;
    NetworkTableEntry pipeline;
    NetworkTableEntry json;
    NetworkTableEntry tv;
    NetworkTableEntry tx;
    NetworkTableEntry ty;
    NetworkTableEntry ta;
    NetworkTableEntry botpose;
    NetworkTableEntry camtran;

    double pipe;
    String jason;
    double x;
    double y;
    double a;
    double vt;
    double[] bp;
    double[] ct;

    ObjectMapper mapper;

    private XboxController controller;

    public Limelight(XboxController controller) {

        this.controller = controller;

        table = NetworkTableInstance.getDefault().getTable("limelight");
        limelight = new LimelightHelpers();
        mapper = new ObjectMapper();
        

    }

    @Override
    public void periodic() {
        // checks for valid target
        tv = table.getEntry("tv");
        vt = tv.getInteger(0);
        //Crashboard.toDashboard("valit target", vt);
        //setPipeline();


        if (vt == 1) {
            //RelativeToDashboard();
            //Crashboard.toDashboard("valit target", vt);
            //setPipeline();
            dumpJson();
        }
        else {
            //Crashboard.toDashboard("valit target", vt);
            //setPipeline();
        }


    }

    private void SimplePositionToDashboard(){

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

    private void RelativeToDashboard() {

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

    private void ExactToDashboard() {      

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

    private void setPipeline() {
        pipeline = table.getEntry("pipeline");
        pipe = pipeline.getDouble(0);

        Crashboard.toDashboard("pipeline", pipe);

        if (controller.getYButton()) {
            pipeline.setDouble(1);
        }
        else {
            pipeline.setDouble(0);
        }


    }

    private void dumpJson() {

        json = table.getEntry("json");
        jason = json.getString("");
       // System.out.println(jason);

        //JSON string to Java Object
        try {
          LimelightInformation obj = mapper.readValue(jason, LimelightInformation.class);
        System.out.println(obj.gResults().getFiducial().getFID());
        }
        catch (JsonProcessingException exp) {
         System.out.println(exp.getMessage());
        }

    }

    




    
}
