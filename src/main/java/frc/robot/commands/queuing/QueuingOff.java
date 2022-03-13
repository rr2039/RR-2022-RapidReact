// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.queuing;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Queuing;
import frc.robot.subsystems.Shooter;

public class QueuingOff extends CommandBase {
  private Queuing queuing = null;
  private Shooter shooter = null;
  /** Creates a new QueuingOff. */
  public QueuingOff(Queuing m_queuing, Shooter m_shooter) {
    queuing = m_queuing;
    shooter = m_shooter;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(queuing, shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    queuing.queuingOff();
    shooter.intakeOff();
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
