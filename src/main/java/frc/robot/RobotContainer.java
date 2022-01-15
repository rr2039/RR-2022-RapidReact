// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DriveTank;
import frc.robot.commands.DriveArcade;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.IntakeDown;
import frc.robot.commands.IntakeOff;
import frc.robot.commands.IntakeOn;
import frc.robot.commands.IntakeUp;
import frc.robot.commands.QueuingOff;
import frc.robot.commands.QueuingOn;
import frc.robot.commands.ShooterDown;
import frc.robot.commands.ShooterFeedOff;
import frc.robot.commands.ShooterFeedOn;
import frc.robot.commands.ShooterLeft;
import frc.robot.commands.ShooterOff;
import frc.robot.commands.ShooterOn;
import frc.robot.commands.ShooterRight;
import frc.robot.commands.ShooterStop;
import frc.robot.commands.ShooterUp;
import frc.robot.subsystems.DrivetrainSparkMax;
import frc.robot.subsystems.DrivetrainTalon;
import frc.robot.interfaces.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Queuing;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  //private final static Drivetrain m_drivetrain = new DrivetrainTalon(); // Talon Drivetrain
  private final static Drivetrain m_drivetrain = new DrivetrainSparkMax();  // SparkMax Drivetrain
  private final Shooter m_shooter = new Shooter();
  private final Queuing m_queuing = new Queuing();
  private final Intake m_intake = new Intake();

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

  public void startup() {
    new ShooterDown(m_shooter);
  }
  
  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    Button B1 = new JoystickButton(driverController, 1);
    B1.whenPressed(new ShooterOn(m_shooter));
    Button B2 = new JoystickButton(driverController, 2);
    B2.whenPressed(new ShooterOff(m_shooter));
    Button B3 = new JoystickButton(driverController, 3);
    B3.whileHeld(new QueuingOn(m_queuing));
    B3.whenReleased(new QueuingOff(m_queuing));
    Button B4 = new JoystickButton(driverController, 4);
    B4.whileHeld(new ShooterFeedOn(m_queuing));
    B4.whenReleased(new ShooterFeedOff(m_queuing));
    Button B5 = new JoystickButton(driverController, 5);
    B5.whenPressed(new IntakeDown(m_intake));
    Button B6 = new JoystickButton(driverController, 6);
    B6.whenPressed(new IntakeUp(m_intake));

    AxisButton rightTrigger = new AxisButton(driverController, 4, 0.1, 0);
    rightTrigger.whenPressed(new IntakeOn(m_intake));
    rightTrigger.whenReleased(new IntakeOff(m_intake));

    Button DpadLeft = new POVButton(driverController, 270);
    DpadLeft.whileHeld(new ShooterLeft(m_shooter));
    DpadLeft.whenReleased(new ShooterStop(m_shooter));
    Button DpadRight = new POVButton(driverController, 90);
    DpadRight.whileHeld(new ShooterRight(m_shooter));
    DpadRight.whenReleased(new ShooterStop(m_shooter));
    Button DpadUp = new POVButton(driverController, 0);
    DpadUp.whenPressed(new ShooterUp(m_shooter));
    Button DpadDown = new POVButton(driverController, 180);
    DpadDown.whenPressed(new ShooterDown(m_shooter));
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
