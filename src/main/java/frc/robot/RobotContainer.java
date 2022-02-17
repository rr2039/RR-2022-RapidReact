// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.autonomous.Autonomous1;
import frc.robot.commands.autonomous.Autonomous2;
import frc.robot.commands.autonomous.Autonomous3;
import frc.robot.commands.autonomous.IntakeAndShoot;
import frc.robot.commands.autonomous.MacDaddyAuto;
import frc.robot.commands.drivetrain.DriveArcade;
import frc.robot.commands.intake.IntakeDown;
import frc.robot.commands.intake.IntakeOff;
import frc.robot.commands.intake.IntakeOn;
import frc.robot.commands.intake.IntakeUp;
import frc.robot.commands.queuing.QueuingOff;
import frc.robot.commands.queuing.QueuingOn;
import frc.robot.commands.queuing.ShooterFeedOff;
import frc.robot.commands.queuing.ShooterFeedOn;
import frc.robot.commands.shooter.AimAtTarget;
import frc.robot.commands.shooter.AutoAimAndShoot;
import frc.robot.commands.shooter.ResetTurretPosition;
import frc.robot.commands.shooter.SetShooterSpeed;
import frc.robot.commands.shooter.ShooterDown;
import frc.robot.commands.shooter.ShooterLeft;
import frc.robot.commands.shooter.ShooterOff;
import frc.robot.commands.shooter.ShooterOn;
import frc.robot.commands.shooter.ShooterRight;
import frc.robot.commands.shooter.ShooterStop;
import frc.robot.commands.shooter.ShooterUp;
import frc.robot.commands.shooter.VisionTrackingShooter;
import frc.robot.subsystems.DrivetrainSparkMax;
import frc.robot.interfaces.Drivetrain;
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

    auto_chooser.setDefaultOption("Auto 1", new Autonomous1(m_drivetrain, m_shooter));
    auto_chooser.addOption("Auto 2", new Autonomous2(m_drivetrain, m_shooter));
    auto_chooser.addOption("Auto 3", new Autonomous3(m_drivetrain, m_intake, m_shooter, m_queuing));
    auto_chooser.addOption("IntakeAndShoot", new IntakeAndShoot(m_intake, m_queuing, m_shooter, m_drivetrain));
    auto_chooser.addOption("MacDaddyAuto", new MacDaddyAuto(m_intake, m_queuing, m_shooter, m_drivetrain));
    SmartDashboard.putData(auto_chooser);

    SmartDashboard.putData("ResetTurret", new ResetTurretPosition(m_shooter));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    Button B1 = new JoystickButton(driverController, 1);
    //B1.whenPressed(new ShooterOn(m_shooter));
    B1.whenPressed(new SetShooterSpeed(m_shooter, 500));
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

    AxisButton rightTrigger = new AxisButton(driverController, 3, 0.1, 0);
    rightTrigger.whenPressed(new IntakeOn(m_intake, m_queuing));
    rightTrigger.whenReleased(new IntakeOff(m_intake, m_queuing));

    AxisButton leftTrigger = new AxisButton(driverController, 2, 0.1, 0);
    leftTrigger.whenPressed(new AutoAimAndShoot(m_shooter));

    Button DpadLeft = new POVButton(driverController, 270);
    DpadLeft.whenHeld(new ShooterLeft(m_shooter));
    DpadLeft.whenReleased(new ShooterStop(m_shooter));
    Button DpadRight = new POVButton(driverController, 90);
    DpadRight.whenHeld(new ShooterRight(m_shooter));
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
    //return new DriveDistance(m_drivetrain, Preferences.getDouble("Distance", 180.0));
    return auto_chooser.getSelected();
  }
}
