/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7144.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.hal.PDPJNI;

public class Robot extends IterativeRobot {

//Setting up auto, position sensitive
	private static final String C_AUTO = "Straight";
	
	

//SONIC CODY This number multiplies the joystick input by this number.
	private static final double TELEOP_SPEED = 1.0;

//These are the ports for your motors and the joystick port should be set to the joystick that you use to move
	private static final int MOTOR0_PORT = 0;
	private static final int MOTOR1_PORT = 1;
	private static final int JOYSTICK0_PORT = 0;
	private static final int JOYSTICK1_PORT = 1;

// Basic Setup
	private PWMSpeedController speedController0 = null;
	private PWMSpeedController speedController1 = null;
	private Timer mainTimer = null;
	private Joystick joystick = null;
	private DifferentialDrive drive = null;
	private String gameData;
	
//Setup for motors
	private Joystick joystick2;
	private PWMSpeedController speedController2 = null;
	private PWMSpeedController speedController3 = null;
	private static final int MOTOR2_PORT = 2;
	private static final int MOTOR3_PORT = 3;	
	private static final int JOYSTICK2_PORT = 1;
	private PWMSpeedController speedController4 = null;
	private PWMSpeedController speedController5 = null;
	private static final int MOTOR4_PORT = 4;
	private static final int MOTOR5_PORT = 5;	
	
//maybe how the auto is selected?
	private String autoSelected;
	private SendableChooser<String> chooser = new SendableChooser<>();

	@Override
	public void robotInit() {
//start camera - 1504
		CameraServer.getInstance().startAutomaticCapture();
// Setup Position. It always defaults to right. 
		chooser.addDefault("Straight", C_AUTO);
		chooser.addObject("Right Switch", R_AUTO);
		chooser.addObject("Left Switch", L_AUTO);
		chooser.addObject("Dream Center", R_SCALE);
		chooser.addObject("Left Scale", L_SCALE);		
		SmartDashboard.putData("Auto choices", chooser);

		speedController0 = new Spark(MOTOR0_PORT);
		speedController1 = new Spark(MOTOR1_PORT);
		mainTimer = new Timer();
		joystick = new Joystick(JOYSTICK_PORT);
		drive = new DifferentialDrive(speedController0, speedController1);
// Motors
//Arm
		speedController2 = new Spark(MOTOR2_PORT);
		speedController3 = new Spark(MOTOR3_PORT);	
		joystick2 = new Joystick(JOYSTICK2_PORT);	
//Claw
		speedController4 = new Spark(MOTOR4_PORT);
		speedController5 = new Spark(MOTOR5_PORT);	


		
//setup position buttons
		Scheduler.getInstance().run();
	}
	
	/**
	 * @return the joystick1Port
	 */
	public static int getJoystick1Port() {
		return JOYSTICK1_PORT;
	}

