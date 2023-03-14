// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.autonomous.Auto1;
import frc.robot.commands.autonomous.Auto2;
import frc.robot.commands.autonomous.Auto3;
import frc.robot.commands.drivetrain.DriveArcade;
import frc.robot.commands.elevator.ElevatorPistonDown;
import frc.robot.commands.elevator.ElevatorPistonUp;
import frc.robot.commands.queuing.ExpelBall;
import frc.robot.commands.queuing.FeedBall;
import frc.robot.commands.queuing.IntakeBall;
import frc.robot.commands.queuing.IntakeDown;
import frc.robot.commands.queuing.IntakeOff;
import frc.robot.commands.queuing.IntakeUp;
import frc.robot.commands.queuing.QueueOff;
import frc.robot.commands.shooter.AutoAimAndShoot;
import frc.robot.commands.shooter.AutoAimAndShoot2_0;
import frc.robot.commands.shooter.SetShooterSpeed;
import frc.robot.commands.shooter.ShooterOff;
import frc.robot.commands.shooter.ShooterOn;
import frc.robot.commands.shooter.ShooterSpeedFromDistance;
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
  public static Joystick operatorController = new Joystick(Constants.OPERATOR_CONTROLLER);
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Set default commands on subsystems
    m_drivetrain.setDefaultCommand(new DriveArcade(m_drivetrain));
    //m_shooter.setDefaultCommand(new VisionTrackingShooter(m_shooter));
    auto_chooser.setDefaultOption("LowGoal", new Auto1(m_drivetrain, m_shooter, m_queuing, m_elevator));
    auto_chooser.addOption("2BallAuto", new Auto2(m_drivetrain, m_shooter, m_queuing, m_elevator));
    auto_chooser.addOption("1BallHigh", new Auto3(m_shooter, m_drivetrain, m_queuing));
    SmartDashboard.putData(auto_chooser);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //Driver Controller
    //Trigger DRTrigger = new AxisButton(driverController, 3, 0.1, 0);
    Trigger DRTrigger = new JoystickButton(driverController, 8);
    DRTrigger.onTrue(new SetShooterSpeed(m_shooter, 1150));
    DRTrigger.onFalse(new ShooterOff(m_shooter));
    //Trigger DLTrigger = new AxisButton(driverController, 2, 0.1, 0);
    Trigger DLTrigger = new JoystickButton(driverController, 7);
    DLTrigger.onTrue(new ShooterSpeedFromDistance(m_shooter));
    DLTrigger.onFalse(new ShooterOff(m_shooter));

    Trigger DB9 = new JoystickButton(driverController, 10);
    DB9.onTrue(new IntakeBall(m_shooter, m_queuing));
    DB9.onFalse(new IntakeOff(m_queuing, m_shooter));

    Trigger B5 = new JoystickButton(driverController, 5);
    B5.onTrue(new AutoAimAndShoot(m_shooter, m_drivetrain));
    B5.onFalse(new ShooterOff(m_shooter));
    Trigger B6 = new JoystickButton(driverController, 6);
    B6.onTrue(new SetShooterSpeed(m_shooter, 550));
    B6.onFalse(new ShooterOff(m_shooter));
    Trigger DB1 = new JoystickButton(driverController, 1);
    DB1.onTrue(new FeedBall(m_queuing));
    DB1.onFalse(new QueueOff(m_queuing));
    Trigger DB2 = new JoystickButton(driverController, 2);
    DB2.onTrue(new FeedBall(m_queuing));
    DB2.onFalse(new QueueOff(m_queuing));
    Trigger B4 = new JoystickButton(driverController, 4);
    B4.onTrue(new SetShooterSpeed(m_shooter, 550, true));
    B4.onFalse(new ShooterOff(m_shooter));
    Trigger B3 = new JoystickButton(driverController, 3);
    B3.onTrue(new AutoAimAndShoot2_0(m_shooter, m_drivetrain));
    B3.onFalse(new ShooterOff(m_shooter));

    /*
    Button B3 = new JoystickButton(driverController, 3);
    B3.whenPressed(new MoveDegrees(m_elevator, 22.5).withTimeout(4));
    Button B4 = new JoystickButton(driverController, 4);
    B4.whenPressed(new MoveDegrees(m_elevator, -22.5).withTimeout(4));
    */
    /*
    Button B3 = new JoystickButton(driverController, 3);
    B3.whenPressed(new ShooterOff(m_shooter));
    Button B4 = new JoystickButton(driverController, 4);
    B4.whenPressed(new SetShooterSpeed(m_shooter, 100));
    Button RTrigger = new AxisButton(driverController, 3, 0.1, 0);
    RTrigger.whenPressed(new FeedBall(m_queuing));
    RTrigger.whenReleased(new QueueOff(m_queuing));
    */

    //Operator Controller
    //Button B1 = new JoystickButton(operatorController, 1);
    //B1.whenPressed(new MoveDegrees(m_elevator, 45.0));
    //Button B2 = new JoystickButton(operatorController, 2);
    //B2.whenPressed(new MoveDegrees(m_elevator, -45.0));
    Trigger B1 = new JoystickButton(operatorController, 1);
    B1.onTrue(new IntakeUp(m_shooter));
    Trigger B2 = new JoystickButton(operatorController, 2);
    B2.onFalse(new IntakeDown(m_shooter));

    //Trigger ORTrigger = new AxisButton(operatorController, 3, 0.1, 0);
    Trigger ORTrigger = new JoystickButton(operatorController, 8);
    ORTrigger.onTrue(new IntakeBall(m_shooter, m_queuing));
    ORTrigger.onFalse(new IntakeOff(m_queuing, m_shooter));
    //Trigger OLTrigger = new AxisButton(operatorController, 2, 0.1, 0);
    Trigger OLTrigger = new JoystickButton(operatorController, 7);
    OLTrigger.onTrue(new ExpelBall(m_shooter, m_queuing));
    OLTrigger.onFalse(new IntakeOff(m_queuing, m_shooter));


    Trigger DpadUp = new POVButton(operatorController, 0);
    DpadUp.onTrue(new ElevatorPistonUp(m_elevator, m_shooter));
    Trigger DpadDown = new POVButton(operatorController, 180);
    DpadDown.onFalse(new ElevatorPistonDown(m_elevator, m_shooter, m_queuing));
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
