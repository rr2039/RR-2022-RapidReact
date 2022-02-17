// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class AimAtTarget2_0 extends CommandBase {
  private Shooter shooter;
  private NetworkTableInstance inst = NetworkTableInstance.getDefault();
  private NetworkTable limelightTable = inst.getTable("limelight");
  private NetworkTableEntry tv, tx, ty, tl;
  private double errorMargin = 1.0;

  private int counter = 0;

  /** Creates a new AimAtTarget2_0. */
  public AimAtTarget2_0(Shooter m_shooter) {
    shooter = m_shooter;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    counter = 0;
    tv = limelightTable.getEntry("tv");
    tx = limelightTable.getEntry("tx");
    ty = limelightTable.getEntry("ty");
    tl = limelightTable.getEntry("tl");
    shooter.setTurretPID(
      Preferences.getDouble("TurretP", 0.05), 
      Preferences.getDouble("TurretI", 0), 
      Preferences.getDouble("TurretD", 0), 
      Preferences.getDouble("TurretF", 0.0362),
      Preferences.getDouble("TurretIz", 0)
    );
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (tv.getNumber(0).doubleValue() == 1.0) {
      double x_pos = tx.getNumber(0).doubleValue();
      System.out.println(x_pos);
      if (x_pos < -errorMargin) {
        // Turn left
        shooter.turnTurretLeft(100 * (-x_pos/5));
        //shooter.turnTurretLeft(300);
      } else if (x_pos > errorMargin) {
        // Turn right
        shooter.turnTurretRight(100 * (x_pos/5));
        //shooter.turnTurretRight(300);
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stopTurretSpin();
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
