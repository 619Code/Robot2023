package frc.robot.helpers.limelight;

public class LimelightFiducial {
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