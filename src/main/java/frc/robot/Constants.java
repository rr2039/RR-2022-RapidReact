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

  public static final double[][] SHOOTER_DATA = {
    {12,550},
    {24,625},
    {42,725},
    {60,825},
    {72,875},
    {80,1175},
    {96,1160},
    {110,1200},
    {122,1250},
    {145,1310},
    {168,1380},
    {189,1400},
    {195,1450},
    {214,1550}
  };

    /*[106.62775312677226:1190.365008933635, 
    143.85346707662373:1297.507537123095, 
    140.49473120324126:1290.2059373983507, 
    170.07465876054644:1381.9758654862346, 
    129.6242587253664:1266.574475489927]*/
}