	/**
	 * @return the joystick0Port
	 */
	public static int getJoystick0Port() {
		return JOYSTICK0_PORT;
	}

	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		System.out.println(autoSelected);
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		mainTimer.reset();
		mainTimer.start();
	}

	/**
	 * This function is called periodically during autonomous. This is your running for loop
	 */
	@Override
	public void autonomousPeriodic() {

//Switch
		double AutoSpeed = 0.7;
		double AutoRotation = 0.22;
		double TimeToSwitch = 4.0;
		double ShootCube = 6.0;
		double AutoStop = 7.0;
		double SwitchPower = 0.85;		
	

//Scale
		double ScaleSpeed = 0.7;
		double ScaleRotation = 0.22;
		double TimeToScale = 4.0;
		double ShootScale = 6.0;
		double ScaleStop = 7.0;
		double ScalePower = 0.35;
		
		switch (autoSelected) {
String R_SCALE;
		// ALL the different Auto options are listed below--- Maybe this center will work - going right			
		case R_SCALE:
			if(gameData.charAt(0) == 'R')
			{
				//first turn was .37
				if(mainTimer.get() <= 1.57)
				{
				   drive.arcadeDrive(0.82, 0.547);
				}
				//second turn 
				else if(mainTimer.get() <= 2.11)
				{
					//was .07
				   drive.arcadeDrive(0.8, 0.10);
				}			
				//third turn
				else if(mainTimer.get() <= 3.0)
				{
				   drive.arcadeDrive(0.5, 0.21);
				}	
				else if (mainTimer.get() <= 5.0)
				{
                   drive.arcadeDrive(0.0, 0.0);
        		   speedController5.set(0.75);
        		   speedController4.set(0.75);                
				}
				else if (mainTimer.get() <= 6.0)
				{
                   drive.arcadeDrive(0.0, 0.0);
        		   speedController5.set(0.0);
        		   speedController4.set(0.0);  	                
				}				
				else
				{
                   drive.arcadeDrive(0.0, 0.0);
				}
			}
// Going Left
			if(gameData.charAt(0)=='L')
			{	
				//first turn
				if(mainTimer.get() <= 1.11)
				{
				   drive.arcadeDrive(0.8, -0.41);
				}
				//second turn - was 2.51
				else if(mainTimer.get() <= 2.41)
				{
				   drive.arcadeDrive(0.8, 0.58);
				}				
				//third turn - final value from Thursday
				else if(mainTimer.get() <= 5.35)
				{
					//WAS .23
				   drive.arcadeDrive(0.7, 0.27);
				}	
				else if (mainTimer.get() <= 6.0)
				{
                   drive.arcadeDrive(0.0, 0.0);
        		   speedController5.set(ScalePower);
        		   speedController4.set(ScalePower);                
				}
				else if (mainTimer.get() <= 9.0)
				{
                   drive.arcadeDrive(0.0, 0.0);
        		   speedController5.set(0.0);
        		   speedController4.set(0.0);  	                
				}
				else 
				{
	               drive.arcadeDrive(0.0, 0.0);						
				}	
			}		
            break;					
		
			case C_AUTO:
				if(mainTimer.get() <= TimeToSwitch)
				{
				drive.arcadeDrive(AutoSpeed, AutoRotation);
				}
				else
				{
                drive.arcadeDrive(0.0, 0.0);
				}
                break;
              //used to be right switch. change to charAt(0)
			case R_AUTO:
				if(gameData.charAt(0) == 'L')
				{

					if(mainTimer.get() <= TimeToSwitch)
					{
					drive.arcadeDrive(AutoSpeed, AutoRotation);
					}
					else
					{
	                drive.arcadeDrive(0.0, 0.0);
					}
				}
				if(gameData.charAt(0) == 'R')
				{	

					if(mainTimer.get() <= TimeToSwitch)
					{
					drive.arcadeDrive(AutoSpeed, AutoRotation);
					}
					else if (mainTimer.get() <= ShootCube)
					{
	                drive.arcadeDrive(0.0, 0.0);
	        		speedController5.set(SwitchPower);
	        		speedController4.set(SwitchPower);                
					}
					else if (mainTimer.get() <= AutoStop)
					{
	                drive.arcadeDrive(0.0, 0.0);
	        		speedController5.set(0.0);
	        		speedController4.set(0.0);  	                
					}
					else 
					{
		            drive.arcadeDrive(0.0, 0.0);						
					}	
				}		
                break;				

			case L_SCALE:
				if(gameData.charAt(0) == 'L')
				{

					if(mainTimer.get() <= TimeToScale)
					{
					drive.arcadeDrive(ScaleSpeed, ScaleRotation);
					}
					else if (mainTimer.get() <= ShootScale)
					{
	                drive.arcadeDrive(0.0, 0.0);
	        		speedController5.set(ScalePower);
	        		speedController4.set(ScalePower);                
					}
					else if (mainTimer.get() <= ScaleStop)
					{
	                drive.arcadeDrive(0.0, 0.0);
	        		speedController5.set(0.0);
	        		speedController4.set(0.0);  	                
					}					
					else
					{
	                drive.arcadeDrive(0.0, 0.0);
					}
				}
				if(gameData.charAt(0)=='R')
				{	

					if(mainTimer.get() <= TimeToScale)
					{
					drive.arcadeDrive(ScaleSpeed, ScaleRotation);
					}
					else
					{
	                drive.arcadeDrive(0.0, 0.0);
					}		
				}		
				break;                 
                
			case L_AUTO:
				if(gameData.charAt(0) == 'L')
				{

					if(mainTimer.get() <= TimeToSwitch)
					{
					drive.arcadeDrive(AutoSpeed, AutoRotation);
					}
					else if (mainTimer.get() <= ShootCube)
					{
	                drive.arcadeDrive(0.0, 0.0);
	        		speedController5.set(SwitchPower);
	        		speedController4.set(SwitchPower);                
					}
					else if (mainTimer.get() <= AutoStop)
					{
	                drive.arcadeDrive(0.0, 0.0);
	        		speedController5.set(0.0);
	        		speedController4.set(0.0);  	                
					}					
					else
					{
	                drive.arcadeDrive(0.0, 0.0);
					}
				}
				if(gameData.charAt(0)=='R')
				{	

					if(mainTimer.get() <= TimeToSwitch)
					{
					drive.arcadeDrive(AutoSpeed, AutoRotation);
					}
					else
					{
	                drive.arcadeDrive(0.0, 0.0);
					}		
				}		
				break; }
	}



	@Override
	public void teleopPeriodic() {
//SONIC CODY
		drive.arcadeDrive(joystick.getRawAxis(1) * TELEOP_SPEED, joystick.getRawAxis(0) * TELEOP_SPEED );
		Object PDPJNI;
		// SONIC JARRETT - ARM
		double worm_current = Math.max((PDPJNI.getPDPChannelCurrent((byte)2, 0) + PDPJNI.getPDPChannelCurrent((byte)13, 0)) / 250.0 + 0.15, 1.0);
		System.out.println(
				((Object) PDPJNI).getPDPChannelCurrent((byte) 2, 0) + PDPJNI.getPDPChannelCurrent((byte) 13, 0));
		speedController2.set( 1.0 * worm_current * joystick2.getY());
		speedController3.set(-1.0 * worm_current * joystick2.getY());
		System.out.println(41);
		if (joystick2.getRawAxis(3)>0)
//		Out
		{	
		speedController4.set(joystick2.getRawAxis(3));
		speedController5.set(joystick2.getRawAxis(3));
		}
		else if (joystick2.getRawAxis(2)>0)
//		IN
		{
		speedController4.set(-1.0*joystick2.getRawAxis(2));
		speedController5.set(-1.0*joystick2.getRawAxis(2));
		}
		else
		{
		speedController5.set(0.0);
		speedController4.set(0.0);
		}		

// Switch power: short hold
		if(joystick2.getRawButtonPressed(2))
		{
		speedController5.set(0.9);
		speedController4.set(0.9);
		}		
//Scale power: hold down longer		
		if(joystick2.getRawButtonPressed(3))
		{
		speedController5.set(1.0);
		speedController4.set(1.0);
		}			
			
			
	}


	@Override
	public void testPeriodic() {
	}
}
