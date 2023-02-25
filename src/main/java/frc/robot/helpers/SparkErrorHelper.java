package frc.robot.helpers;

import javax.print.CancelablePrintJob;

import com.revrobotics.CANSparkMax;

public class SparkErrorHelper {

    public static short GetError(CANSparkMax spark){
        return spark.getFaults();
    }
    
    public static boolean IsThereSpecificError(CANSparkMax spark, CANSparkMax.FaultID error){
        return spark.getFault(error);
    }

    public static boolean HasSensorError(CANSparkMax spark){

        return IsThereSpecificError(spark, CANSparkMax.FaultID.kSensorFault);
    }

    

}   
