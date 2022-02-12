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

public class ShooterSpeedFromDistance extends CommandBase {
  private Shooter shooter;
  private NetworkTableInstance inst = NetworkTableInstance.getDefault();
  private NetworkTable limelightTable = inst.getTable("limelight");
  private NetworkTableEntry tv, ty;

  /** Creates a new ShooterSpeedFromDistance. */
  public ShooterSpeedFromDistance(Shooter m_shooter) {
    shooter = m_shooter;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    tv = limelightTable.getEntry("tv");
    ty = limelightTable.getEntry("ty");
    shooter.setPID(
      Preferences.getDouble("ShooterP", 0.05), 
      Preferences.getDouble("ShooterI", 0), 
      Preferences.getDouble("ShooterD", 0), 
      Preferences.getDouble("ShooterF", 0.0362)
    );
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("Ty: " + ty.getNumber(0).doubleValue());
    double distance = shooter.estimateDistance(ty.getNumber(0).doubleValue());
    if (distance < 65 && shooter.isTurretUp()) {
      shooter.turretDown();
    } else if(distance >= 65 && !shooter.isTurretUp()) {
      shooter.turretUp();
    }
    double rpms = shooter.calculateSpeedFromDistance(distance);
    double unitsPer100ms = (4096 * rpms) / 600;
    shooter.setShooterSpeed(unitsPer100ms); 
    System.out.println("Distance: " + distance);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Finished setting shooter speed from vision!");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
