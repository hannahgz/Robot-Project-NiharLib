package com.palyrobotics.frc2017.subsystems;

import com.ctre.CANTalon;
import com.palyrobotics.frc2017.config.Commands;
import com.palyrobotics.frc2017.config.RobotState;
import com.palyrobotics.frc2017.util.CANTalonOutput;

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
		if(Math.abs(commands.climberStickInput.y) > 0){
			mState = ClimberState.MANUAL; 
		}
		switch(mState){
			case IDLE: 
				mOutput.setPercentVBus(0); 
				break; 
			case MANUAL:
				if(commands.climberStickInput.y < 0){
					mOutput.setVoltage(commands.climberStickInput.y * 12);
				}
				break; 
		}
	}
	public CANTalonOutput getOutput() {
		return mOutput;
	}
}
