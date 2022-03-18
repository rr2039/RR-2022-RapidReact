// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Queuing;

public class ElevatorPistonDown extends CommandBase {
  private Elevator elevator = null; 
  private Shooter shooter = null;
  private Queuing queuing = null;

  /** Creates a new ElevatorPistonDown. */
  public ElevatorPistonDown(Elevator m_elevator, Shooter m_shooter, Queuing m_queuing) {
    elevator = m_elevator;
    shooter = m_shooter;
    queuing = m_queuing;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(elevator, shooter, queuing);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    elevator.elevatorPistonDown();
    shooter.intakePistonDown();
    shooter.turnOffShooter();
    queuing.setRollers(0.0, 0.0);
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
