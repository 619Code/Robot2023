package frc.robot.subsystems.arm;

import javax.lang.model.util.ElementScanner14;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.helpers.Crashboard;

public class Wrist extends SubsystemBase {
    private CANSparkMax wristMotor;
    private DutyCycleEncoder wristAbsoluteEncoder;
    private RelativeEncoder wristRelativeEncoder;
    private GenericEntry pEntry;

    double baseAngleRelativeToEndOfArm = 95;
    private double ninetyDegreesPosition = 0;
    private double zeroDegreesPosition = 13.905;
    private double closePosition = 6;
    //private double slowSpeed = .12;
    //private double ffBaseValue = .05;
    private double maxSpeed = .15; 
    private double tolerance = .5;
    private GenericEntry maxFFEntry;
    private GenericEntry maxSpeedEntry;
    //private GenericEntry slowSpeedEntry;
    private double maxFF = .1;

    public Wrist() {
        wristMotor = new CANSparkMax(Constants.WRIST_MOTOR, MotorType.kBrushless);
        wristMotor.restoreFactoryDefaults();
        wristMotor.setIdleMode(IdleMode.kBrake);
        wristMotor.setSmartCurrentLimit(30);
        wristMotor.setInverted(true);

        wristAbsoluteEncoder = new DutyCycleEncoder(Constants.WRIST_ABSOLUTE_ENCODER);
        wristRelativeEncoder = wristMotor.getEncoder();

        maxFFEntry = Crashboard.toDashboard("Wrist FF Base Value", maxFF, Constants.WRIST_TAB);
        maxSpeedEntry = Crashboard.toDashboard("Wrist Max Value", maxSpeed, Constants.WRIST_TAB);
        zero();
    }
    

    public void periodic() {
        Crashboard.toDashboard("Wrist Absolute Position", getAbsolutePosition(), Constants.WRIST_TAB);
        Crashboard.toDashboard("Wrist Relative Position", getRelativePosition(), Constants.WRIST_TAB);
        Crashboard.toDashboard("Wrist Motor Speed", this.wristMotor.get(), Constants.WRIST_TAB);
        Crashboard.toDashboard("Wrist V", wristRelativeEncoder.getVelocity(), Constants.WRIST_TAB);
        maxFF = this.maxFFEntry.getDouble(maxFF);
        //maxSpeed = maxSpeedEntry.getDouble(maxSpeed);
        //this.TempDashbaord();
    }

    public void move(double speed) {
        move(speed, false);
    }

    public void move(double speed, boolean zeroing) {
        boolean move = true;
        Crashboard.toDashboard("Wrist Attempted Power", speed, Constants.WRIST_TAB);

        if(speed > 0) {
            if(!zeroing && getRelativePosition() > Constants.MAX_WRIST_POSITION) {
                stop(); move = false;
            }
        } else if(speed < 0) {
            if(!zeroing && getRelativePosition() < Constants.MIN_WRIST_POSITION) {
                stop(); move = false;
            }
        }



        if(move) {            
            wristMotor.set(speed);
        }
            
    }

    protected double calculateSpeed(double diff)
    {
        double pValue = .02;
        double speed = 0;
        //if (Math.abs(diff) <= this.closePosition)
        {
            // Never want the speed to over the max
            speed = Math.min(maxSpeed, Math.abs(diff) * pValue);
        }
        // else
        // {
        //     speed = maxSpeed;
        // }

        return speed; //* this.getDirectionalSpeedMultiplier();
    }

    // speed and ff will always be opposit of each other
    public double getDirectionalSpeedMultiplier() {
        return -getDirectionalFFMultiplier();
    }

    public double getDirectionalFFMultiplier() {
        double directionalValue;
        double currentAngleRelativeToGround = this.getFFAngleForCalculating();

        // If our angle is over 90 then our ff flips and becomes positive otherwise
        //  the ff is going to be negative to hold up the grabber
        if (currentAngleRelativeToGround > 90 && currentAngleRelativeToGround < 180)
        {
            directionalValue = 1;
        }
        else
        {
            directionalValue = -1;
        }
        return directionalValue;
    }

