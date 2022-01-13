// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.DriveTank;
import frc.robot.commands.DriveArcade;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.ShooterDown;
import frc.robot.commands.ShooterLeft;
import frc.robot.commands.ShooterOn;
import frc.robot.commands.ShooterRight;
import frc.robot.commands.ShooterTurnStop;
import frc.robot.commands.ShooterOff;
import frc.robot.commands.ShooterUp;
import frc.robot.subsystems.DrivetrainSparkMax;
import frc.robot.subsystems.DrivetrainTalon;
import frc.robot.interfaces.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.OI; // File for joystick commands

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  //private final static Drivetrain m_drivetrain = new DrivetrainTalon(); // Talon Drivetrain
  private final static Drivetrain m_drivetrain = new DrivetrainSparkMax(); // SparkMax Drivetrain
  private final Shooter m_shooter = new Shooter();

  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  // Controller setup
  public static Joystick driverController = new Joystick(Constants.DRIVER_CONTROLLER);
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Set default commands on subsystems
    m_drivetrain.setDefaultCommand(new DriveArcade(m_drivetrain));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    Button B1 = new JoystickButton(driverController, 1);
    B1.whenPressed(new ShooterUp(m_shooter));
    Button B2 = new JoystickButton(driverController, 2);
    B2.whenPressed(new ShooterDown(m_shooter));
    Button B5 = new JoystickButton(driverController, 5);
    B5.whenPressed(new ShooterOn(m_shooter));
    Button B6 = new JoystickButton(driverController, 6);
    B6.whenPressed(new ShooterOff(m_shooter));
    Button DpadLeft = new POVButton(driverController, 270);
    DpadLeft.whileHeld(new ShooterLeft(m_shooter));
    DpadLeft.whenReleased(new ShooterTurnStop(m_shooter));
    Button DpadRight = new POVButton(driverController, 90);
    DpadRight.whileHeld(new ShooterRight(m_shooter));
    DpadRight.whenReleased(new ShooterTurnStop(m_shooter));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
