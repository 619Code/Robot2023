package frc.robot.subsystems;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.enums.Pipeline;
import frc.robot.helpers.limelight.*;

public class Limelight extends SubsystemBase {
    ObjectMapper mapper;

    NetworkTable table;
    NetworkTableInstance pipeline;
    NetworkTableEntry json;

    String jsonString;

    public Pipeline currentPipeline;
    public boolean cameraMode;
    public boolean rrtMode;

    //raw limelight data
    private boolean validTarget;
    private double[] simplePoseData; //tx, ty, area

    private GenericEntry validTargetEntry, pipelineEntry;

    public Limelight() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        mapper = new ObjectMapper();

        currentPipeline = Pipeline.CAMERA_PIPELINE;

        simplePoseData = new double[3];
    }

    @Override
    public void periodic() {
        if(currentPipeline == Pipeline.CAMERA_PIPELINE) {
            cameraMode = true;
            rrtMode = false;
            validTarget = false;

            simplePoseData[0] = 0.0;
            simplePoseData[1] = 0.0;
            simplePoseData[2] = 0.0;
        } else {
            cameraMode = false;
            if(currentPipeline == Pipeline.RRT_PIPELINE) {
                rrtMode = true;
            } else {
                rrtMode = false;
            }

            // checks for valid target
            NetworkTableEntry tv = table.getEntry("tv");
            validTarget = tv.getInteger(0) == 1;
            updateSimplePose();
        }

        validTargetEntry = Crashboard.toDashboard("valid target", validTarget, Constants.LIMELIGHT_TAB);
        
        pipelineEntry = Crashboard.toDashboard("pipeline", (double)table.getEntry("pipeline").getNumber(-1), Constants.LIMELIGHT_TAB);

        LimelightDataStorer.update(cameraMode, validTarget, simplePoseData);
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

    public void setPipeline(int pipelinenumber) {
        table.getEntry("pipeline").setNumber(pipelinenumber);
    }

    /*private void dumpJson() {
        json = table.getEntry("json");
        jsonString = json.getString("");

        // JSON string to Java Object
        try {
            LimelightInformation obj = mapper.readValue(jsonString, LimelightInformation.class);
            System.out.println("" + obj.gResults().getFiducial().length);
        } catch (JsonProcessingException exp) {
            System.out.println(exp.getMessage());
        }
    }*/
}