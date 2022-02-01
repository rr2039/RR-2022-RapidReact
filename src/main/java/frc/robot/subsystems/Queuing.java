 // Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Queuing extends SubsystemBase {

  private TalonSRX shooterFeed = null;
  private TalonSRX queuing = null;

  /** Creates a new Queuing. */
  public Queuing() {
    shooterFeed = new TalonSRX(Constants.SHOOTER_FEED_TALON);
    queuing = new TalonSRX(Constants.QUEUING_TALON);
  }

  public void turnQueuingOn() {
    queuing.set(ControlMode.PercentOutput, -1.0);
  }

  public void turnQueuingOff() {
    queuing.set(ControlMode.PercentOutput, 0.0);
  }

  public void moveQueuing(double moveSpeed) {
    queuing.set(ControlMode.PercentOutput, moveSpeed);
  }

  public void turnShooterFeedOn() {
    shooterFeed.set(ControlMode.PercentOutput, 1.0);
    turnQueuingOn();
  }

  public void turnShooterFeedOff() {
    shooterFeed.set(ControlMode.PercentOutput, 0.0);
    turnQueuingOff();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
