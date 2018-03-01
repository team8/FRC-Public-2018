package com.palyrobotics.frc2018.config.driveteam;

import com.palyrobotics.frc2018.config.Constants;

public class DriverProfiles {
	/**
	 * Class for configuring the control constants for the robot Has one static method which configures the constants based off the driver
	 * 
	 * @author Justin
	 */
	public static void configureConstants() {
		switch(Constants.kDriverName) {
			case ERIC:
				Constants.kDeadband = 0.02;

				Constants.kDriveSensitivity = .7;

				Constants.kQuickTurnSensitivity = 0.675;//0.8;
				Constants.kPreciseQuickTurnSensitivity = 0.35;

				Constants.kQuickTurnSensitivityThreshold = 0.90;

				Constants.kQuickStopAccumulatorDecreaseRate = 0.8;

				Constants.kQuickStopAccumulatorDecreaseThreshold = 1.1;//1.2;
				Constants.kNegativeInertiaScalar = 5.0;

				Constants.kAlpha = 0.55;//0.45;

				Constants.kCyclesUntilStop = 50;

				break;
		}
	}
}