package com.palyrobotics.frc2018.auto;

import com.palyrobotics.frc2018.behavior.Routine;

public abstract class AutoModeBase {
	protected boolean active = false;

	public abstract String toString();

	//Will be run before the routine is taken
	public abstract void prestart();

	public AutoModeBase() {}

	public enum Alliance {
		RED,
		BLUE
	}

	public enum StartingPosition {
		LEFT,
		CENTER,
		RIGHT
	}

	public enum Decision {
		NEVER,
		LEFT,
		RIGHT,
		BOTH
	}

	public enum SecondSideDecision {
	    NEVER,
	    OPPOSITE,
        SAME,
        BOTH
    }

	public enum Priority {
		SCALE,
		SWITCH
	}
	
	// To set the auto mode, set these variables in code!
	public static Alliance mAlliance = Alliance.BLUE;
	public static StartingPosition mStartingPosition = StartingPosition.RIGHT;
	public static Decision mScaleDecision = Decision.BOTH;
	public static Decision mSwitchDecision = Decision.NEVER;
    public static SecondSideDecision mSecondScaleSideDecision = SecondSideDecision.NEVER;
    public static SecondSideDecision mSecondSwitchSideDecision = SecondSideDecision.NEVER;
	public static Priority mPriority = Priority.SCALE;
	public static Priority mSecondCubePriority = Priority.SWITCH;
	public static boolean mMultiCube = false;

	public abstract Routine getRoutine();

	public void stop() {
		active = false;
	}

	public boolean isActive() {
		return active;
	}
	
	public abstract String getKey();
}