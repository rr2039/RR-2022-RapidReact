// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Shooter extends SubsystemBase {
  WPI_TalonSRX rightShooter = null;
  WPI_TalonSRX leftShooter = null;
  WPI_TalonSRX turretMotor = null;
  DoubleSolenoid turretSolenoid = null;

  /** Creates a new Shooter. */
  public Shooter() {
    rightShooter = new WPI_TalonSRX(Constants.SHOOTER_RIGHT_TALON);
    leftShooter = new WPI_TalonSRX(Constants.SHOOTER_LEFT_TALON);
    turretMotor = new WPI_TalonSRX(Constants.SHOOTER_TURRET_TALON);
    turretMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    turretSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.SHOOTER_SOLENOID0, Constants.SHOOTER_SOLENOID3);
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
  }
} 
