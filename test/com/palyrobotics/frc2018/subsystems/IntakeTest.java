package com.palyrobotics.frc2018.subsystems;

import com.palyrobotics.frc2018.config.Commands;
import com.palyrobotics.frc2018.config.Constants;
import com.palyrobotics.frc2018.config.RobotState;
import com.palyrobotics.frc2018.robot.Robot;
import com.palyrobotics.frc2018.subsystems.Intake.IntakeState;
import com.palyrobotics.frc2018.subsystems.Intake.OpenCloseState;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class IntakeTest {
	Intake mIntake = new Intake();
	RobotState mRobotState;
	Commands mCommands;
	
	@Before
	public void setUp() {
		mCommands = Robot.getCommands();
		mRobotState = new RobotState();
	}
	
	@After
	public void tearDown() {
		mCommands = null;
		mRobotState = null;
	}
	
	@Test
	public void testClose() {
		mCommands.wantedOpenCloseState = OpenCloseState.CLOSED;
		mIntake.update(mCommands, mRobotState);
		assertThat("Did not close properly", mIntake.getOpenCloseOutput(), is(DoubleSolenoid.Value.kForward));	
	}
	
	@Test
	public void testIntake() {
		mCommands.wantedIntakeState = IntakeState.INTAKING;
		mIntake.update(mCommands, mRobotState);
		assertThat("Did not intake properly", mIntake.getTalonOutput().getSetpoint(), is(Constants.kIntakingMotorVelocity));
	}
	
	@Test
	public void testExpel() {
		mCommands.wantedIntakeState = IntakeState.EXPELLING;
		mIntake.update(mCommands, mRobotState);
		assertThat("Did not expel properly",mIntake.getTalonOutput().getSetpoint(), is(Constants.kExpellingMotorVelocity));
	}
}
