// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.queuing;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Queuing;

public class ExpelBall extends CommandBase {
  private Shooter shooter = null;
  private Queuing queuing = null;
  /** Creates a new ExpelBall. */
  public ExpelBall(Shooter m_shooter, Queuing m_queuing) {
    shooter = m_shooter;
    queuing = m_queuing;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter, queuing);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.setShooterPercent(0.25, 0.25);
    queuing.setRollers(-0.5, 0.5);
    //shooter.intakePistonUp();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
