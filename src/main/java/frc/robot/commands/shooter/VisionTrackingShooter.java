// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;


public class VisionTrackingShooter extends CommandBase {
  /** Creates a new VisionTrackingShooter. */
  private Shooter shooter = null;
  private NetworkTableInstance inst = NetworkTableInstance.getDefault();
  private NetworkTable visionTable = inst.getTable("Vision");
  
  public VisionTrackingShooter(Shooter m_shooter) {
    shooter = m_shooter;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    NetworkTableEntry output = visionTable.getEntry("Vision Output");
    NetworkTableEntry height = visionTable.getEntry("Vision Height");
    SmartDashboard.putNumber("Vision Height", height.getNumber(0).intValue());
    if (output.getNumber(0).intValue() > 0) {
      NetworkTableEntry x = visionTable.getEntry("Vision X");
      NetworkTableEntry y = visionTable.getEntry("Vision Y");
      SmartDashboard.putNumber("Vision Output X", x.getNumber(640.0).doubleValue());
      SmartDashboard.putNumber("Vision Output Y", y.getNumber(0.0).doubleValue());
      int x_int = x.getNumber(640.0).intValue();
      //System.out.println("X: " + x_int + " Num Contours: " + output.getNumber(0).intValue());
      if (630 <= x_int && x_int <= 650) {
        //shooter.stopTurretSpin();
        shooter.turnTurretLeft(0.0);
        //System.out.println("Trying to stop");
      } else if (x_int < 630) {
        shooter.turnTurretLeft((x_int - 630) * 4.5);
        //shooter.turnTurretLeft(-2000);
        //System.out.println("Trying to go left");
      } else if (x_int > 650) {
        shooter.turnTurretRight((x_int - 650) * 4.5);
        //shooter.turnTurretRight(1000);
        //System.out.println("Trying to go right");
      } else {
        System.out.println("VISION TRACKING: BAD STATE!!!!");
      }
    } else {
      shooter.stopTurretSpin();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stopTurretSpin();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
