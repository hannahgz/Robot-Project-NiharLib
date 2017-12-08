package com.palyrobotics.frc2017.subsystems;

import com.ctre.CANTalon;
import com.palyrobotics.frc2017.config.Commands;
import com.palyrobotics.frc2017.config.RobotState;
import com.palyrobotics.frc2017.util.CANTalonOutput;
//import com.palyrobotics.frc2017.robot.HardwareAdapter;

public class Climber extends Subsystem {
	public Climber() {
		super("Climber");
		// TODO Auto-generated constructor stub
	}
	public CANTalon climber;
	public enum ClimberState{
		IDLE,
		MANUAL; 
	}
	private ClimberState mState = ClimberState.IDLE;
//	private double mOutput = 0;  
	private CANTalonOutput mOutput = new CANTalonOutput();
	private static Climber instance = new Climber();
	public static Climber getInstance() {
		return instance;
	}
	public void start(){
		mState = ClimberState.IDLE; 
	}
	public void stop(){
		mState = ClimberState.IDLE; 
	}
	public void update(Commands commands, RobotState robotState) {
		mState = commands.wantedClimberState; 
		switch(mState){
			case IDLE: 
				mOutput.setVoltage(0); 
				break; 
			case MANUAL:
				//System.out.println("climber stick input " + HardwareAdapter.getInstance().getJoysticks().climberStick.getY()); 
//				System.out.println("MANUAL");
//				mOutput = HardwareAdapter.getInstance().getJoysticks().climberStick.getY() * -12;
				setManualOutput(commands); 
				break; 
		}
	}
//	public double getOutput() {
//		return mOutput;
//	}
	public CANTalonOutput getOutput(){
		return mOutput; 
	}
	private void setManualOutput(Commands commands){
		mOutput.setVoltage(commands.climberStickInput.y * -6);
	}
}
