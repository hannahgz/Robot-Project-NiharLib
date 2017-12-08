package com.palyrobotics.frc2017.robot;

import com.palyrobotics.frc2017.config.Commands;
import com.palyrobotics.frc2017.config.Commands.JoystickInput;
import com.palyrobotics.frc2017.subsystems.*;
import com.palyrobotics.frc2017.subsystems.Climber.ClimberState;
import com.palyrobotics.frc2017.subsystems.Drive.DriveState;
import com.palyrobotics.frc2017.subsystems.Slider.SliderState;
import edu.wpi.first.wpilibj.Joystick;

/**
 * Used to produce Commands {@link Commands} from human input
 * Singleton class. Should only be used in robot package.
 * @author Nihar
 *
 */
public class OperatorInterface {
	private static OperatorInterface instance = new OperatorInterface();

	public static OperatorInterface getInstance() {
		return instance;
	}

	private OperatorInterface() {}

	private HardwareAdapter.Joysticks mJoysticks = HardwareAdapter.getInstance().getJoysticks();
	private Joystick mDriveStick = mJoysticks.driveStick;
	private Joystick mTurnStick = mJoysticks.turnStick;
	private Joystick mSliderStick = mJoysticks.sliderStick;
	private Joystick mClimberStick = mJoysticks.climberStick;


	/**
	 * Returns modified commands
	 * @param prevCommands
	 */
	public Commands updateCommands(Commands prevCommands) {
		Commands newCommands = prevCommands.copy();
		newCommands.leftStickInput = new JoystickInput(mDriveStick.getX(), mDriveStick.getY(), mDriveStick.getTrigger());
		newCommands.rightStickInput = new JoystickInput(mTurnStick.getX(), mTurnStick.getY(), mTurnStick.getTrigger());
		newCommands.sliderStickInput = new JoystickInput(mSliderStick.getX(), mSliderStick.getY(), mSliderStick.getTrigger());
		newCommands.climberStickInput = new JoystickInput(mClimberStick.getX(), mClimberStick.getY(), mClimberStick.getTrigger());
//		System.out.println("climber stick input: " + newCommands.climberStickInput.y);
		
		if(newCommands.climberStickInput.y > 0){
			newCommands.wantedClimberState = ClimberState.MANUAL; 
			
		} else { 
			newCommands.wantedClimberState = ClimberState.IDLE; 
		}
		
		if(Math.abs(newCommands.sliderStickInput.x) > 0){
			newCommands.wantedSliderState = SliderState.MANUAL; 
			//System.out.println("Operator Interface: wantedSliderState = MANUAL");
		} else {
			newCommands.wantedSliderState = SliderState.IDLE; 
			//System.out.println("Operator Interface: wantedSliderState = IDLE");
		}
		
		if(Math.abs(newCommands.leftStickInput.y) > 0 || Math.abs(newCommands.rightStickInput.x) > 0){
			newCommands.wantedDriveState = DriveState.DRIVING; 
//			System.out.println("Operator Interface: wantedDriveState = DRIVING"); 
		} else {
			newCommands.wantedDriveState = DriveState.NEUTRAL; 
//			System.out.println("Operator Interface: wantedDriveState = NEUTRAL");
		}
		
		return newCommands;
	}	
}