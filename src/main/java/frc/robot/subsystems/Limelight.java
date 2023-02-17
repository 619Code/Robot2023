package frc.robot.subsystems;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
    NetworkTableEntry botPose;
    NetworkTableEntry translation;

    double pipe;
    String jsonString;

    boolean validTarget;
    double[] botPoseData;
    double[] translationData;

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
        validTarget = tv.getInteger(0) == 1;

        Crashboard.toDashboard("valid target", validTarget);

        setPipeline();
        updateRelativePose();
        updateSimplePose();

        if (validTarget) {
            dumpJson();
            // setPipeline();
        } else {
            // setPipeline();
        }
    }

    private void updateSimplePose() {
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");

        double x = tx.getDouble(0.0);
        double y = ty.getDouble(0.0);
        double area = ta.getDouble(0.0);

        // simple position
        Crashboard.toDashboard("tx", x);
        Crashboard.toDashboard("ty", y);
        Crashboard.toDashboard("Area", area);
    }

    private void updateRelativePose() {
        translation = table.getEntry("camtran");
        translationData = translation.getDoubleArray(new double[6]);

        // relative 3d position to april tag
        Crashboard.toDashboard("X pos", translationData[0]);
        Crashboard.toDashboard("Y pos", translationData[1]);
        Crashboard.toDashboard("Z pos", translationData[2]);
        Crashboard.toDashboard("Roll", translationData[5]);
        Crashboard.toDashboard("Pitch", translationData[3]);
        Crashboard.toDashboard("Yaw", translationData[4]);
    }

    private void updateExactPose() {
        botPose = table.getEntry("botpose");
        botPoseData = botPose.getDoubleArray(new double[6]);

        // exact 3d position
        Crashboard.toDashboard("X pos", botPoseData[0]);
        Crashboard.toDashboard("Y pos", botPoseData[1]);
        Crashboard.toDashboard("Z pos", botPoseData[2]);
        Crashboard.toDashboard("Roll", botPoseData[5]);
        Crashboard.toDashboard("Pitch", botPoseData[3]);
        Crashboard.toDashboard("Yaw", botPoseData[4]);
    }

    private void setPipeline() {
        pipeline = table.getEntry("pipeline");
        pipe = pipeline.getDouble(0);

        Crashboard.toDashboard("pipeline", pipe);

        if (controller.getYButton()) {
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
            System.out.println("" + obj.gResults().getFiducial()[0].getTx());
        } catch (JsonProcessingException exp) {
            System.out.println(exp.getMessage());
        }
    }
}