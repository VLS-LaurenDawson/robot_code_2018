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

import org.usfirst.frc6357.SpringKonstant.commands.AutoPlan1;
import org.usfirst.frc6357.SpringKonstant.commands.AutoPlan2;
import org.usfirst.frc6357.SpringKonstant.commands.AutoPlan3;
import org.usfirst.frc6357.SpringKonstant.commands.GearPush;
import org.usfirst.frc6357.SpringKonstant.subsystems.DriveBaseSystem;
import org.usfirst.frc6357.SpringKonstant.subsystems.GearDeploymentSystem;
import org.usfirst.frc6357.SpringKonstant.subsystems.RopeClimbSystem;
//import org.usfirst.frc6357.SpringKonstant.subsystems.GearDeploymentSystem.gearState;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot 
{

    Command autonomousCommand;
    SendableChooser<Command> autoChooser;

    public static OI oi;
    
    // Subsystems
    public static GearDeploymentSystem gearDeploymentSystem;
    public static RopeClimbSystem ropeClimbSystem;
    public static DriveBaseSystem driveBaseSystem;
    
    // Joysticks
    public static Joystick driver;
    public static Joystick operator;
    
    // Actuators
    public static DoubleSolenoid gearDoubleSolenoidRight;
    public static DoubleSolenoid gearDoubleSolenoidLeft;
    public static DoubleSolenoid gearDoubleSolenoidPush;

    public static SpeedController baseFrontLeft;
    public static SpeedController baseCenterLeft;
    public static SpeedController baseBackLeft;
    public static SpeedController baseFrontRight;
    public static SpeedController baseCenterRight;
    public static SpeedController baseBackRight;
    public static SpeedController ropeMotor1;
    public static SpeedController ropeMotor2;
    public static Compressor compressor1;
    //encoders
    public static Encoder encoderLeft;
	public static Encoder encoderRight;
	//private double wait;
	//gyroscope
	//public static ADIS16448_IMU myIMU;
	
	//Auto
	public static AutonomousMatchController auto;
	
	//private double leftJoystickOffset;
	//private double rightJoystickOffset;

	private final Timer myTimer = new Timer();
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() 
    {	
    	// Actuators
    	compressor1 = new Compressor(1);
    	
        gearDoubleSolenoidRight = new DoubleSolenoid(1, 6, 4);					
        
        gearDoubleSolenoidLeft = new DoubleSolenoid(1, 1, 0);									
        
        gearDoubleSolenoidPush = new DoubleSolenoid(1, 3, 2);
       
        
        
        //TALON SRX ASSIGNMENTS:
        // LEFT 10,11,15
        // RIGHT 12,14,16
        // THE TALONS ARE SET UP TO USE FOLLOWING, so we only need the front left and front right
        
        baseFrontLeft = new CANTalon(10);
        baseCenterLeft = new CANTalon(11);
        ((CANTalon)baseCenterLeft).changeControlMode(CANTalon.TalonControlMode.Follower);
        ((CANTalon)baseCenterLeft).set(((CANTalon)baseFrontLeft).getDeviceID());
        baseBackLeft = new CANTalon(15);
        ((CANTalon)baseBackLeft).changeControlMode(CANTalon.TalonControlMode.Follower);
        ((CANTalon)baseBackLeft).set(((CANTalon)baseFrontLeft).getDeviceID());
        
        baseFrontRight = new CANTalon(12);
        baseFrontRight.setInverted(true);
        baseCenterRight = new CANTalon(14);
        ((CANTalon)baseCenterRight).changeControlMode(CANTalon.TalonControlMode.Follower);
        ((CANTalon)baseCenterRight).set(((CANTalon)baseFrontRight).getDeviceID());
        baseBackRight = new CANTalon(16);
        ((CANTalon)baseBackRight).changeControlMode(CANTalon.TalonControlMode.Follower);
        ((CANTalon)baseBackRight).set(((CANTalon)baseFrontRight).getDeviceID());
        
        ropeMotor1 = new CANTalon(20);
        
        ropeMotor2 = new CANTalon(21);
        
    	
        //Encoders 
        encoderLeft = new Encoder(2, 3);
        encoderRight = new Encoder(0, 1);
        
        // IMPORTANT CONVENTION: ALWYAS FEET PER SECOND
        final double DistancePerPulse = (3.1415926539*4.0/12.0)/384.0;
        
        encoderLeft.setDistancePerPulse(DistancePerPulse);
        encoderRight.setDistancePerPulse(DistancePerPulse);
        
    	// Subsystems
    	gearDeploymentSystem = new GearDeploymentSystem(gearDoubleSolenoidLeft, gearDoubleSolenoidRight, gearDoubleSolenoidPush);
    	ropeClimbSystem = new RopeClimbSystem(ropeMotor1, ropeMotor2);
    	driveBaseSystem = new DriveBaseSystem(baseFrontLeft, baseFrontRight, encoderLeft, encoderRight);
        
    	//Auto
        //auto = new AutonomousMatchController(encoderRight, encoderLeft, driveBaseSystem);

    	
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        // instantiate the command used for the autonomous period
       
        //GyroScope 
        //myIMU = new ADIS16448_IMU();
        //myIMU.reset();
        
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit()
    {

    }

    public void disabledPeriodic() 
    {
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("rvel", encoderRight.getRate());
        SmartDashboard.putNumber("lvel", encoderLeft.getRate());
        SmartDashboard.putNumber("rpos", encoderRight.getDistance());
        SmartDashboard.putNumber("lpos", encoderLeft.getDistance());
        SmartDashboard.putNumber("l_setpt", driveBaseSystem.GetLeftSpeedSetpoint());
        SmartDashboard.putNumber("r_setpt", driveBaseSystem.GetRightSpeedSetpoint());
        //SmartDashboard.putString("git revision", GitRevisionEvaluator.GetGitRevision());
        //SmartDashboard.putData("IMU", myIMU);
        driveBaseSystem.setLeftMotorSpeedPercent(0.0f);
        driveBaseSystem.setRightMotorSpeedPercent(0.0f);
        
        autoChooser = new SendableChooser<Command>();
        autoChooser.addDefault("Middle", new AutoPlan1());
        autoChooser.addObject("Left", new AutoPlan2());
        autoChooser.addObject("Right", new AutoPlan3());
        SmartDashboard.putData("Auto Plan Selector", autoChooser);       
    }

    public void autonomousInit() 
    {
    	//autonomousCommand = (Command) autoChooser.getSelected();
        // schedule the autonomous command (example)
        //if (autonomousCommand != null) autonomousCommand.start();
        //gyro1.calibrate();
    	encoderRight.reset();
    	encoderLeft.reset();
    	driveBaseSystem.Enable();
    	driveBaseSystem.SetPositionMode();
    	
    	gearDeploymentSystem.resetSolenoids();
    	
    	myTimer.start();
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() 
    {
        Scheduler.getInstance().run();
        driveBaseSystem.DriveStraight(10);
        
        SmartDashboard.putNumber("rvel", encoderRight.getRate());
        SmartDashboard.putNumber("lvel", encoderLeft.getRate());
        SmartDashboard.putNumber("rpos", encoderRight.getDistance());
        SmartDashboard.putNumber("lpos", encoderLeft.getDistance());
        SmartDashboard.putNumber("l_setpt", driveBaseSystem.GetLeftSpeedSetpoint());
        SmartDashboard.putNumber("r_setpt", driveBaseSystem.GetRightSpeedSetpoint());
        SmartDashboard.putNumber("l_drive", baseFrontLeft.get());
        SmartDashboard.putNumber("r_drive", baseFrontRight.get());
    }

    public void teleopInit() 
    {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        
        driver = oi.getDriver();
        operator = oi.getOperator();
        compressor1.start();
        compressor1.enabled();
        driveBaseSystem.SetVelocityMode();
        driveBaseSystem.setLeftMotorSpeedPercent(0.0f);
        driveBaseSystem.setRightMotorSpeedPercent(0.0f);
        
        gearDeploymentSystem.resetSolenoids();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() 
    {
        Scheduler.getInstance().run();
        
        double leftDrive = -1 * driver.getRawAxis(1);
        double rightDrive = -1 * driver.getRawAxis(5);
        
        //compressor1.start();
        
        if(Math.abs(leftDrive) < 0.05)
        {
        	leftDrive = 0.0f;
        }
        if(Math.abs(rightDrive) < 0.05)
        {
        	rightDrive = 0.0f;
        }
        
        
        driveBaseSystem.setLeftMotorSpeedPercent(leftDrive);
        driveBaseSystem.setRightMotorSpeedPercent(rightDrive);
        
        SmartDashboard.putNumber("rvel", encoderRight.getRate());
        SmartDashboard.putNumber("lvel", encoderLeft.getRate());
        SmartDashboard.putNumber("rpos", encoderRight.getDistance());
        SmartDashboard.putNumber("lpos", encoderLeft.getDistance());
        
        SmartDashboard.putNumber("l_setpt", driveBaseSystem.GetLeftSpeedSetpoint());
        SmartDashboard.putNumber("r_setpt", driveBaseSystem.GetRightSpeedSetpoint());
        SmartDashboard.putNumber("l_drive", baseFrontLeft.get());
        SmartDashboard.putNumber("r_drive", baseFrontRight.get());
    
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() 
    {
        LiveWindow.run();
    }
}
