// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.interfaces.Drivetrain;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax.ControlType;

/** Add your docs here. */
public class DrivetrainTalon extends SubsystemBase implements Drivetrain {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  WPI_TalonSRX leftTalon1 = null;
  WPI_TalonSRX leftTalon2 = null;
  WPI_TalonSRX rightTalon1 = null;
  WPI_TalonSRX rightTalon2 = null;

  DifferentialDrive differentialDrive = null;

  public DrivetrainTalon() {
    // init Talons
    leftTalon1 = new WPI_TalonSRX(Constants.DRIVETRAIN_LEFT_FRONT);
    leftTalon2 = new WPI_TalonSRX(Constants.DRIVETRAIN_LEFT_BACK);
    rightTalon1 = new WPI_TalonSRX(Constants.DRIVETRAIN_RIGHT_FRONT);
    rightTalon2 = new WPI_TalonSRX(Constants.DRIVETRAIN_RIGHT_BACK);

    leftTalon2.follow(leftTalon1);
    rightTalon2.follow(rightTalon1);

    differentialDrive = new DifferentialDrive(leftTalon1, rightTalon1);
  }

  public void arcadeDrive(double moveSpeed, double rotateSpeed) {
    differentialDrive.arcadeDrive(moveSpeed, rotateSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public double encoderToDistanceInch(double rotations) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void resetEncoders() {
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
  public void setMotors(double left, double right) {
    // TODO Auto-generated method stub
    
  }
}
