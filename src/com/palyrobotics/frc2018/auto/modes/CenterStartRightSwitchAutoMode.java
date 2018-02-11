package com.palyrobotics.frc2018.auto.modes;

import com.palyrobotics.frc2018.auto.AutoModeBase;
import com.palyrobotics.frc2018.behavior.Routine;
import com.palyrobotics.frc2018.behavior.SequentialRoutine;
import com.palyrobotics.frc2018.behavior.routines.drive.DrivePathRoutine;
import com.palyrobotics.frc2018.behavior.routines.drive.DriveSensorResetRoutine;
import com.palyrobotics.frc2018.config.AutoDistances;
import com.palyrobotics.frc2018.config.Constants;
import com.palyrobotics.frc2018.util.trajectory.Path;
import com.palyrobotics.frc2018.util.trajectory.Path.Waypoint;
import com.palyrobotics.frc2018.util.trajectory.Translation2d;

import java.util.ArrayList;
import java.util.List;

public class CenterStartRightSwitchAutoMode extends AutoModeBase {

	private Alliance mAlliance;
	
	public CenterStartRightSwitchAutoMode(Alliance alliance) {
		this.mAlliance = alliance;
	}
	
	@Override
	public String toString() {
		return mAlliance + this.getClass().toString();
	}

	@Override
	public void prestart() {
		
	}

	@Override
	public Routine getRoutine() {
        List<Waypoint> path = new ArrayList<>();
        path.add(new Waypoint(new Translation2d(0, 0), 72.0));
        if (mAlliance == Alliance.BLUE) {
            path.add(new Waypoint(new Translation2d(2.0 * Constants.kRobotLengthInches, -(AutoDistances.kFieldWidth
					- AutoDistances.kBlueLeftToCenterY - Constants.kRobotWidthInches/2.0) + AutoDistances.kBlueRightSwitchY
					+ Constants.kPlateWidth/2.0), 72.0));
			path.add(new Waypoint(new Translation2d(AutoDistances.kBlueRightSwitchX - Constants.kRobotLengthInches,
					-(AutoDistances.kFieldWidth - AutoDistances.kBlueLeftToCenterY - Constants.kRobotWidthInches/2.0)
							+ AutoDistances.kBlueRightSwitchY + Constants.kPlateWidth/2.0), 0.0));
        } else {
            path.add(new Waypoint(new Translation2d(2.0 * Constants.kRobotLengthInches, -(AutoDistances.kFieldWidth
					- AutoDistances.kRedLeftToCenterY - Constants.kRobotWidthInches/2.0) + AutoDistances.kRedRightSwitchY
					+ Constants.kPlateWidth/2.0), 72.0));
			path.add(new Waypoint(new Translation2d(AutoDistances.kRedRightSwitchX - Constants.kRobotLengthInches,
					-(AutoDistances.kFieldWidth - AutoDistances.kRedLeftToCenterY - Constants.kRobotWidthInches/2.0)
							+ AutoDistances.kRedRightSwitchY + Constants.kPlateWidth/2.0), 0.0));
        }

        ArrayList<Routine> routines = new ArrayList<>();

        routines.add(new DriveSensorResetRoutine());
        routines.add(new DrivePathRoutine(new Path(path), false));

        return new SequentialRoutine(routines);
	}

	@Override
	public String getKey() {
		return mAlliance + " CENTER SWITCH RIGHT";
	}
}
