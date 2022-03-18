// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drivetrain.DriveDistance;
import frc.robot.commands.elevator.ElevatorPistonDown;
import frc.robot.commands.queuing.FeedBall;
import frc.robot.commands.queuing.QueueOff;
import frc.robot.commands.shooter.SetShooterSpeed;
import frc.robot.commands.shooter.ShooterOff;
import frc.robot.interfaces.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Queuing;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Auto1 extends SequentialCommandGroup {
  /** Creates a new Auto1. */
  public Auto1(Drivetrain m_drivetrain, Shooter m_shooter, Queuing m_queuing, Elevator m_elevator) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new SetShooterSpeed(m_shooter, 500),
      new WaitCommand(2),
      new FeedBall(m_queuing),
      new WaitCommand(2),
      new ShooterOff(m_shooter),
      new QueueOff(m_queuing),
      new WaitCommand(0.25),
      new DriveDistance(m_drivetrain, -20).withTimeout(2),
      new ElevatorPistonDown(m_elevator, m_shooter, m_queuing)
    );
  }
}
