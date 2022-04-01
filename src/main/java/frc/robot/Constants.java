// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  // General Constants
  public static final int DRIVETRAIN_GEARBOX_RATIO = 0;
  public static final double SHOOTER_CONVERSION_FACTOR = 1;

  public static final int GYRO_PORT = 0;
  public static final double GYRO_kVoltsPerDegreePerSecond = 0.0128;
  
  // Motor Controllers
  public static final int DRIVETRAIN_LEFT_FRONT = 21;
  public static final int DRIVETRAIN_LEFT_BACK = 22;
  public static final int DRIVETRAIN_RIGHT_FRONT = 31;
  public static final int DRIVETRAIN_RIGHT_BACK = 32; //10
  public static final int SHOOTER_RIGHT_TALON = 8;
  public static final int SHOOTER_LEFT_TALON = 7;
  public static final int SHOOTER_TURRET_TALON = 5;
  public static final int SHOOTER_FEED_TALON = 3;
  public static final int QUEUING_TALON = 6;
  public static final int INTAKE_ARM_TALON = 1;
  public static final int INTAKE_END_TALON= 4;
  public static final int SHOOTER_LEFT_SPARK = 16;
  public static final int SHOOTER_RIGHT_SPARK = 15;
  public static final int SHOOTER_TOP_TALON = 20;
  public static final int SHOOTER_BOTTOM_TALON = 19;  
  public static final int QUEUING_TOP_SPARK = 17;
  public static final int QUEUING_BOTTOM_SPARK = 18; //18

  // Solenoids
  //public static final int SHOOTER_SOLENOID0 = 0;
  //public static final int SHOOTER_SOLENOID3 = 1;
  //public static final int INTAKE_SOLENOID1 = 2;
  //public static final int INTAKE_SOLENOID2 = 3;
  public static final int ELEVATOR_SOLENOID0 = 0;
  public static final int ELEVATOR_SOLENOID1 = 1;
  public static final int INTAKE_SOLENOID2 = 2;
  public static final int INTAKE_SOLENOID3 = 3;

  // Joysticks
  public static final int DRIVER_CONTROLLER = 0;
  public static final int OPERATOR_CONTROLLER = 1;
  public static final int DRIVER_CONTROLLER_MOVE_AXIS = 1; // Change for your controller
  public static final int DRIVER_CONTROLLER_ROTATE_AXIS = 4; // Change for your controller

  /*public static final String[] SHOOTER_DATA = [
    194.88132730517003:1477.2659190957556, 
    200.96532198784442:1500.4115510407125, 
    82.21903578737914:1130.4758946844786, 
    91.66932881626886:550.0, 
    168.21323152024937:1375.8112068705138, 
    151.59469356728417:1333.9867339182103, 
    135.33779009027106:1300.562983483785, 
    125.1951616677466:1283.6586027795777, 
    116.67414790030877:1261.1422829179533, 
    116.67414790030877:1261.1422829179533, 
    116.67414790030877:1261.1422829179533, 
    107.35668838137127:1223.65976746119, 
    189.06976882494416:1455.156729225331, 
    189.06976882494416:1455.156729225331, 
    173.09898612889438:1394.398316794707, 
    156.81457046453562:1347.036426161339, 
    145.27682472141402:1318.1920618035351,
    121.56089289758017:1277.6014881626336, 
    121.56089289758017:1277.6014881626336, 
    122.08682592862186:1278.4780432143698, 
    110.1615819183568:1234.0065913264866, 
    96.41496249679881:1200.864505201664, 
    86.89601228612989:1181.0333589294373, 
    Split, 
    113.72912228432807:1248.871342851367, 
    126.05337915931248:1285.0889652655208, 
    180.0569703326625:1420.8689088742594, 
    138.4625908920818:1305.7709848201364, 
    140.03767023969175:1308.396117066153]*/
}
