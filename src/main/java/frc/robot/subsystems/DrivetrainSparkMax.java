// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.interfaces.Drivetrain;

public class DrivetrainSparkMax extends SubsystemBase implements Drivetrain {

  CANSparkMax leftSpark1 = null;
  CANSparkMax leftSpark2 = null;
  CANSparkMax rightSpark1 = null;
  CANSparkMax rightSpark2 = null;

  RelativeEncoder leftEnc = null;
  RelativeEncoder rightEnc = null;

  SparkMaxPIDController leftPID = null;
  SparkMaxPIDController rightPID = null;

  AnalogGyro drivetrainGyro = null;

  DifferentialDrive differentialDrive = null;

  double openRampRate = 0.5;
  double closedRampRate = 2.0;

  /** Creates a new DrivetrainSparkMax. */
  public DrivetrainSparkMax() {
    leftSpark1 = new CANSparkMax(Constants.DRIVETRAIN_LEFT_FRONT, MotorType.kBrushless);
    leftSpark2 = new CANSparkMax(Constants.DRIVETRAIN_LEFT_BACK, MotorType.kBrushless);
    rightSpark1 = new CANSparkMax(Constants.DRIVETRAIN_RIGHT_FRONT, MotorType.kBrushless);
    rightSpark2 = new CANSparkMax(Constants.DRIVETRAIN_RIGHT_BACK, MotorType.kBrushless);
    
    //leftSpark1.setClosedLoopRampRate(Preferences.getDouble("Ramp", closedRampRate));
    leftSpark1.setOpenLoopRampRate(Preferences.getDouble("Ramp", openRampRate));
    //leftSpark2.setClosedLoopRampRate(Preferences.getDouble("Ramp", closedRampRate));
    leftSpark2.setOpenLoopRampRate(Preferences.getDouble("Ramp", openRampRate));
    //rightSpark1.setClosedLoopRampRate(Preferences.getDouble("Ramp", closedRampRate));
    rightSpark1.setOpenLoopRampRate(Preferences.getDouble("Ramp", openRampRate));
    //rightSpark2.setClosedLoopRampRate(Preferences.getDouble("Ramp", closedRampRate));
    rightSpark2.setOpenLoopRampRate(Preferences.getDouble("Ramp", openRampRate));

    leftPID = leftSpark1.getPIDController();
    leftPID.setOutputRange(-1.0, 1.0);
    rightPID = rightSpark1.getPIDController();
    rightPID.setOutputRange(-1.0, 1.0);
    
    leftEnc = leftSpark1.getEncoder();
    leftEnc.setPositionConversionFactor(18.84 / 7.31);
    rightEnc = rightSpark1.getEncoder();
    rightEnc.setPositionConversionFactor(18.84 / 7.31);

    leftSpark2.follow(leftSpark1);
    rightSpark2.follow(rightSpark1);
    leftSpark1.setInverted(true);

    drivetrainGyro = new AnalogGyro(0);
    drivetrainGyro.setSensitivity(Constants.GYRO_kVoltsPerDegreePerSecond);

    differentialDrive = new DifferentialDrive(rightSpark1, leftSpark1);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Drivetrain Left", leftEnc.getPosition());
    SmartDashboard.putNumber("Drivetrain Right", rightEnc.getPosition());
    SmartDashboard.putNumber("P", rightPID.getP());
    SmartDashboard.putNumber("I", rightPID.getI());
    SmartDashboard.putNumber("D", rightPID.getD());
    SmartDashboard.putNumber("Gyro Pos", drivetrainGyro.getAngle());
    SmartDashboard.putNumber("Gyro Rate", drivetrainGyro.getRate());
  }

  public void setMotors(double left, double right) {
    leftSpark1.set(left);
    rightSpark1.set(right);
  }

  public double encoderToDistanceInch(double rotations) {
    return rotations * (18.84 / 7.31);
  }

  public double getLeftEnc() {
    return leftEnc.getPosition();
  }

  public double getRightEnc() {
    return rightEnc.getPosition();
  }

  public void resetEncoders() {
    leftEnc.setPosition(0.0);
    rightEnc.setPosition(0.0);
  }

  public void setPID(double p, double i, double d, double Iz) {
    //Left
    leftPID.setP(p);
    leftPID.setI(i);
    leftPID.setD(d);
    leftPID.setIZone(Iz);
    //Right
    rightPID.setP(p);
    rightPID.setI(i);
    rightPID.setD(d);
    rightPID.setIZone(Iz);
  }

  public void setPIDReference(double leftReference, double rightReference, ControlType controlType) {
    leftPID.setReference(leftReference, controlType);
    rightPID.setReference(rightReference, controlType);
  }

  public void arcadeDrive(double moveSpeed, double rotateSpeed) {
    differentialDrive.arcadeDrive(moveSpeed, rotateSpeed);
  }
}