package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.Constants;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
   public Joystick driverController = new Joystick(Constants.DRIVER_CONTROLLER);

   Button D1 = new JoystickButton(driverController, 1);

   public OI() {
       //D1.whenPressed(new ShooterUp());
   }
}
