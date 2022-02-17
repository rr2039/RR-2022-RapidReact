// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drivetrain.DriveDistance;
import frc.robot.commands.drivetrain.TurnDegree;
import frc.robot.commands.intake.IntakeOff;
import frc.robot.commands.intake.IntakeOn;
import frc.robot.commands.queuing.ShooterFeedOff;
import frc.robot.commands.queuing.ShooterFeedOn;
import frc.robot.commands.shooter.AimAtTarget2_0;
import frc.robot.commands.shooter.ShooterOff;
import frc.robot.commands.shooter.ShooterSpeedFromDistance;
import frc.robot.interfaces.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Queuing;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class MacDaddyAuto extends SequentialCommandGroup {
  /** Creates a new MacDaddyAuto. */
  public MacDaddyAuto(Intake m_intake, Queuing m_queuing, Shooter m_shooter, Drivetrain m_drivetrain) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new ParallelCommandGroup(
        new DriveDistance(m_drivetrain, -Preferences.getDouble("Distance", 18)),
        new IntakeOn(m_intake, m_queuing)
      ),
      new WaitCommand(0.95),
      new DriveDistance(m_drivetrain, -Preferences.getDouble("Distance", 18)),
      new WaitCommand(0.95),
      new DriveDistance(m_drivetrain, -Preferences.getDouble("Distance", 18)),
      new WaitCommand(0.95),
      new IntakeOff(m_intake, m_queuing),
      new TurnDegree(m_drivetrain, -75).withTimeout(1.5),
      new AimAtTarget2_0(m_shooter), 
      new ShooterSpeedFromDistance(m_shooter),
      new WaitCommand(0.5),
      new ShooterFeedOn(m_queuing),
      new WaitCommand(7.5),
      new ShooterOff(m_shooter),
      new ShooterFeedOff(m_queuing)
    );
  }
}
