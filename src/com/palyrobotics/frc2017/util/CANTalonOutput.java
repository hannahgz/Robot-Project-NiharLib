package com.palyrobotics.frc2017.util;

import com.ctre.CANTalon;

/**
 * Created by Nihar on 1/14/17.
 * Mocks the output of a CANTalon's configuration
 * Allows passthrough of -1 to 1 mSignal
 * Allows configuration for offboard SRX calculations
 * @author Nihar
 */
public class CANTalonOutput {

	/**
	 * Prevent null pointer exceptions
	 */
	private CANTalon.TalonControlMode controlMode;
	// PercentVBus, Speed, Current, Voltage, not Follower, MotionProfile, MotionMagic
	private double setpoint;	// Encoder ticks
	public int profile;

	// Used for motion magic
	public double accel;
	public double cruiseVel;

	/**
	 * Default constructor
	 */
	public CANTalonOutput() {
		controlMode = CANTalon.TalonControlMode.Disabled;
		setpoint = 0;
		profile = 0;
		
		accel = 0;
		cruiseVel = 0;
	}

	/**
	 * Copy constructor
	 * @param talon output to copy
	 */
	public CANTalonOutput(CANTalonOutput talon) {
		this.controlMode = talon.getControlMode();
		this.setpoint = talon.getSetpoint();
		this.profile = talon.profile;
		
		this.accel = talon.accel;
		this.cruiseVel = talon.cruiseVel;
		this.profile = talon.profile;
	}
	
	public CANTalonOutput(CANTalon.TalonControlMode controlMode, double setpoint) {
		this.controlMode = controlMode;
		this.setpoint = setpoint;
		profile = 0;
		
		accel = 0;
		cruiseVel = 0;
	}

	public CANTalon.TalonControlMode getControlMode() {
		return controlMode;
	}

	public double getSetpoint() {
		return setpoint;
	}

	/**
	 * Sets Talon to TalonControlMode.Speed, velocity target control loop
	 * @param speed, target velocity (from -1023019 to 10230?)
	 */
	public void setSpeed(double speed) {
		controlMode = CANTalon.TalonControlMode.Speed;
		setpoint = speed;
	}

	/**
	 * Sets Talon to TalonControlMode.Position
	 * @param setpoint, target distance in native units
	 */
	public void setPosition(double setpoint) {
		controlMode = CANTalon.TalonControlMode.Position;
		this.setpoint = setpoint;
	}

	/**
	 * Sets Talon to standard -1 to 1 voltage control
	 * @param power
	 */
	public void setPercentVBus(double power) {
		controlMode = CANTalon.TalonControlMode.PercentVbus;
		setpoint = power;
	}

	/**
	 * Sets Talon to TalonControlMode.Voltage
	 * @param voltage in volts
	 */
	public void setVoltage(double voltage) {
		controlMode = CANTalon.TalonControlMode.Voltage;
		setpoint = voltage;
	}

	/**
	 * Sets Talon to TalonControlMode.Current
	 * @param current in amps
	 * @param p,i,d, f, izone, rampRate parameters for control loop
	 */
	public void setCurrent(double current) {
		controlMode = CANTalon.TalonControlMode.Current;
		setpoint = current;
	}

	/**
	 * Sets Talon to TalonControlMode.Disabled
	 */
	public void setDisabled() {
		this.controlMode = CANTalon.TalonControlMode.Disabled;
	}

	public String toString() {
		String name = "";
		if (controlMode == null) {
			name += "null";
		} else {
			name += controlMode.toString();
		}
		name+= " "+getSetpoint();
		return name;
	}

	/**
	 * Used for unit tests to compare drive signal values
	 */
	@Override
	public boolean equals(Object other) {
		return ((CANTalonOutput) other).getSetpoint() == this.getSetpoint() && 
				((CANTalonOutput) other).controlMode == this.controlMode; 
	}

	/* Should not be used as talon's should be set to slave mode when initialized
	public void setSlave(int masterDeviceID) {
		controlMode = CANTalon.TalonControlMode.Follower;
		this.masterDeviceID = masterDeviceID;
	} */
}