// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drivetrain.DriveDistance;
import frc.robot.commands.drivetrain.TurnDegree;
import frc.robot.commands.shooter.ShooterDown;
import frc.robot.commands.shooter.ShooterUp;
import frc.robot.interfaces.Drivetrain;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Autonomous2 extends SequentialCommandGroup {
  /** Creates a new Autonomous2. */
  public Autonomous2(Drivetrain m_drivetrain, Shooter m_shooter) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new ShooterUp(m_shooter),
      new DriveDistance(m_drivetrain, Preferences.getDouble("Distance", -36.0)),
      new WaitCommand(3.0),
      new TurnDegree(m_drivetrain, Preferences.getDouble("Turn Angle", 180)),
      new WaitCommand(3.0),
      new DriveDistance(m_drivetrain, Preferences.getDouble("Distance", -36.0)),
      new WaitCommand(3.0),
      new ShooterDown(m_shooter)
    );
  }
}
