// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Queuing;

public class IntakeOn extends CommandBase {
  private Intake intake = null;
  private Queuing queuing = null;
  
  /** Creates a new IntakeOn. */
  public IntakeOn(Intake m_intake, Queuing m_queuing) {
    intake = m_intake;
    queuing = m_queuing;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake, queuing);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intake.intakeOn();
    queuing.turnQueuingOn();
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
