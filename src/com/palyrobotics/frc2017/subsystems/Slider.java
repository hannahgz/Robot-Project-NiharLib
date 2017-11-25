package com.palyrobotics.frc2017.subsystems;

import com.palyrobotics.frc2017.config.Commands;
import com.palyrobotics.frc2017.config.Constants;
import com.palyrobotics.frc2017.config.Gains;
import com.palyrobotics.frc2017.config.RobotState;
import com.palyrobotics.frc2017.config.dashboard.DashboardManager;
import com.palyrobotics.frc2017.config.dashboard.DashboardValue;
import com.palyrobotics.frc2017.util.CANTalonOutput;

import java.util.HashMap;
import java.util.Optional;

/**
 * Created by Nihar on 1/28/17.
 * @author Prashanti
 * Controls the slider subsystem,
 */
public class Slider extends Subsystem{
	private static Slider instance = new Slider();
	public static Slider getInstance() {
		return instance;
	}

	//Miscellaneous constants
	private static final int kPotentiometerTolerance = 40;
	
	//all code assumes that right is 0 and left and center are both positive on both pot and encoder
	
	public enum SliderState {
		IDLE,
		MANUAL,
	}
	
	public enum SliderTarget {
		NONE,
		CUSTOM,
		DONE,
		LEFT,
		CENTER,
		RIGHT
	}
	
	private SliderState mState;
	private SliderTarget mTarget;
	
	private RobotState mRobotState;
	private CANTalonOutput mOutput = new CANTalonOutput();
	private DashboardValue sliderPotentiometer;
	
	private Slider() {
		super("Slider");
		sliderPotentiometer = new DashboardValue("slider-pot");
	}
	
	
	@Override
	public void start() {
		mState = SliderState.IDLE;
		mTarget = SliderTarget.NONE;
		mOutput.setVoltage(0);
	}
	
	@Override
	public void stop() {
		mState = SliderState.IDLE;
		mTarget = SliderTarget.NONE;
		mOutput.setVoltage(0);
	}
	
	/**
	 * Updates the slider with the newest robot state, does not set any states or change the output
	 * @param commands the commands
	 * @param robotState robot sensor data
	 */
	@Override
	public void update(Commands commands, RobotState robotState) {
		mRobotState = robotState;
		mState = commands.wantedSliderState.MANUAL; 
		sliderPotentiometer.updateValue(robotState.sliderPotentiometer);
		switch(mState) {
			case IDLE:
				mTarget = SliderTarget.NONE;
				mOutput.setVoltage(0);
				break;
			case MANUAL:
				mTarget = SliderTarget.NONE;
				double potValue = mRobotState.sliderPotentiometer; 
				if(Math.abs(potValue) < kPotentiometerTolerance || (potValue >= kPotentiometerTolerance && commands.sliderStickInput.x < 0) || (potValue <= kPotentiometerTolerance && commands.sliderStickInput.x > 0)){
					setManualOutput(commands);
				}
				else{
					mOutput.setVoltage(0);
				}
				break;
		}
	}
	public CANTalonOutput getOutput() {
		return mOutput;
	}
	
	/**
	 * Encapsulate to use in both run and update methods
	 */
	private void setManualOutput(Commands commands) {
		mOutput.setVoltage(commands.sliderStickInput.x * 12);
	}
	
	

}
