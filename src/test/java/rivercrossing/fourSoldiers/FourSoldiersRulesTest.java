package rivercrossing.fourSoldiers;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import rivercrossing.Passenger;
import rivercrossing.Raft;
import rivercrossing.State;

public class FourSoldiersRulesTest extends FourSoldiersRules
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

	private State buildState(boolean lazySide, boolean arrogantSide, boolean braveASide, boolean braveBSide)
	{
		State state = getInitialState();

		state.getPassenger(LAZY).setCrossed(lazySide);
		state.getPassenger(ARROGANT).setCrossed(arrogantSide);
		state.getPassenger(BRAVE_A).setCrossed(braveASide);
		state.getPassenger(BRAVE_B).setCrossed(braveBSide);

		return state;
	}

}
