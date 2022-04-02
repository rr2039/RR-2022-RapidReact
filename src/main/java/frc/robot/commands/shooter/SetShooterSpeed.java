// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class SetShooterSpeed extends CommandBase {
  private Shooter shooter = null;
  private double speed;
  private boolean pref = false;
  private double counter = 0;
  private double errorMargin = 15;
  private double rpms = 0;
  private boolean setSpeed = false;

  /** Creates a new SetShooterSpeed. */
  public SetShooterSpeed(Shooter m_shooter, double m_speed) {
    shooter = m_shooter;
    speed = m_speed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  public SetShooterSpeed(Shooter m_shooter, double m_speed, boolean m_pref) {
    shooter = m_shooter;
    speed = m_speed;
    pref = m_pref;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    setSpeed = false;
    counter = 0;
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
      if (pref == true) {
        speed = Preferences.getDouble("Shooter Speed", 100.0);
      }
      double unitsPer100ms = (4096 * speed) / 600;
      shooter.setShooterSpeed(unitsPer100ms);
      setSpeed = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("SetShooterSpeed END");
    SmartDashboard.putBoolean("ShooterSpeedDone", true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //return -speed - 10.0 <= shooter.getLeftShooterSpeed() && shooter.getLeftShooterSpeed() <= -speed + 10.0
    //&& speed - 10.0 <= shooter.getRightShooterSpeed() && shooter.getRightShooterSpeed() <= speed + 10.0;
    //return true;
    double shooterSpeeds = (Math.abs(shooter.getBottomShooterSpeed()) + Math.abs(shooter.getTopShooterSpeed()))/2;
    //double targetSpeed = speed * 6.825;
    System.out.println(shooterSpeeds);
    System.out.println(speed);
    if (counter > 13 && 
    (speed-errorMargin) < shooter.getBottomShooterSpeed() && shooter.getBottomShooterSpeed() < (speed+errorMargin) &&
    (speed-errorMargin) < shooter.getTopShooterSpeed() && shooter.getTopShooterSpeed() < (speed+errorMargin)
    ) {
      return true;
    }
    counter++;
    return false;
  }
}
