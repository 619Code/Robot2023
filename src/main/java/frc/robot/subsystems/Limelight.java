package frc.robot.subsystems;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.LimelightHelpers;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.limelight.LimelightDataStorer;
import frc.robot.helpers.limelight.LimelightFiducial;
import frc.robot.helpers.limelight.LimelightInformation;

public class Limelight extends SubsystemBase {
    ObjectMapper mapper;
    private CommandXboxController controller;

    LimelightHelpers limelight;
    NetworkTable table;
    NetworkTableEntry pipeline;
    NetworkTableEntry json;

    double pipe;
    String jsonString;

    //settings
    boolean getSimplePose, getRelativePose, getAbsolutePose;

    //raw limelight data
    private boolean validTarget;
    private double[] simplePoseData; //tx, ty, area
    private double[] relativePoseData; //x, y, z, roll, pitch, yaw
    private double[] absolutePoseData; //x, y, z, roll, pitch, yaw

    public Limelight(CommandXboxController controller) {
        this.controller = controller;

        table = NetworkTableInstance.getDefault().getTable("limelight");
        limelight = new LimelightHelpers();
        mapper = new ObjectMapper();

        //settings
        getSimplePose = true;
        getRelativePose = true;
        getAbsolutePose = false;

        simplePoseData = new double[3];
        relativePoseData = new double[6];
        absolutePoseData = new double[6];
    }

    @Override
    public void periodic() {
        // checks for valid target
        NetworkTableEntry tv = table.getEntry("tv");
        validTarget = tv.getInteger(0) == 1;

        Crashboard.toDashboard("valid target", validTarget);

        setPipeline();

        if(getSimplePose) {
            updateSimplePose();
        }
        if(getRelativePose) {
            updateRelativePose();
        }
        if(getAbsolutePose) {
            updateAbsolutePose();
        }

        LimelightDataStorer.update(validTarget, simplePoseData, relativePoseData);
    }

    private void updateSimplePose() {
        NetworkTableEntry tx = table.getEntry("tx");
        NetworkTableEntry ty = table.getEntry("ty");
        NetworkTableEntry ta = table.getEntry("ta");

        simplePoseData[0] = tx.getDouble(0.0);
        simplePoseData[1] = ty.getDouble(0.0);
        simplePoseData[2] = ta.getDouble(0.0);

        // simple position
        /*Crashboard.toDashboard("tx", simplePoseData[0]);
        Crashboard.toDashboard("ty", simplePoseData[1]);
        Crashboard.toDashboard("Area", simplePoseData[2]);*/
    }

    private void updateRelativePose() {
        NetworkTableEntry camtran = table.getEntry("camtran");
        relativePoseData = camtran.getDoubleArray(new double[6]);

        // relative 3d position to april tag
        /*Crashboard.toDashboard("X pos", relativePoseData[0]);
        Crashboard.toDashboard("Y pos", relativePoseData[1]);
        Crashboard.toDashboard("Z pos", relativePoseData[2]);
        Crashboard.toDashboard("Roll", relativePoseData[5]);
        Crashboard.toDashboard("Pitch", relativePoseData[3]);
        Crashboard.toDashboard("Yaw", relativePoseData[4]);*/
    }

    private void updateAbsolutePose() {
        NetworkTableEntry botpose = table.getEntry("botpose");
        absolutePoseData = botpose.getDoubleArray(new double[6]);

        // exact 3d position
        /*Crashboard.toDashboard("X pos", absolutePoseData[0]);
        Crashboard.toDashboard("Y pos", absolutePoseData[1]);
        Crashboard.toDashboard("Z pos", absolutePoseData[2]);
        Crashboard.toDashboard("Roll", absolutePoseData[5]);
        Crashboard.toDashboard("Pitch", absolutePoseData[3]);
        Crashboard.toDashboard("Yaw", absolutePoseData[4]);*/
    }

    private void setPipeline() {
        pipeline = table.getEntry("pipeline");
        pipe = pipeline.getDouble(0);

        Crashboard.toDashboard("pipeline", pipe);

        if (controller.y().getAsBoolean()) {
            pipeline.setDouble(1);
        } else {
            pipeline.setDouble(0);
        }
    }

    private void dumpJson() {
        json = table.getEntry("json");
        jsonString = json.getString("");

        // JSON string to Java Object
        try {
            LimelightInformation obj = mapper.readValue(jsonString, LimelightInformation.class);
            System.out.println(obj.gResults().getFiducial().getFID());
        } catch (JsonProcessingException exp) {
            System.out.println(exp.getMessage());
        }
    }
}