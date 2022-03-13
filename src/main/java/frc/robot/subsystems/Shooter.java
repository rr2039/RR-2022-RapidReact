// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class Shooter extends SubsystemBase {
  WPI_TalonSRX rightShooter = null;
  WPI_TalonSRX leftShooter = null;
  WPI_TalonSRX turretMotor = null;
  Solenoid turretSolenoid = null;
  TalonFX topShooter = null;
  TalonFX bottomShooter = null;
  DoubleSolenoid intakePistons = null;

  private static final int kTimeoutMs = 30;
  private static final double kF = 0.0362;
  private static final double kP = 0.05;
  private static final double kI = 0;
  private static final double kD = 0;

  private static final double h1 = 15.25; // Height of camera
  private double h2 = 0;
  private static final double a1 = 27.5; // Angle of camera

  /** Creates a new Shooter. */
  public Shooter() {
    //rightShooter = new WPI_TalonSRX(Constants.SHOOTER_RIGHT_TALON);
    //leftShooter = new WPI_TalonSRX(Constants.SHOOTER_LEFT_TALON);
    //turretMotor = new WPI_TalonSRX(Constants.SHOOTER_TURRET_TALON);
    //turretSolenoid = new Solenoid(null, Constants.SHOOTER_SOLENOID0);
    topShooter = new TalonFX(Constants.SHOOTER_TOP_TALON);
    bottomShooter = new TalonFX(Constants.SHOOTER_BOTTOM_TALON);

    intakePistons = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.INTAKE_SOLENOID2, Constants.INTAKE_SOLENOID3);
  }

  public void intake() {
    topShooter.set(ControlMode.PercentOutput, 0.1);
    bottomShooter.set(ControlMode.PercentOutput, -0.1);
  }

  public void reverseIntake() {
    topShooter.set(ControlMode.PercentOutput, -0.1);
    bottomShooter.set(ControlMode.PercentOutput, 0.1);
  }

  public void intakeOff() {
    topShooter.set(ControlMode.PercentOutput, 0.0);
    bottomShooter.set(ControlMode.PercentOutput, 0.0);
  }

  public void intakePistonUp() {
    intakePistons.set(Value.kReverse);
  }

  public void intakePistonDown() {
    intakePistons.set(Value.kForward);
  }

  public void turnOnShooter() {
    //rightShooter.set(ControlMode.PercentOutput, 1.0);
    //leftShooter.set(ControlMode.PercentOutput, -1.0);
    topShooter.set(ControlMode.PercentOutput, -0.5);
    bottomShooter.set(ControlMode.PercentOutput, 0.5);
  }

  public void turnOffShooter() {
    //rightShooter.set(ControlMode.PercentOutput, 0.0);
    //leftShooter.set(ControlMode.PercentOutput, 0.0);
    topShooter.set(ControlMode.PercentOutput, 0.0);
    bottomShooter.set(ControlMode.PercentOutput, 0.0);
  }

  public void turnTurretRight() {
    turretMotor.set(ControlMode.PercentOutput, 1.0);
  }

  public void turnTurretLeft() {
    turretMotor.set(ControlMode.PercentOutput, -1.0);
  }

  public void stopTurretSpin() {
    turretMotor.set(ControlMode.PercentOutput, 0.0);
  }

  public void turretUp() {
    turretSolenoid.set(true);
  }

  public void turretDown() {
    turretSolenoid.set(false);
  }

  public void setShooterSpeed(double speed) {
    topShooter.set(ControlMode.Velocity, -speed);
    bottomShooter.set(ControlMode.Velocity, speed);
  }

  public double estimateDistance(double a2) {
    // d = (h2-h1) / tan(a1+a2)
    h2 = Preferences.getDouble("Target Height", 0);
    return (h2 - h1) / Math.tan(Math.toRadians(a1 + a2));
  }

  public double calculateSpeedFromDistance(double distance) {
    //return ((distance + 1144) / 0.759);
    return ((distance + 224) / 0.257);
  }

  public double getTopShooterSpeed() {
    return topShooter.getSelectedSensorVelocity() / Constants.SHOOTER_CONVERSION_FACTOR;
  }
                  
  public double getRightShooterSpeed() {
    return rightShooter.getSelectedSensorVelocity() / Constants.SHOOTER_CONVERSION_FACTOR;
  }

  public void setPID(double kP, double kI, double kD, double kF) {
    topShooter.configFactoryDefault();
    bottomShooter.configFactoryDefault();

    topShooter.setSensorPhase(true);
    topShooter.configNominalOutputForward(0, kTimeoutMs);
		topShooter.configNominalOutputReverse(0, kTimeoutMs);
		topShooter.configPeakOutputForward(1, kTimeoutMs);
		topShooter.configPeakOutputReverse(-1, kTimeoutMs);

		/* Config the Velocity closed loop gains in slot0 */
		topShooter.config_kF(0, kF, kTimeoutMs);
		topShooter.config_kP(0, kP, kTimeoutMs);
		topShooter.config_kI(0, kI, kTimeoutMs);
    topShooter.config_kD(0, kD, kTimeoutMs);

    bottomShooter.setSensorPhase(true);
    bottomShooter.configNominalOutputForward(0, kTimeoutMs);
		bottomShooter.configNominalOutputReverse(0, kTimeoutMs);
		bottomShooter.configPeakOutputForward(1, kTimeoutMs);
		bottomShooter.configPeakOutputReverse(-1, kTimeoutMs);

		/* Config the Velocity closed loop gains in slot0 */
		bottomShooter.config_kF(0, kF, kTimeoutMs);
		bottomShooter.config_kP(0, kP, kTimeoutMs);
		bottomShooter.config_kI(0, kI, kTimeoutMs);
    bottomShooter.config_kD(0, kD, kTimeoutMs);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
