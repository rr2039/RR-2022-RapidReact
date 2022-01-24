// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drivetrain.DriveDistance;
import frc.robot.commands.intake.IntakeOff;
import frc.robot.commands.intake.IntakeOn;
import frc.robot.commands.queuing.ShooterFeedOff;
import frc.robot.commands.queuing.ShooterFeedOn;
import frc.robot.commands.shooter.SetShooterSpeed;
import frc.robot.commands.shooter.ShooterOff;
import frc.robot.interfaces.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Queuing;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class IntakeAndShoot extends SequentialCommandGroup {
  /** Creates a new IntakeAndShoot. */
  public IntakeAndShoot(Intake m_intake, Queuing m_queuing, Shooter m_shooter, Drivetrain m_drivetrain) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new ParallelCommandGroup(
        new IntakeOn(m_intake, m_queuing),
        new DriveDistance(m_drivetrain, -Preferences.getDouble("Distance", 18))
      ),
      new WaitCommand(1.0),
      new IntakeOff(m_intake, m_queuing),
      new WaitCommand(1.0),
      new ParallelCommandGroup(
        new DriveDistance(m_drivetrain, Preferences.getDouble("Distance", -18)),
        new ShooterFeedOn(m_queuing)
      ),
      new WaitCommand(2.0),
      new SetShooterSpeed(m_shooter, 1),
      new WaitCommand(3.0),
      new ShooterOff(m_shooter),
      new ShooterFeedOff(m_queuing)
    );
  }
}
