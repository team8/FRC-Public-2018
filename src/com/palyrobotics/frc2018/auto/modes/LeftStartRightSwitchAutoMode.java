package com.palyrobotics.frc2018.auto.modes;

import com.palyrobotics.frc2018.auto.AutoModeBase;
import com.palyrobotics.frc2018.behavior.Routine;
import com.palyrobotics.frc2018.behavior.SequentialRoutine;
import com.palyrobotics.frc2018.behavior.routines.drive.DrivePathRoutine;
import com.palyrobotics.frc2018.behavior.routines.drive.DriveSensorResetRoutine;
import com.palyrobotics.frc2018.config.AutoDistances;
import com.palyrobotics.frc2018.config.Constants;
import com.palyrobotics.frc2018.util.trajectory.Path;
import com.palyrobotics.frc2018.util.trajectory.Translation2d;

import java.util.ArrayList;
import java.util.List;

public class LeftStartRightSwitchAutoMode extends AutoModeBase {

    private Alliance mAlliance;

    public LeftStartRightSwitchAutoMode(Alliance alliance) {
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
        List<Path.Waypoint> path = new ArrayList<>();
        path.add(new Path.Waypoint(new Translation2d(0.0, 0.0), 72.0));
        if(mAlliance == Alliance.BLUE) {
            path.add(new Path.Waypoint(new Translation2d(AutoDistances.kBlueScaleSwitchMidlineX
                    - Constants.kRobotLengthInches, 0.0), 72.0));
            path.add(new Path.Waypoint(new Translation2d(AutoDistances.kBlueScaleSwitchMidlineX - Constants.kRobotLengthInches,
                    -(AutoDistances.kFieldWidth - AutoDistances.kBlueLeftCornerOffset - (Constants.kRobotWidthInches / 2)
                            - AutoDistances.kBlueRightSwitchY - (Constants.kPlateWidth / 2))), 72.0));
            path.add(new Path.Waypoint(new Translation2d(AutoDistances.kBlueRightSwitchX + Constants.kPlateLength
                    + Constants.kRobotLengthInches, -(AutoDistances.kFieldWidth - AutoDistances.kBlueLeftCornerOffset
                    - (Constants.kRobotWidthInches / 2) - AutoDistances.kBlueRightSwitchY - (Constants.kPlateWidth / 2))), 0.0));
        } else {
            path.add(new Path.Waypoint(new Translation2d(AutoDistances.kRedScaleSwitchMidlineX
                    - Constants.kRobotLengthInches, 0.0), 72.0));
            path.add(new Path.Waypoint(new Translation2d(AutoDistances.kRedScaleSwitchMidlineX - Constants.kRobotLengthInches,
                    -(AutoDistances.kFieldWidth - AutoDistances.kRedLeftCornerOffset - (Constants.kRobotWidthInches / 2)
                            - AutoDistances.kRedRightSwitchY - (Constants.kPlateWidth / 2))), 72.0));
            path.add(new Path.Waypoint(new Translation2d(AutoDistances.kRedRightSwitchX + Constants.kPlateLength
                    + Constants.kRobotLengthInches, -(AutoDistances.kFieldWidth - AutoDistances.kRedLeftCornerOffset
                    - (Constants.kRobotWidthInches / 2) - AutoDistances.kRedRightSwitchY - (Constants.kPlateWidth / 2))), 0.0));
        }
        ArrayList<Routine> routines = new ArrayList<>();
        routines.add(new DriveSensorResetRoutine());
        routines.add(new DrivePathRoutine(new Path(path), false));

        return new SequentialRoutine(routines);
    }
}