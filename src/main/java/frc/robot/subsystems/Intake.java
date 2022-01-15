// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

  private TalonSRX intakeArm = null;
  private TalonSRX intakeEnd = null;
  private DoubleSolenoid intakeSolenoid = null;

  /** Creates a new Intake. */
  public Intake() {
    intakeArm = new TalonSRX(Constants.INTAKE_ARM_TALON);
    intakeEnd = new TalonSRX(Constants.INTAKE_END_TALON);
    intakeSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.INTAKE_SOLENOID1, Constants.INTAKE_SOLENOID2);
  }

  public void intakeOn() {
    intakeArm.set(ControlMode.PercentOutput, 1.0);
    intakeEnd.set(ControlMode.PercentOutput, 1.0);
  }

  public void intakeOff() {
    intakeArm.set(ControlMode.PercentOutput, 0.0);
    intakeEnd.set(ControlMode.PercentOutput, 0.0);
  }

  public void intakeUp() {
    intakeSolenoid.set(Value.kForward);
  }

  public void intakeDown() {
    intakeSolenoid.set(Value.kReverse);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
