// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.revrobotics.CANSparkMax.ControlType;

public class Elevator extends SubsystemBase {
    CANSparkMax leftSpark = null;
    CANSparkMax rightSpark = null;

    RelativeEncoder leftEnc = null;
    RelativeEncoder rightEnc = null;

    SparkMaxPIDController leftPID = null;
    SparkMaxPIDController rightPID = null;

    double openRampRate = 10;
    double closedRampRate = 10;

    DoubleSolenoid pistons = null;

  /**Creates a new ShooterSparkMax. **/
  public Elevator() {
    leftSpark = new CANSparkMax(Constants.SHOOTER_LEFT_SPARK, MotorType.kBrushless);
    rightSpark = new CANSparkMax(Constants.SHOOTER_RIGHT_SPARK, MotorType.kBrushless);

    leftSpark.setOpenLoopRampRate(Preferences.getDouble("Ramp", openRampRate));
    rightSpark.setOpenLoopRampRate(Preferences.getDouble("Ramp", openRampRate));

    leftPID = leftSpark.getPIDController();
    leftPID.setOutputRange(-1.0, 1.0);
    rightPID = rightSpark.getPIDController();
    rightPID.setOutputRange(-1.0, 1.0);
    
    leftEnc = leftSpark.getEncoder();
    leftEnc.setPositionConversionFactor(2.88);
    rightEnc = rightSpark.getEncoder();
    rightEnc.setPositionConversionFactor(2.88);

    pistons = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.ELEVATOR_SOLENOID0, Constants.ELEVATOR_SOLENOID1);
  } 

  public void elevatorPistonUp() {
    pistons.set(Value.kForward);
  }

  public void elevatorPistonDown() {
    pistons.set(Value.kReverse);
  }

  public void setPID(double p, double i, double d, double f, double Iz) {
    //Left
    leftPID.setP(p);
    leftPID.setI(i);
    leftPID.setD(d);
    leftPID.setFF(f);
    leftPID.setIZone(Iz);
    //Right
    rightPID.setP(p);
    rightPID.setI(i);
    rightPID.setD(d);
    rightPID.setFF(f);
    rightPID.setIZone(Iz);
  }

  public void setPIDReference(double leftReference, double rightReference, ControlType controlType) {
    leftPID.setReference(leftReference, controlType);
    rightPID.setReference(rightReference, controlType);
  }

  public void setMotors(double left, double right) {
    leftSpark.set(left);
    rightSpark.set(right);
  }

  public void resetEncoders() {
    leftEnc.setPosition(0.0);
    rightEnc.setPosition(0.0);
  }

  public double getLeftEnc() {
    return leftEnc.getPosition();
  }

  public double getRightEnc() {
    return rightEnc.getPosition();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Elevator Left", leftEnc.getPosition());
    SmartDashboard.putNumber("Elevator Right", rightEnc.getPosition());
  }
}
