package com.palyrobotics.frc2017.subsystems;

import com.palyrobotics.frc2017.config.Commands;
import com.palyrobotics.frc2017.config.RobotState;
import com.palyrobotics.frc2017.config.dashboard.DashboardManager;
import com.palyrobotics.frc2017.config.dashboard.DashboardValue;
import com.palyrobotics.frc2017.robot.HardwareAdapter;
import com.palyrobotics.frc2017.util.*;
import com.palyrobotics.frc2017.config.Constants;
import com.palyrobotics.frc2017.config.Gains;
import com.palyrobotics.frc2017.util.archive.CheesyDriveHelper;
import com.palyrobotics.frc2017.util.archive.DriveSignal;
import com.palyrobotics.frc2017.util.archive.CheesyDriveHelper;
/**
 * Represents the drivetrain
 * Uses controllers or cheesydrivehelper/proportionaldrivehelper to calculate DriveSignal
 * @author Nihar
 */
public class Drive extends Subsystem{
	
	public enum DriveState{
		DRIVING, 
		NEUTRAL;
	}
	private DriveState mState = DriveState.NEUTRAL;
	
	private Drive(){
		super("Drive");
	}
	private DriveSignal mSignal = DriveSignal.getNeutralSignal();
	private void setDriveOutputs(DriveSignal signal) {
		mSignal = signal;
	}
	private static Drive instance = new Drive();
	public static Drive getInstance() {
		return instance;
	}
	
	double leftSpeed;  
	double rightSpeed; 
	double robotRotation; 
	public void update(Commands commands, RobotState state) {
		leftSpeed = -commands.leftStickInput.y; 
		rightSpeed = -commands.leftStickInput.y; 
		robotRotation = commands.rightStickInput.x; 
		if(Math.abs(leftSpeed) > 0 || Math.abs(rightSpeed) > 0 || Math.abs(robotRotation) > 0){
			mState = DriveState.DRIVING; 
		}
		switch(mState){
			case DRIVING: 
				leftSpeed += robotRotation; 
				rightSpeed -= robotRotation; 
				
				rightSpeed = -rightSpeed; 
				HardwareAdapter.getInstance().getDrivetrain().leftMasterTalon.set(leftSpeed);
				HardwareAdapter.getInstance().getDrivetrain().leftSlave1Talon.set(leftSpeed);
				HardwareAdapter.getInstance().getDrivetrain().leftSlave2Talon.set(leftSpeed);
				HardwareAdapter.getInstance().getDrivetrain().rightMasterTalon.set(rightSpeed);
				HardwareAdapter.getInstance().getDrivetrain().rightSlave1Talon.set(rightSpeed);
				HardwareAdapter.getInstance().getDrivetrain().rightSlave2Talon.set(rightSpeed);
			case NEUTRAL: 
				setDriveOutputs(DriveSignal.getNeutralSignal());
		}
	}
	
}
