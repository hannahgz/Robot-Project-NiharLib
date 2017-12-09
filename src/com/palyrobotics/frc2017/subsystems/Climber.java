package com.palyrobotics.frc2017.subsystems;

import com.ctre.CANTalon;
import com.palyrobotics.frc2017.config.Commands;
import com.palyrobotics.frc2017.config.RobotState;
import com.palyrobotics.frc2017.util.CANTalonOutput;
//import com.palyrobotics.frc2017.robot.HardwareAdapter;

public class Climber extends Subsystem {
	public Climber() {
		super("Climber");
	}
	public CANTalon climber;
	public enum ClimberState{
		IDLE,
		MANUAL; 
	}
	private ClimberState mState;
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
				setManualOutput(commands); 
				break; 
		}
	}
	public CANTalonOutput getOutput(){
		return mOutput; 
	}
	private void setManualOutput(Commands commands){
		mOutput.setVoltage(commands.climberStickInput.y * 6);
	}
}
