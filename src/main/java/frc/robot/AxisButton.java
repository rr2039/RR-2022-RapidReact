// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Button;

public class AxisButton extends Button { // change to Trigger?

	Joystick joystick;
	int axis;
	double pressThreshold;
	double releaseThreshold;
	int reverseFactor;
	boolean wasPressed;
	
	public AxisButton(Joystick joystick, int axis, double pressThreshold, double releaseThreshold, boolean reverse) {
		this.joystick = joystick;
		this.axis = axis;
		this.reverseFactor = reverse ? -1 : 1;
		this.pressThreshold = pressThreshold * reverseFactor;
		this.releaseThreshold = releaseThreshold * reverseFactor;
		this.wasPressed = false;
	}

	public AxisButton(Joystick joystick, int axis, double pressThreshold, double releaseThreshold) {
		this(joystick, axis, pressThreshold, releaseThreshold, false);
	}
	
	@Override
	public boolean get() {
		double threshold = wasPressed ? releaseThreshold : pressThreshold;
		wasPressed = reverseFactor * joystick.getRawAxis(axis) > threshold; 
		return wasPressed;
	}

	
}
