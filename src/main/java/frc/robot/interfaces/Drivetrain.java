package frc.robot.interfaces;

import edu.wpi.first.wpilibj2.command.Subsystem;

public interface Drivetrain extends Subsystem {
    public void arcadeDrive(double moveSpeed, double rotateSpeed);
}
