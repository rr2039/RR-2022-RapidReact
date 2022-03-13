// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.elevator;

import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;

public class MoveDegrees extends CommandBase {
  private Elevator elevator = null;
  private double degrees = 0;
  /** Creates a new MoveDegrees. */
  public MoveDegrees(Elevator m_elevator, double m_degrees) {
    elevator = m_elevator;
    degrees = m_degrees;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_elevator);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    elevator.resetEncoders();
    elevator.setPID(
      Preferences.getDouble("ElevatorP", 5e-5),
      Preferences.getDouble("ElevatorI", 1e-6),
      Preferences.getDouble("ElevaotrD", 0.0),
      Preferences.getDouble("ElevatorFF", 0.0),
      Preferences.getDouble("ElevatorIz", 0.0)
    );
    elevator.setPIDReference(degrees, degrees, ControlType.kPosition);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double error = elevator.getLeftEnc() - elevator.getRightEnc();
    if (error >= 0) {
      elevator.setPIDReference(degrees - error, degrees, ControlType.kPosition);
    } else {
      elevator.setPIDReference(degrees, degrees + error, ControlType.kPosition);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    elevator.setMotors(0.0, 0.0);
    System.out.println("ELEVATOR MOVE DEGREES END");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return 
      ((degrees - 0.5) <= elevator.getLeftEnc() && elevator.getLeftEnc() <= (degrees + 0.5))
      && ((degrees - 0.5) <= elevator.getRightEnc() && elevator.getRightEnc() <= (degrees + 0.5));
  }
}
