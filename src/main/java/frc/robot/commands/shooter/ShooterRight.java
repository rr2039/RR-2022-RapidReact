// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShooterRight extends CommandBase {
  private Shooter shooter = null;
  
  /** Creates a new ShooterRight. */
  public ShooterRight(Shooter m_shooter) {
    shooter = m_shooter;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.turnTurretRight();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stopTurretSpin();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (shooter.turretAtRightBound()) {
      return true;
    } else {
      return false;
    }
  }
}