    // Calculate ff taking into account the the angle of the grabber in respect to the 
    //  ground
    public double calculateFF() {
        return Math.abs(Math.cos(Math.toRadians(this.getFFAngleForCalculating()))) * this.getDirectionalFFMultiplier() * maxFF;
        // if (this.getAngle() > 91)
        //     return .025;
        // if (this.getAngle() > 85)
        //     return -.015;
        // if (this.getAngle() > 75)
        //     return -.04;
        // else
        //     return -.10;
        
        //return Math.abs(ff);
    }

    // Gets the angle from the ground taking into account the arm angle in addition to 
    //  the wrist
    public double getFFAngleForCalculating() {
        return States.ArmAngleInDegreesFromStart + this.getAngle();
    }

    public double getAngle() {

        double degreesPerTick = baseAngleRelativeToEndOfArm/zeroDegreesPosition;

        if (this.getRelativePosition() >= zeroDegreesPosition )
        {
            return -((this.getRelativePosition() - zeroDegreesPosition) * degreesPerTick);
        }
        else
        {
            return baseAngleRelativeToEndOfArm - (this.getRelativePosition() * degreesPerTick);
        }
    }

    public void CalculateStuff(double goal) {
        
        Crashboard.toDashboard("Verified Target Value", goal, Constants.WRIST_TAB);
        goal = Math.min(goal,Constants.MAX_WRIST_POSITION);
        goal = Math.max(goal,Constants.MIN_WRIST_POSITION);

        double diff = goal - this.getRelativePosition();
        Crashboard.toDashboard("Wrist Diff", diff, Constants.WRIST_TAB);

        // speed could be positive or negative depending 
        //  on where the arm is
        double speed = calculateSpeed(diff);
        Crashboard.toDashboard("Wrist Calculated Speed", speed, Constants.WRIST_TAB);

        // ff could be positive or negative depending
        //  on where the arm is
        double ff = calculateFF();
        Crashboard.toDashboard("Wrist Calculated FF", ff, Constants.WRIST_TAB);
    }

    public boolean moveToPositionSimple(double goal) {

        goal = Math.min(goal,Constants.MAX_WRIST_POSITION);
        goal = Math.max(goal,Constants.MIN_WRIST_POSITION);

        double diff = goal - this.getRelativePosition();
        Crashboard.toDashboard("Wrist Diff", diff, Constants.WRIST_TAB);

        // speed could be positive or negative depending 
        //  on where the arm is
        double speed = calculateSpeed(diff);
        Crashboard.toDashboard("Wrist Calculated Speed", speed, Constants.WRIST_TAB);

        // ff could be positive or negative depending
        //  on where the arm is
        double ff = calculateFF();
        Crashboard.toDashboard("Wrist Calculated FF", ff, Constants.WRIST_TAB);

        if (Math.abs(diff) > this.tolerance )
        {
            if (diff > 0)
            {
                this.move(ff + speed, false);
            }
            else
            {
                // commenting out to get a decent baseline for new code changes
                //  the following commented code was put in to overcome a power
                //  hump
                //if (this.getAngle() > 75 && this.getAngle() < 90)
                //    this.move((ff - speed)*1.10);
                //else
                this.move(ff - speed, false );
            }
            return false;
        }
        else
        {
            // Move at the ff rate
            this.move(ff);
            return true;
        }
    }

    public void stop() {
        wristMotor.set(0);
    }

    public void zero() {
        wristRelativeEncoder.setPosition(0);
    }

    public double getAbsolutePosition() {
        return wristAbsoluteEncoder.getAbsolutePosition() - Constants.POSITION_OFFSET;
    }

    public double getRelativePosition() {
        return wristRelativeEncoder.getPosition();
    }
}
