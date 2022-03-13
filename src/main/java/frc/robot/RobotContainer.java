// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.drivetrain.DriveArcade;
import frc.robot.commands.elevator.ElevatorPistonDown;
import frc.robot.commands.elevator.ElevatorPistonUp;
import frc.robot.commands.elevator.MoveDegrees;
import frc.robot.commands.queuing.ExpelBall;
import frc.robot.commands.queuing.IntakeBall;
import frc.robot.commands.queuing.QueuingOff;
import frc.robot.commands.queuing.QueuingOn;
import frc.robot.commands.shooter.ShooterOff;
import frc.robot.commands.shooter.ShooterOn;
import frc.robot.subsystems.DrivetrainSparkMax;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Queuing;
import frc.robot.subsystems.Shooter;
import frc.robot.interfaces.Drivetrain;
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
  private final static Drivetrain m_drivetrain = new DrivetrainSparkMax(); // SparkMax Drivetrain
  private final static Elevator m_elevator = new Elevator();
  private final static Shooter m_shooter = new Shooter();
  private final static Queuing m_queuing = new Queuing();

  // Auto Chooser for Dashboard
  SendableChooser<Command> auto_chooser = new SendableChooser<>();

  // Controller setup
  public static Joystick driverController = new Joystick(Constants.DRIVER_CONTROLLER);
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Set default commands on subsystems
    m_drivetrain.setDefaultCommand(new DriveArcade(m_drivetrain));
    //m_shooter.setDefaultCommand(new VisionTrackingShooter(m_shooter));

    SmartDashboard.putData(auto_chooser);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    Button B1 = new JoystickButton(driverController, 1);
    B1.whenPressed(new MoveDegrees(m_elevator, 90.0));
    Button B2 = new JoystickButton(driverController, 2);
    B2.whenPressed(new MoveDegrees(m_elevator, -90.0));
    Button B3 = new JoystickButton(driverController, 3);
    B3.whenPressed(new ShooterOff(m_shooter));
    Button B4 = new JoystickButton(driverController, 4);
    B4.whenPressed(new ShooterOn(m_shooter));
    Button B5 = new JoystickButton(driverController, 5);
    B5.whenPressed(new IntakeBall(m_shooter, m_queuing));
    B5.whenReleased(new QueuingOff(m_queuing, m_shooter));
    Button B6 = new JoystickButton(driverController, 6);
    B6.whenPressed(new QueuingOn(m_queuing));
    //Button B6 = new JoystickButton(driverController, 6);
    //B6.whenPressed(new ExpelBall(m_shooter, m_queuing));
    //B6.whenReleased(new QueuingOff(m_queuing, m_shooter));
    
    Button DpadUp = new POVButton(driverController, 0);
    DpadUp.whenPressed(new ElevatorPistonUp(m_elevator, m_shooter));
    Button DpadDown = new POVButton(driverController, 180);
    DpadDown.whenPressed(new ElevatorPistonDown(m_elevator, m_shooter, m_queuing));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    //return new DriveDistance(m_drivetrain, Preferences.getDouble("Distance", 180.0));
    return auto_chooser.getSelected();
  }
}
