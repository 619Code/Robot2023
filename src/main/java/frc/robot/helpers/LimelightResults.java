package frc.robot.helpers;

import frc.robot.helpers.LimelightFiducial;

public class LimelightResults {
    private Object[] classifier;
    private Object[] detector;
    private LimelightFiducial fiducial;
    private Object[] retro;
    private long pID;
    private double tl;
    private double ts;
    private long v;

    public Object[] getClassifier() { return classifier; }
    public void setClassifier(Object[] value) { this.classifier = value; }

    public Object[] getDetector() { return detector; }
    public void setDetector(Object[] value) { this.detector = value; }

    public LimelightFiducial getFiducial() { return fiducial; }
    public void setFiducial(LimelightFiducial value) { this.fiducial = value; }

    public Object[] getRetro() { return retro; }
    public void setRetro(Object[] value) { this.retro = value; }

    public long getPID() { return pID; }
    public void setPID(long value) { this.pID = value; }

    public double getTl() { return tl; }
    public void setTl(double value) { this.tl = value; }

    public double getTs() { return ts; }
    public void setTs(double value) { this.ts = value; }

    public long getV() { return v; }
    public void setV(long value) { this.v = value; }
}