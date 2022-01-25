// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.interfaces.Drivetrain;
import com.revrobotics.SparkMaxPIDController;

public class DrivetrainSparkMax extends SubsystemBase implements Drivetrain {

  CANSparkMax leftSpark1 = null;
  CANSparkMax leftSpark2 = null;
  CANSparkMax rightSpark1 = null;
  CANSparkMax rightSpark2 = null;

  DifferentialDrive differentialDrive = null;

  /** Creates a new DrivetrainSparkMax. */
  public DrivetrainSparkMax() {
    leftSpark1 = new CANSparkMax(Constants.DRIVETRAIN_LEFT_FRONT, MotorType.kBrushless);
    leftSpark2 = new CANSparkMax(Constants.DRIVETRAIN_LEFT_BACK, MotorType.kBrushless);
    rightSpark1 = new CANSparkMax(Constants.DRIVETRAIN_RIGHT_FRONT, MotorType.kBrushless);
    rightSpark2 = new CANSparkMax(Constants.DRIVETRAIN_RIGHT_BACK, MotorType.kBrushless);

    leftSpark2.follow(leftSpark1);
    rightSpark2.follow(rightSpark1);
    leftSpark1.setInverted(true);

    differentialDrive = new DifferentialDrive(rightSpark1, leftSpark1);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setPID(int slotId, double p, double i, double d) {   
    SparkMaxPIDController left1 = leftSpark1.getPIDController();
    SparkMaxPIDController left2 = leftSpark2.getPIDController();
    SparkMaxPIDController right1 = rightSpark1.getPIDController();
    SparkMaxPIDController right2 = rightSpark2.getPIDController();   
  }

  public void arcadeDrive(double moveSpeed, double rotateSpeed) {
    differentialDrive.arcadeDrive(moveSpeed, rotateSpeed);
  }

  @Override
  public double encoderToDistanceInch(double rotations) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public double getLeftEnc() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public double getRightEnc() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void resetEncoders() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setMotors(double left, double right) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setPID(double p, double i, double d, double Iz) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setPIDReference(double leftReference, double rightReference, ControlType controlType) {
    // TODO Auto-generated method stub
    
  }
}
