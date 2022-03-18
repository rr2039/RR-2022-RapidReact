// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants;

public class Queuing extends SubsystemBase {

  CANSparkMax topRoller = null;
  CANSparkMax bottomRoller = null;

  /** Creates a new Queuing. */
  public Queuing() {
    topRoller = new CANSparkMax(Constants.QUEUING_TOP_SPARK, MotorType.kBrushless);
    bottomRoller = new CANSparkMax(Constants.QUEUING_BOTTOM_SPARK, MotorType.kBrushless);

    topRoller.setOpenLoopRampRate(0.5);
    bottomRoller.setOpenLoopRampRate(0.5);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setRollers(double top, double bottom) {
    topRoller.set(top);
    bottomRoller.set(bottom);
  }
}
