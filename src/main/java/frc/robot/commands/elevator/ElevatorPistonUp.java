// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Shooter;

public class ElevatorPistonUp extends CommandBase {
  private Elevator elevator = null;
  private Shooter shooter = null;

  /** Creates a new ElevatorPistonUp. */
  public ElevatorPistonUp(Elevator m_elevator, Shooter m_shooter) {
    elevator = m_elevator;
    shooter = m_shooter;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(elevator, shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    elevator.elevatorPistonUp(); 
    shooter.intakePistonUp();
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
