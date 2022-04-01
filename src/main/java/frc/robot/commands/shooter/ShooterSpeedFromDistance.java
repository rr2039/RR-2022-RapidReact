// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import javax.swing.text.html.HTML.Tag;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShooterSpeedFromDistance extends CommandBase {
  private Shooter shooter;
  private NetworkTableInstance inst = NetworkTableInstance.getDefault();
  private NetworkTable limelightTable = inst.getTable("limelight");
  private NetworkTableEntry tv, ty;
  private double counter = 0;
  private double errorMargin = 20;
  private double rpms = 0;
  private boolean setSpeed = false;
  private double distance = 0;

  private NetworkTable preferences = inst.getTable("Preferences");
  private NetworkTableEntry shotHistoryTable;
  private String[] shotHistory;

  /** Creates a new ShooterSpeedFromDistance. */
  public ShooterSpeedFromDistance(Shooter m_shooter) {
    shooter = m_shooter;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    setSpeed = false;
    counter = 0;
    distance = 0;
    tv = limelightTable.getEntry("tv");
    ty = limelightTable.getEntry("ty");
    shooter.setPID(
      Preferences.getDouble("ShooterP", 0.05), 
      Preferences.getDouble("ShooterI", 0), 
      Preferences.getDouble("ShooterD", 0), 
      Preferences.getDouble("ShooterTopF", 0.0362),
      Preferences.getDouble("ShooterBottomF", 0.0362)
    );
    SmartDashboard.putBoolean("ShooterSpeedDone", false);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!setSpeed) {
      System.out.println("Ty: " + ty.getNumber(0).doubleValue());
      distance = shooter.estimateDistance(ty.getNumber(0).doubleValue());
      distance = distance * 0.9438;
      System.out.println(distance);
      /*if (distance < 65 && shooter.isTurretUp()) {
        shooter.turretDown();
      } else if(distance >= 65 && !shooter.isTurretUp()) {
        shooter.turretUp();
      }*/
      rpms = 550;
      if (tv.getNumber(0).doubleValue() == 1.0) {
        rpms = shooter.calculateSpeedFromDistance(distance);
      }
      System.out.println(rpms);
      double unitsPer100ms = (4096 * rpms) / 600;
      shooter.setShooterSpeed(unitsPer100ms); 
      System.out.println("Distance: " + distance);
      setSpeed = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Finished setting shooter speed from vision!");
    SmartDashboard.putBoolean("ShooterSpeedDone", true);
    shotHistoryTable = preferences.getEntry("ShotHistory");
    shotHistory = shotHistoryTable.getStringArray(shotHistory);
    String[] temp = new String[shotHistory.length+1];
    for (int i = 0; i < shotHistory.length; i++) {
      temp[i] = shotHistory[i];
    }
    temp[shotHistory.length] = String.valueOf(distance).concat(":").concat(String.valueOf(rpms));
    shotHistory = temp;
    shotHistoryTable.setStringArray(shotHistory);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double shooterSpeeds = (Math.abs(shooter.getBottomShooterSpeed()) + Math.abs(shooter.getTopShooterSpeed()))/2;
    //double targetSpeed = rpms * 6.825;
    System.out.println(shooterSpeeds);
    System.out.println(rpms);
    if (counter > 13 && (rpms-errorMargin) < shooterSpeeds && shooterSpeeds < (rpms+errorMargin)) {
      return true;
    }
    counter++;
    return false;
  }
}
