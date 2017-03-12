// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc6357.SpringKonstant;

import org.usfirst.frc6357.SpringKonstant.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;
import org.usfirst.frc6357.SpringKonstant.subsystems.*;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI 
{
    //// CREATING BUTTONS TEST
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());

	public Joystick driver, operator;
    public Button a, b, x, y, rb, lb, start, slowDrive, stopSlowDrive;
    
    public OI() 
    {

        operator = new Joystick(1);
        driver = new Joystick(0);

        a = new JoystickButton(operator, 1);
        b = new JoystickButton(operator, 2);
        x = new JoystickButton(operator, 3);
        y = new JoystickButton(operator, 4);
        rb = new JoystickButton(operator, 5);
        lb = new JoystickButton(operator, 6);
        start = new JoystickButton(operator, 8);
        stopSlowDrive = new JoystickButton(driver, 5);
        slowDrive = new JoystickButton(driver, 6);
        
        a.whenPressed(new ForwardGearDoubleSolenoid());
        b.whenPressed(new ReverseGearDoubleSolenoid());
        x.whenPressed(new GearPush());
        y.whenPressed(new GearPull());
        rb.whenPressed(new RopeDown());
        lb.whenPressed(new RopeUp());
        start.whenPressed(new WinchStop());
        stopSlowDrive.whenPressed(new StopSlowDrive());
        slowDrive.whenPressed(new SlowDrive());


       
        
        SmartDashboard.putData("Gear Slide Down", new ForwardGearDoubleSolenoid());
        SmartDashboard.putData("Gear Slide Up", new ReverseGearDoubleSolenoid());
        SmartDashboard.putData("Gear Push", new GearPush());
        SmartDashboard.putData("Gear Pull", new GearPull());
        SmartDashboard.putData("Rope Up", new RopeUp());
        SmartDashboard.putData("Rope Down", new RopeDown());
        SmartDashboard.putData("AutoPlan1", new AutoPlan1());
        SmartDashboard.putData("AutoPlan2", new AutoPlan2());
        SmartDashboard.putData("AutoPlan3", new AutoPlan3());

    }

    public Joystick getDriver() 
    {
        return driver;
    }

    public Joystick getOperator() 
    {
        return operator;
    }
}

