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
import edu.wpi.first.wpilibj.Preferences;

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

  private static final int leftBound = 6856;
  private static final int rightBound = -6856;

  private static final double h1 = 15.25; // Height of camera
  private double h2 = 0;
  private static final double a1 = 27.5; // Angle of camera

  /** Creates a new Shooter. */
  public Shooter() {
    rightShooter = new WPI_TalonSRX(Constants.SHOOTER_RIGHT_TALON);
    rightShooter.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, kTimeoutMs);
    leftShooter = new WPI_TalonSRX(Constants.SHOOTER_LEFT_TALON);
    leftShooter.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, kTimeoutMs);
    
    turretMotor = new WPI_TalonSRX(Constants.SHOOTER_TURRET_TALON);
    turretMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, kTimeoutMs);
    
    turretSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.SHOOTER_SOLENOID0, Constants.SHOOTER_SOLENOID3);
  }

  public void setShooterSpeed(double speed) {
    leftShooter.set(ControlMode.Velocity, -speed);
    rightShooter.set(ControlMode.Velocity, speed);
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

  public void setTurretPID(double kP, double kI, double kD, double kF, double kIz) {
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
    turretMotor.config_IntegralZone(0, kIz, kTimeoutMs);
  }

  public double getTurretPosition() {
      return turretMotor.getSelectedSensorPosition();
  }

  public void resetTurretPosition() {
    turretMotor.setSelectedSensorPosition(0.0);
  }

  public boolean turretAtLeftBound() {
    return leftBound <= getTurretPosition();
  }

  public boolean turretAtRightBound() {
    return getTurretPosition() <= rightBound;
  }

  public boolean turretTurningLeft() {
    return turretMotor.getMotorOutputPercent() < 0;
  }

  public boolean turretStopped() {
    return turretMotor.getMotorOutputPercent() == 0;
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
    turretMotor.set(ControlMode.Velocity, -speed);
  }

  public void turnTurretTicks(double ticks) {
    turretMotor.set(ControlMode.Position, ticks);
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

  public boolean isTurretUp() {
    return turretSolenoid.get() == Value.kForward ? true : false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Shooter Position", getTurretPosition());
    SmartDashboard.putNumber("Shooter Output", turretMotor.getMotorOutputPercent());
    SmartDashboard.putNumber("Shooter Left Speed", getLeftShooterSpeed());
    SmartDashboard.putNumber("Shooter Right Speed", getRightShooterSpeed());
    SmartDashboard.putNumber("Shooter Left Pos", rightShooter.getSelectedSensorPosition());

    if (turretAtLeftBound() || turretAtRightBound()) {
      stopTurretSpin();
    }
  }
} 
