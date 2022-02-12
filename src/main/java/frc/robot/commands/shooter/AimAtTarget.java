// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class AimAtTarget extends CommandBase {
  private Shooter shooter = null;
  private NetworkTableInstance inst = NetworkTableInstance.getDefault();
  private NetworkTable visionTable = inst.getTable("Vision");
  private NetworkTableEntry output, height, x, y;
  private int camera_height = 480;
  private int camera_width = 640;
  private int camera_center = 320;
  private double ticksPerDegree = 76.21;

  /** Creates a new AimAtTarget. */
  public AimAtTarget(Shooter m_shooter) {
    shooter = m_shooter;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    output = visionTable.getEntry("Vision Output");
    height = visionTable.getEntry("Vision Height");
    x = visionTable.getEntry("Vision X");
    y = visionTable.getEntry("Vision Y");
    shooter.setTurretPID(
      Preferences.getDouble("TurretP", 0.05), 
      Preferences.getDouble("TurretI", 0), 
      Preferences.getDouble("TurretD", 0), 
      Preferences.getDouble("TurretF", 0.0362),
      Preferences.getDouble("TurretIz", 0)
    );
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putNumber("Vision Height", height.getNumber(0).intValue());
    if (output.getNumber(0).intValue() > 0) {
      SmartDashboard.putNumber("Vision Output X", x.getNumber(camera_center).doubleValue());
      SmartDashboard.putNumber("Vision Output Y", y.getNumber(0.0).doubleValue());
      int x_int = x.getNumber(camera_center).intValue();
      //System.out.println("X: " + x_int + " Num Contours: " + output.getNumber(0).intValue());
      if ((camera_center - 10) <= x_int && x_int <= (camera_center + 10)) {
        //shooter.stopTurretSpin();
        shooter.turnTurretLeft(0.0);
        //System.out.println("Trying to stop");
      } else if (x_int < (camera_center - 10)) {
        shooter.turnTurretTicks(-ticksPerDegree * 2);
        //shooter.turnTurretLeft((x_int - (camera_center - 10)) * 8);
        //shooter.turnTurretLeft(-2000);
        //System.out.println("Trying to go left");
      } else if (x_int > (camera_center + 10)) {
        shooter.turnTurretTicks(ticksPerDegree * 2);
        //shooter.turnTurretRight((x_int - (camera_center + 10)) * 8);
        //shooter.turnTurretRight(1000);
        //System.out.println("Trying to go right");
      } else {
        System.out.println("VISION TRACKING: BAD STATE!!!!");
      }
    } else {
      //System.out.println("Turret Output Percent: " + shooter.turretStopped());
      /*if (shooter.turretStopped() && !shooter.turretAtLeftBound() && !shooter.turretAtRightBound()) {
        shooter.turnTurretLeft(-500);
      } else if (shooter.turretAtLeftBound()) {
        shooter.turnTurretRight(500);
      } else if (shooter.turretAtRightBound()) {
        shooter.turnTurretLeft(-500);
      }*/
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //shooter.stopTurretSpin();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    /*int x_int = x.getNumber(0).intValue();
    if ((camera_center - 10) <= x_int && x_int <= (camera_center + 10)) {
      return true;
    } else {
      return false;
    }*/
    return true;
  }
}
