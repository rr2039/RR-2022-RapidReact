// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain;

import com.revrobotics.CANSparkMax.ControlType;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.interfaces.Drivetrain;

public class TurnDegree extends CommandBase {
  private Drivetrain drivetrain;
  private double degrees;
  private double distance;
  private double r = 14; // Distance from wheels to center of robot in inches

  /** Creates a new TurnDegree. */
  public TurnDegree(Drivetrain m_drivetrain, double m_degrees) {
    drivetrain = m_drivetrain;
    degrees = m_degrees;
    distance = degreesToDistance(degrees);
    System.out.println("------------ Turn Distance: " + distance);
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  public double degreesToDistance(double degrees) {
      return ((degrees * (Math.PI/180)) * r);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.resetEncoders();
    drivetrain.setPID(
      Preferences.getDouble("P", 5e-5),
      Preferences.getDouble("I", 1e-6),
      Preferences.getDouble("D", 0.0),
      Preferences.getDouble("Iz", 0.0)
    );
    drivetrain.setPIDReference(-distance, distance, ControlType.kPosition);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrain.setPIDReference(-distance, distance, ControlType.kPosition);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.setMotors(0.0, 0.0);
    System.out.println("Turn Degree END");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return 
      ((-distance - 0.5) <= drivetrain.getLeftEnc() && drivetrain.getLeftEnc() <= (-distance + 0.5))
      && ((distance - 0.5) <= drivetrain.getRightEnc() && drivetrain.getRightEnc() <= (distance + 0.5));
  }
}
