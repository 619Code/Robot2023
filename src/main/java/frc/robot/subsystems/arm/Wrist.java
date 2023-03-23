package frc.robot.subsystems.arm;

import javax.lang.model.util.ElementScanner14;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.helpers.Crashboard;

public class Wrist extends SubsystemBase {
    private CANSparkMax wristMotor;
    private DutyCycleEncoder wristAbsoluteEncoder;
    private RelativeEncoder wristRelativeEncoder;
    private GenericEntry pEntry;

    private double ninetyDegreesPosition = 0;
    private double zeroDegreesPosition = 13.905;
    private double closePosition = 6;
    private double slowSpeed = .12;
    private double ffBaseValue = .05;
    private double maxSpeed = .15; 
    private double tolerance = .5;
    private GenericEntry ffEntry;
    private GenericEntry maxSpeedEntry;
    private GenericEntry slowSpeedEntry;

    public Wrist() {
        wristMotor = new CANSparkMax(Constants.WRIST_MOTOR, MotorType.kBrushless);
        wristMotor.restoreFactoryDefaults();
        wristMotor.setIdleMode(IdleMode.kBrake);
        wristMotor.setSmartCurrentLimit(30);
        wristMotor.setInverted(true);

        wristAbsoluteEncoder = new DutyCycleEncoder(Constants.WRIST_ABSOLUTE_ENCODER);
        wristRelativeEncoder = wristMotor.getEncoder();

        ffEntry = Crashboard.toDashboard("Wrist FF Base Value", ffBaseValue, Constants.ARM_TAB);
        maxSpeedEntry = Crashboard.toDashboard("Wrist Slow Value", slowSpeed, Constants.ARM_TAB);
        slowSpeedEntry = Crashboard.toDashboard("Wrist Max Value", maxSpeed, Constants.ARM_TAB);
        zero();
    }
    

    public void periodic() {
        Crashboard.toDashboard("Wrist Absolute Position", getAbsolutePosition(), Constants.ARM_TAB);
        Crashboard.toDashboard("Wrist Relative Position", getRelativePosition(), Constants.ARM_TAB);
        //this.ffBaseValue = ffEntry.getDouble(ffBaseValue);
        this.maxSpeed = maxSpeedEntry.getDouble(maxSpeed);
        this.slowSpeed = slowSpeedEntry.getDouble(slowSpeed);
    }

    public void move(double speed) {
        move(speed, false);
    }

    public void move(double speed, boolean zeroing) {
        boolean move = true;

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
            Crashboard.toDashboard("Wrist Speed", speed, Constants.ARM_TAB);
            wristMotor.set(speed);
            System.out.println("Wrist Speed: " + wristMotor.get());
        }
    }

    //boolean return says if it's at that position
    public boolean moveToPosition(double goal) {

        goal = Math.min(goal,Constants.MAX_WRIST_POSITION);
        goal = Math.max(goal,Constants.MIN_WRIST_POSITION);

        if(Math.abs(getRelativePosition() - goal) < 1) {
            return true;
        }

        double wristP = Constants.WRIST_P;
        if (pEntry != null)
            wristP = pEntry.getDouble(wristP);


        double speed = Math.abs(getRelativePosition() - goal) * wristP; //proportional control
        speed = Math.min(speed, Constants.WRIST_SPEED);
        
        if(getRelativePosition() < goal) {
            move(speed);
        } else {
            move(-speed);
        }
        
        return false;
    }

    protected double calculateSpeed(double diff)
    {

        double speed = 0;
        if (Math.abs(diff) <= this.closePosition)
        {
            speed = slowSpeed * diff * .05 ;
        }
        else
        {
            speed = maxSpeed;
        }

        // if (diff > 0)
        //     speed = speed / 2;

        return speed;
    }

    // Calculate FF based on side, length, and angle of arm
    public double calculateFF() {

        // double angleForCalc = 0.0;
        
        // angleForCalc = Math.abs(this.getAngle());
        

        // // Not using this yet
        // var angleFactor = (Math.abs(Math.cos(angleForCalc) * 8) * ffBaseValue);
        
        // var ff = (ffBaseValue * angleFactor) + .001; 
        
        //Crashboard.toDashboard("FF Wrist Calculated Value", ff, Constants.ARM_TAB);
        
        if (this.getAngle() > 91)
            return .025;
        if (this.getAngle() > 85)
            return -.015;
        if (this.getAngle() > 75)
            return -.04;
        else
            return -.10;
        
        //return Math.abs(ff);
    }

    public double getAngle() {

        double baseAngle = 90;

        double degreesPerTick = 90/zeroDegreesPosition;

        if (this.getRelativePosition() >= zeroDegreesPosition )
        {
            return -((this.getRelativePosition() - zeroDegreesPosition) * degreesPerTick);
        }
        else
        {
            return baseAngle - (this.getRelativePosition() * degreesPerTick);
        }
    }

      //Front to Back is at 35
    public boolean moveToPositionSimple(double goal) {

        goal = Math.min(goal,Constants.MAX_HINGE_POSITION);
        goal = Math.max(goal,Constants.MIN_HINGE_POSITION);

        double diff = goal - this.getRelativePosition();
        double speed = calculateSpeed(diff);
        double ff = calculateFF();

        if (Math.abs(diff) > this.tolerance )
        {
            if (diff > 0)
            {
                this.move(ff + speed, false);
            }
            else
            {
                if (this.getAngle() > 75 && this.getAngle() < 90)
                    this.move((ff - speed)*1.10);
                else
                    this.move(ff - speed, false );
            }
            return false;
        }
        else
        {
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
