package rivercrossing.fourSoldiers;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import rivercrossing.Passenger;
import rivercrossing.Raft;
import rivercrossing.State;

public class FourSoldiersRulesTest
{
	private FourSoldiersRules testSubject;

	@Before
	public void setUp()
	{
		testSubject = new FourSoldiersRules();
	}

	@Test
	public void testBankValid0000()
	{
		State state = buildState(false, false, false, false);
		boolean isValid = testSubject.banksAreValid(state);
		assertTrue(isValid);
	}

	private State buildState(boolean lonelySide, boolean arrogantSide, boolean braveASide, boolean braveBSide)
	{
		Passenger lonely = new Passenger("lonely");
		Passenger arrogant = new Passenger("arrogant");
		Passenger braveA = new Passenger("brave_A");
		Passenger braveB = new Passenger("brave_B");

		lonely.setCrossed(lonelySide);
		arrogant.setCrossed(arrogantSide);
		braveA.setCrossed(braveASide);
		braveB.setCrossed(braveBSide);

		List<Passenger> passengers = new ArrayList<>();
		passengers.add(new Raft());
		passengers.add(lonely);
		passengers.add(arrogant);
		passengers.add(braveA);
		passengers.add(braveB);

		State state = new State(testSubject.getClass());
		state.setPassengers(passengers);

		return state;
	}

}
