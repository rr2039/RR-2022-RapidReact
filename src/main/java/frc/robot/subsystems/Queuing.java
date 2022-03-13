// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants;

public class Queuing extends SubsystemBase {

  CANSparkMax topSpark = null;
  CANSparkMax bottomSpark = null;

  double openRampRate = 0.5;
  double closedRampRate = 2.0;
  /** Creates a new Queuing. */
  public Queuing() {
    topSpark = new CANSparkMax(Constants.QUEUING_TOP_SPARK, MotorType.kBrushless);
    bottomSpark = new CANSparkMax(Constants.QUEUING_BOTTOM_SPARK, MotorType.kBrushless);

    topSpark.setOpenLoopRampRate(Preferences.getDouble("Ramp", openRampRate));
    bottomSpark.setOpenLoopRampRate(Preferences.getDouble("Ramp", openRampRate));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void indexBall() {
    topSpark.set(0.5);
    bottomSpark.set(-0.5);
  }

  public void queuingOff() {
    topSpark.set(0.0);
    bottomSpark.set(0.0);
  }

  public void expelBall() {
    topSpark.set(-0.5);
    bottomSpark.set(0.5);
  }

  public void queuingOn() {
    bottomSpark.set(0.5);
  }
}
