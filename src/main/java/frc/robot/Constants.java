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
  // Motor Controllers
  public static final int DRIVETRAIN_LEFT_FRONT = 40;
  public static final int DRIVETRAIN_LEFT_BACK = 30;
  public static final int DRIVETRAIN_RIGHT_FRONT = 20;
  public static final int DRIVETRAIN_RIGHT_BACK = 10;
  public static final int SHOOTER_RIGHT_TALON = 8;
  public static final int SHOOTER_LEFT_TALON = 7;
  public static final int SHOOTER_TURRET_TALON = 5;

  // Solenoids
  public static final int SHOOTER_SOLENOID = 10;

  // Joysticks
  public static final int DRIVER_CONTROLLER = 0;
  public static final int DRIVER_CONTROLLER_MOVE_AXIS = 1; // Change for your controller
  public static final int DRIVER_CONTROLLER_ROTATE_AXIS = 4; // Change for your controller
}