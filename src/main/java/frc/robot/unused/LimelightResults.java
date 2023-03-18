package frc.robot.unused;

public class LimelightResults {
    public Object[] Classifier;
    public Object[] Detector;
    public LimelightFiducial[] Fiducial;
    public Object[] Retro;
    public long pID;
    public double tl;
    public double ts;
    public long v;

    public Object[] getClassifier() { return Classifier; }
    public void setClassifier(Object[] value) { this.Classifier = value; }

    public Object[] getDetector() { return Detector; }
    public void setDetector(Object[] value) { this.Detector = value; }

    public LimelightFiducial[] getFiducial() { return Fiducial; }
    public void setFiducial(LimelightFiducial[] value) { this.Fiducial = value; }

    public Object[] getRetro() { return Retro; }
    public void setRetro(Object[] value) { this.Retro = value; }

    public long getPID() { return pID; }
    public void setPID(long value) { this.pID = value; }

    public double getTl() { return tl; }
    public void setTl(double value) { this.tl = value; }

    public double getTs() { return ts; }
    public void setTs(double value) { this.ts = value; }

    public long getV() { return v; }
    public void setV(long value) { this.v = value; }
}