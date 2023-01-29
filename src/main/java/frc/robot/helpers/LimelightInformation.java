package frc.robot.helpers;

import java.util.Properties;

public class LimelightInformation {

    private LimelightResults results; 

    public LimelightResults gResults() { return results; }
    public void setResults(LimelightResults value) { this.results = value; }

}

class LimelightResults {
    private Object[] classifier;
    private Object[] detector;
    private LimelightFiducial[] fiducial;
    private Object[] retro;
    private long pID;
    private double tl;
    private double ts;
    private long v;

    public Object[] getClassifier() { return classifier; }
    public void setClassifier(Object[] value) { this.classifier = value; }

    public Object[] getDetector() { return detector; }
    public void setDetector(Object[] value) { this.detector = value; }

    public LimelightFiducial[] getFiducial() { return fiducial; }
    public void setFiducial(LimelightFiducial[] value) { this.fiducial = value; }

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


class LimelightFiducial {
    private long fID;
    private String fam;
    private Object[] pts;
    private Object[] skew;
    private double[] t6CTs;
    private double[] t6RFS;
    private double[] t6RTs;
    private double[] t6TCS;
    private double[] t6TRs;
    private double ta;
    private double tx;
    private double txp;
    private double ty;
    private double typ;

    public long getFID() { return fID; }
    public void setFID(long value) { this.fID = value; }

    public String getFam() { return fam; }
    public void setFam(String value) { this.fam = value; }

    public Object[] getPts() { return pts; }
    public void setPts(Object[] value) { this.pts = value; }

    public Object[] getSkew() { return skew; }
    public void setSkew(Object[] value) { this.skew = value; }

    public double[] getT6CTs() { return t6CTs; }
    public void setT6CTs(double[] value) { this.t6CTs = value; }

    public double[] getT6RFS() { return t6RFS; }
    public void setT6RFS(double[] value) { this.t6RFS = value; }

    public double[] getT6RTs() { return t6RTs; }
    public void setT6RTs(double[] value) { this.t6RTs = value; }

    public double[] getT6TCS() { return t6TCS; }
    public void setT6TCS(double[] value) { this.t6TCS = value; }

    public double[] getT6TRs() { return t6TRs; }
    public void setT6TRs(double[] value) { this.t6TRs = value; }

    public double getTa() { return ta; }
    public void setTa(double value) { this.ta = value; }

    public double getTx() { return tx; }
    public void setTx(double value) { this.tx = value; }

    public double getTxp() { return txp; }
    public void setTxp(double value) { this.txp = value; }

    public double getTy() { return ty; }
    public void setTy(double value) { this.ty = value; }

    public double getTyp() { return typ; }
    public void setTyp(double value) { this.typ = value; }
}
