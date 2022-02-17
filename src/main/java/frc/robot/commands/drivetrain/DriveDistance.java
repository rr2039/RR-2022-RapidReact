// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain;

import com.revrobotics.CANSparkMax.ControlType;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.interfaces.Drivetrain;
 
public class DriveDistance extends CommandBase {
  private Drivetrain drivetrain;
  private double distance;

  /** Creates a new DriveDistance. */
  public DriveDistance(Drivetrain m_drivetrain, double m_distance) {
    drivetrain = m_drivetrain;
    distance = m_distance;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
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
    //pid.setReference(distance - drivetrain.encoderToDistanceInch(drivetrain.getEnc()), ControlType.kPosition);
    drivetrain.setPIDReference(distance, distance, ControlType.kPosition);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double error = drivetrain.getLeftEnc() - drivetrain.getRightEnc();
    if (error >= 0) {
      drivetrain.setPIDReference(distance - error, distance, ControlType.kPosition);
    } else {
      drivetrain.setPIDReference(distance, distance + error, ControlType.kPosition);
    }
    //pid.setReference(drivetrain.encoderToDistanceInch(drivetrain.getEnc()), ControlType.kPosition);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.setMotors(0.0, 0.0);
    System.out.println("Drive Distance END");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return 
      ((distance - 0.5) <= drivetrain.getLeftEnc() && drivetrain.getLeftEnc() <= (distance + 0.5))
      && ((distance - 0.5) <= drivetrain.getRightEnc() && drivetrain.getRightEnc() <= (distance + 0.5));
  }
}
