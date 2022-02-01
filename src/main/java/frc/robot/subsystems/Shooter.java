// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Shooter extends SubsystemBase {
  WPI_TalonSRX rightShooter = null;
  WPI_TalonSRX leftShooter = null;
  WPI_TalonSRX turretMotor = null;
  DoubleSolenoid turretSolenoid = null;

  private static final int kTimeoutMs = 30;
  private static final double kF = 0.0362;
  private static final double kP = 0.05;
  private static final double kI = 0;
  private static final double kD = 0;

  /** Creates a new Shooter. */
  public Shooter() {
    rightShooter = new WPI_TalonSRX(Constants.SHOOTER_RIGHT_TALON);
    rightShooter.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, kTimeoutMs);
    leftShooter = new WPI_TalonSRX(Constants.SHOOTER_LEFT_TALON);
    leftShooter.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, kTimeoutMs);
    
    turretMotor = new WPI_TalonSRX(Constants.SHOOTER_TURRET_TALON);
    turretMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, kTimeoutMs);
    setTurretPID(0.039125, 0, 0.001, 0.0927625);
    
    turretSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.SHOOTER_SOLENOID0, Constants.SHOOTER_SOLENOID3);
  }

  public void setShooterSpeed(double speed) {
    leftShooter.set(ControlMode.Velocity, -speed);
    rightShooter.set(ControlMode.Velocity, speed);
  }

  public double getLeftShooterSpeed() {
    return leftShooter.getSelectedSensorVelocity() / Constants.SHOOTER_CONVERSION_FACTOR;
  }

  public double getRightShooterSpeed() {
    return rightShooter.getSelectedSensorVelocity() / Constants.SHOOTER_CONVERSION_FACTOR;
  }

  public void setPID(double kP, double kI, double kD, double kF) {
    leftShooter.configFactoryDefault();
    rightShooter.configFactoryDefault();

    leftShooter.setSensorPhase(true);
    leftShooter.configNominalOutputForward(0, kTimeoutMs);
		leftShooter.configNominalOutputReverse(0, kTimeoutMs);
		leftShooter.configPeakOutputForward(1, kTimeoutMs);
		leftShooter.configPeakOutputReverse(-1, kTimeoutMs);

		/* Config the Velocity closed loop gains in slot0 */
		leftShooter.config_kF(0, kF, kTimeoutMs);
		leftShooter.config_kP(0, kP, kTimeoutMs);
		leftShooter.config_kI(0, kI, kTimeoutMs);
    leftShooter.config_kD(0, kD, kTimeoutMs);

    rightShooter.setSensorPhase(true);
    rightShooter.configNominalOutputForward(0, kTimeoutMs);
		rightShooter.configNominalOutputReverse(0, kTimeoutMs);
		rightShooter.configPeakOutputForward(1, kTimeoutMs);
		rightShooter.configPeakOutputReverse(-1, kTimeoutMs);

		/* Config the Velocity closed loop gains in slot0 */
		rightShooter.config_kF(0, kF, kTimeoutMs);
		rightShooter.config_kP(0, kP, kTimeoutMs);
		rightShooter.config_kI(0, kI, kTimeoutMs);
    rightShooter.config_kD(0, kD, kTimeoutMs);
  }

  public void setTurretPID(double kP, double kI, double kD, double kF) {
    turretMotor.configFactoryDefault();

    turretMotor.setSensorPhase(true);
    turretMotor.configNominalOutputForward(0, kTimeoutMs);
		turretMotor.configNominalOutputReverse(0, kTimeoutMs);
		turretMotor.configPeakOutputForward(0.5, kTimeoutMs);
		turretMotor.configPeakOutputReverse(-0.5, kTimeoutMs);

		/* Config the Velocity closed loop gains in slot0 */
		turretMotor.config_kF(0, kF, kTimeoutMs);
		turretMotor.config_kP(0, kP, kTimeoutMs);
		turretMotor.config_kI(0, kI, kTimeoutMs);
    turretMotor.config_kD(0, kD, kTimeoutMs);
  }

  public double getTurretPosition() {
      return turretMotor.getSelectedSensorPosition();
  }

  public void resetTurretPosition() {
    turretMotor.setSelectedSensorPosition(0.0);
  }

  public void turnOnShooter() {
    rightShooter.set(ControlMode.PercentOutput, 1.0);
    leftShooter.set(ControlMode.PercentOutput, -1.0);
  }

  public void turnOffShooter() {
    rightShooter.set(ControlMode.PercentOutput, 0.0);
    leftShooter.set(ControlMode.PercentOutput, 0.0);
  }

  public void turnTurretRight() {
    turretMotor.set(ControlMode.PercentOutput, 0.3);
  }

  public void turnTurretLeft() {
    turretMotor.set(ControlMode.PercentOutput, -0.3);
  }

  public void turnTurretRight(double speed) {
    turretMotor.set(ControlMode.Velocity, speed);
  }

  public void turnTurretLeft(double speed) {
    turretMotor.set(ControlMode.Velocity, speed);
  }

  public void stopTurretSpin() {
    turretMotor.set(ControlMode.PercentOutput, 0.0);
  }

  public void turretUp() {
    turretSolenoid.set(Value.kForward);
  }

  public void turretDown() {
    turretSolenoid.set(Value.kReverse);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Shooter Position", getTurretPosition());
    SmartDashboard.putNumber("Shooter Output", turretMotor.getMotorOutputPercent());
    SmartDashboard.putNumber("Shooter Left Speed", getLeftShooterSpeed());
    SmartDashboard.putNumber("Shooter Right Speed", getRightShooterSpeed());
    SmartDashboard.putNumber("Shooter Left Pos", rightShooter.getSelectedSensorPosition());
  }
} 
