package com.palyrobotics.frc2017.robot;

import com.palyrobotics.frc2017.config.Commands;
import com.palyrobotics.frc2017.config.Constants;
import com.palyrobotics.frc2017.config.RobotState;
import com.palyrobotics.frc2017.config.dashboard.DashboardManager;
import com.palyrobotics.frc2017.config.dashboard.DashboardValue;
import com.palyrobotics.frc2017.subsystems.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	// Instantiate singleton classes
	private static RobotState robotState = new RobotState();
	public static RobotState getRobotState() {
		return robotState;
	}

	// Single instance to be passed around
	private static Commands commands = Commands.getInstance();
	public static Commands getCommands() {return commands;}
	

	private OperatorInterface operatorInterface = OperatorInterface.getInstance();

	// Subsystem controllers
	private Drive mDrive = Drive.getInstance();
	private Slider mSlider = Slider.getInstance();
	private Climber mClimber = Climber.getInstance();

	// Hardware Updater
	private HardwareUpdater mHardwareUpdater;

	@Override
	public void robotInit() {

		if (Constants.kRobotName == Constants.RobotName.STEIK) {
			try {
				mHardwareUpdater = new HardwareUpdater(this, mDrive, mSlider, mClimber);
			} catch (Exception e) {
				System.exit(1);
			}

		} else {
			try {
				mHardwareUpdater = new HardwareUpdater(mDrive);
			} catch (Exception e) {
				System.exit(1);
			}
		}

		mHardwareUpdater.initHardware();
		System.out.println("End robotInit()");
	}

	@Override
	public void autonomousInit() {

	}

	@Override
	public void autonomousPeriodic() {
	}

	@Override
	public void teleopInit() {
		System.out.println("Start teleopInit()");
		robotState.gamePeriod = RobotState.GamePeriod.TELEOP;
		mHardwareUpdater.configureTalons(false);
		mHardwareUpdater.updateSensors(robotState);
		mHardwareUpdater.updateHardware();
		DashboardManager.getInstance().toggleCANTable(true);
		commands.wantedDriveState = Drive.DriveState.DRIVING; 
		commands = operatorInterface.updateCommands(commands);
		startSubsystems();
		System.out.println("End teleopInit()");
	}

	@Override
	public void teleopPeriodic() {
		// Update RobotState
		// Gets joystick commands
		// Updates commands based on routines
		
		commands = operatorInterface.updateCommands(commands);
		mHardwareUpdater.updateSensors(robotState);
		updateSubsystems();

		//Update the hardware
		mHardwareUpdater.updateHardware();
	}

	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
	}

	private void startSubsystems() {
		mDrive.start();
		mSlider.start();
		mClimber.start();
		System.out.println("started subsystems");
	}

	private void updateSubsystems() {
		mDrive.update(commands, robotState);
		mSlider.update(commands, robotState);
		mClimber.update(commands, robotState);
	}

	private void stopSubsystems() {
		mDrive.stop();
		mSlider.stop(); 
		mClimber.stop();
	}
}