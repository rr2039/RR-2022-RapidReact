// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class SetShooterSpeed extends CommandBase {
  private Shooter shooter = null;
  private double speed;

  /** Creates a new SetShooterSpeed. */
  public SetShooterSpeed(Shooter m_shooter, double m_speed) {
    shooter = m_shooter;
    speed = m_speed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
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
    speed = Preferences.getDouble("Shooter Speed", 100.0);
    double unitsPer100ms = (4096 * speed) / 600;
    shooter.setShooterSpeed(unitsPer100ms);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("SetShooterSpeed END");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //return -speed - 10.0 <= shooter.getLeftShooterSpeed() && shooter.getLeftShooterSpeed() <= -speed + 10.0
    //&& speed - 10.0 <= shooter.getRightShooterSpeed() && shooter.getRightShooterSpeed() <= speed + 10.0;
    return true;
  }
}
