package com.palyrobotics.frc2017.subsystems;

import com.palyrobotics.frc2017.config.Commands;
import com.palyrobotics.frc2017.config.RobotState;
import com.palyrobotics.frc2017.util.CANTalonOutput;
import com.palyrobotics.frc2017.util.archive.DriveSignal;

/*
import com.palyrobotics.frc2017.util.archive.CheesyDriveHelper;
import com.palyrobotics.frc2017.config.dashboard.DashboardManager;
import com.palyrobotics.frc2017.config.dashboard.DashboardValue;
import com.palyrobotics.frc2017.robot.HardwareAdapter;
import com.palyrobotics.frc2017.util.*;
import com.palyrobotics.frc2017.config.Constants;
import com.palyrobotics.frc2017.config.Gains;
import com.palyrobotics.frc2017.util.archive.CheesyDriveHelper;
*/ 
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
	
	private static Drive instance = new Drive();
	public static Drive getInstance() {
		return instance;
	}
	
	double leftSpeed;  
	double rightSpeed; 
	double robotRotation; 
	public void start() {
		mState = DriveState.NEUTRAL;
	}
	
	@Override
	public void stop() {
		mState = DriveState.NEUTRAL;
	}
	public void update(Commands commands, RobotState state) {
		mState = commands.wantedDriveState; 
		leftSpeed = -commands.rightStickInput.x; 
		rightSpeed = commands.rightStickInput.x; 
		robotRotation = commands.leftStickInput.y;  

		switch(mState){
			case DRIVING: 	
				leftSpeed += robotRotation;
				rightSpeed = -rightSpeed;
				rightSpeed -= robotRotation; 

				setDriveOutputs(mSignal); 
			case NEUTRAL: 
				setDriveOutputs(DriveSignal.getNeutralSignal());
		}
	}
	public DriveSignal getDriveSignal() {
		return mSignal;
	}
	private void setDriveOutputs(DriveSignal signal) {
		mSignal = signal;
		mSignal.leftMotor.setVoltage(-leftSpeed * 6);
		mSignal.rightMotor.setVoltage(-rightSpeed * 6);
	}
	
}
