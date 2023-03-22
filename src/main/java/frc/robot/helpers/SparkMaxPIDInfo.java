package frc.robot.helpers;

import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.networktables.GenericEntry;

public class SparkMaxPIDInfo {

    public double kP;
    public double kI;
    public double kD;
    public double kIz;
    public double kFF;
    public double kMaxOutput;
    public double kMinOutput;

    private String tabName;
    private String prefix;

    public SparkMaxPIDInfo()
    {
       // PID coefficients
       this.kP = 0; 
       this.kI = 0;
       this.kD = 0; 
       this.kIz = 0; 
       this.kFF = 0; 
       this.kMaxOutput = 1; 
       this.kMinOutput = -1;
    }

    private GenericEntry entry_kP;
    private GenericEntry entry_kI;
    private GenericEntry entry_kD;
    private GenericEntry entry_kIz;
    private GenericEntry entry_kFF;
    private GenericEntry entry_kMaxOutput;
    private GenericEntry entry_kMinOutput;


    public void ToDashboard(String tab, String prefix) {
        this.tabName = tab;
        this.prefix = prefix;

        this.entry_kP = Crashboard.toDashboard(this.getVarName("kP"), this.kP, this.tabName);
        this.entry_kI = Crashboard.toDashboard(this.getVarName("kI"), this.kI, this.tabName);
        this.entry_kD = Crashboard.toDashboard(this.getVarName("kD"), this.kD, this.tabName);
        this.entry_kIz = Crashboard.toDashboard(this.getVarName("kIz"), this.kIz, this.tabName);
        this.entry_kFF = Crashboard.toDashboard(this.getVarName("kFF"), this.kFF, this.tabName);
        this.entry_kMaxOutput = Crashboard.toDashboard(this.getVarName("kMaxOutput"), this.kMaxOutput, this.tabName);
        this.entry_kMinOutput = Crashboard.toDashboard(this.getVarName("kMinOutput"), this.kMinOutput, this.tabName);

    }

    public void updateFromDashboard() {
        
        this.kP = this.entry_kP.getDouble(0);
        this.kI = this.entry_kI.getDouble(0);
        this.kD = Math.max(0, this.entry_kD.getDouble(0));
        this.kIz = this.entry_kIz.getDouble(0);
        this.kFF = this.entry_kFF.getDouble(0);
        this.kMaxOutput = this.entry_kMaxOutput.getDouble(0);
        this.kMaxOutput = this.entry_kMinOutput.getDouble(-.2);
        System.out.println("kP: " + this.entry_kP.getDouble(0));
    }

    public void toPIDController(SparkMaxPIDController sparkMaxPIDController)
    {
        // set PID coefficients
        if (sparkMaxPIDController.getP() != kP)
            sparkMaxPIDController.setP(kP);

        if (sparkMaxPIDController.getI() != kI)
            sparkMaxPIDController.setI(kI);

        System.out.println("D Value: " + kD);
        if (sparkMaxPIDController.getD() != kD)
            sparkMaxPIDController.setD(kD);
        
        if (sparkMaxPIDController.getIZone() != kIz)
            sparkMaxPIDController.setIZone(kIz);
        
        if (sparkMaxPIDController.getFF() != kFF)
            sparkMaxPIDController.setFF(kFF);
        
        if (sparkMaxPIDController.getOutputMin() != kMinOutput  || sparkMaxPIDController.getOutputMax() != kMaxOutput)
            sparkMaxPIDController.setOutputRange(kMinOutput, kMaxOutput);
    }

    private String getVarName(String name)
    {
        return this.prefix + " " + name;
    }
}