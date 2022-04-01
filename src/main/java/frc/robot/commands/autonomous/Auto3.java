// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.DriveDistance;
import frc.robot.commands.queuing.FeedBall;
import frc.robot.commands.queuing.QueueOff;
import frc.robot.commands.shooter.AutoAimAndShoot;
import frc.robot.commands.shooter.ShooterOff;
import frc.robot.interfaces.Drivetrain;
import frc.robot.subsystems.Queuing;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Auto3 extends SequentialCommandGroup {
  /** Creates a new Auto3. */
  public Auto3(Shooter m_shooter, Drivetrain m_drivetrain, Queuing m_queuing) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new DriveDistance(m_drivetrain, -25).withTimeout(3), 
      new WaitCommand(1),
      new AutoAimAndShoot(m_shooter, m_drivetrain),
      new FeedBall(m_queuing),
      new WaitCommand(3),
      new QueueOff(m_queuing),
      new ShooterOff(m_shooter)
    );
  }
}
