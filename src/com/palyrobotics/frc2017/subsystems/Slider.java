package com.palyrobotics.frc2017.subsystems;

import com.palyrobotics.frc2017.config.Commands;
import com.palyrobotics.frc2017.config.RobotState;
import com.palyrobotics.frc2017.config.dashboard.DashboardValue;
import com.palyrobotics.frc2017.util.CANTalonOutput;
//import com.palyrobotics.frc2017.robot.HardwareAdapter;

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
	private static final int kPotentiometerToleranceMax = 2900;
	private static final int kPotentiometerToleranceMin = 2300; 
	
	public enum SliderState {
		IDLE,
		MANUAL,
	}
	
	
	
	private SliderState mState;
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
		
	}
	
	@Override
	public void stop() {
		mState = SliderState.IDLE;
	}
	
	/**
	 * Updates the slider with the newest robot state, does not set any states or change the output
	 * @param commands the commands
	 * @param robotState robot sensor data
	 */
	@Override
	public void update(Commands commands, RobotState robotState) {
		mRobotState = robotState;
		mState = commands.wantedSliderState; 
		sliderPotentiometer.updateValue(robotState.sliderPotentiometer);
		switch(mState) {
			case IDLE:
				mOutput.setVoltage(0);
				break;
			case MANUAL:
				double potValue = mRobotState.sliderPotentiometer; 
				double sliderValue = commands.sliderStickInput.x; 
				if(((potValue > kPotentiometerToleranceMin) && (potValue < kPotentiometerToleranceMax)) || (potValue >= kPotentiometerToleranceMax && sliderValue > 0) || (potValue <= kPotentiometerToleranceMin && sliderValue <  0)){
					System.out.println("setting slider output values");
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
		mOutput.setVoltage(commands.sliderStickInput.x * 6); 
		System.out.println("slider output value: " + getOutput());
//		mOutput.setVoltage(HardwareAdapter.getInstance().getJoysticks().sliderStick.getX() * 12);
	}
}
