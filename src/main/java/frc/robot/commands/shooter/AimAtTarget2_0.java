// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import com.revrobotics.CANSparkMax.ControlType;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.interfaces.Drivetrain;

public class AimAtTarget2_0 extends CommandBase {
  private Drivetrain drivetrain;
  private NetworkTableInstance inst = NetworkTableInstance.getDefault();
  private NetworkTable limelightTable = inst.getTable("limelight");
  private NetworkTableEntry tv, tx, ty, tl;
  private double errorMargin = 1.0;
  private double r = 12.5; // Distance from wheels to center of robot in inches

  private int counter = 0;

  /** Creates a new AimAtTarget2_0. */
  public AimAtTarget2_0(Drivetrain m_drivetrain) {
    drivetrain = m_drivetrain;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  public double degreesToDistance(double degrees) {
    return ((degrees * (Math.PI/180)) * r);
}

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    counter = 0;
    tv = limelightTable.getEntry("tv");
    tx = limelightTable.getEntry("tx");
    ty = limelightTable.getEntry("ty");
    tl = limelightTable.getEntry("tl");
    drivetrain.resetEncoders();
    drivetrain.setPID(
      Preferences.getDouble("P", 5e-5),
      Preferences.getDouble("I", 1e-6),
      Preferences.getDouble("D", 0.0),
      Preferences.getDouble("Iz", 0.0)
    );
    SmartDashboard.putBoolean("AutoAimDone", false);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (tv.getNumber(0).doubleValue() == 1.0) {
      double x_pos = tx.getNumber(0).doubleValue();
      System.out.println(x_pos);
      double distance = 0;
      if (x_pos < 7) {
        distance = degreesToDistance(x_pos) * 5.0;
      } else {
        distance = degreesToDistance(x_pos) * 2.0;
      }
       
      if (x_pos < -errorMargin) {
        // Turn left
        //shooter.turnTurretLeft(100 * (-x_pos/5));
        //shooter.turnTurretLeft(300);
        drivetrain.setPIDReference(distance, -distance, ControlType.kPosition);
      } else if (x_pos > errorMargin) {
        // Turn right
        //shooter.turnTurretRight(100 * (x_pos/5));
        //shooter.turnTurretRight(300);
        drivetrain.setPIDReference(distance, -distance, ControlType.kPosition);
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //shooter.stopTurretSpin();
    drivetrain.setMotors(0.0, 0.0);
    SmartDashboard.putBoolean("AutoAimDone", true);
    System.out.println("Aim routine done!!");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double x_pos = tx.getNumber(0).doubleValue();
    if (-errorMargin < x_pos && x_pos < errorMargin && counter > 13) {
      System.out.println("End Pos: " + x_pos);
      System.out.println("Counter End: " + counter);
      return true;
    } else if (-errorMargin < x_pos && x_pos < errorMargin) {
      counter += 1;
      return false;
    } else {
      counter = 0;
      return false;
    }
  }
}
