package rivercrossing.wildebeestAndLions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import rivercrossing.Passenger;
import rivercrossing.Raft;
import rivercrossing.RiverCrossing;
import rivercrossing.State;

public class WildebeestAndLionsRulesTest extends WildebeestAndLionsRules
{
	private WildebeestAndLionsRules testSubject;

	@Before
	public void setUp()
	{
		testSubject = new WildebeestAndLionsRules();
	}

	@Test
	public void testSolutionSize()
	{
		RiverCrossing testSubject = new RiverCrossing(WildebeestAndLionsRules.class);
		testSubject.solve();
		assertEquals(11, testSubject.getPathLength());
	}

	@Test
	public void testBankValid00()
	{
		State state = buildState(0, 0);
		boolean isValid = testSubject.banksAreValid(state);
		assertTrue(isValid);
	}

	@Test
	public void testBankValid01()
	{
		State state = buildState(0, 1);
		boolean isValid = testSubject.banksAreValid(state);
		assertTrue(isValid);
	}

	@Test
	public void testBankValid02()
	{
		State state = buildState(0, 2);
		boolean isValid = testSubject.banksAreValid(state);
		assertTrue(isValid);
	}

	@Test
	public void testBankValid03()
	{
		State state = buildState(0, 3);
		boolean isValid = testSubject.banksAreValid(state);
		assertTrue(isValid);
	}

	@Test
	public void testBankValid10()
	{
		State state = buildState(1, 0);
		boolean isValid = testSubject.banksAreValid(state);
		assertFalse(isValid);
	}

	@Test
	public void testBankValid11()
	{
		State state = buildState(1, 1);
		boolean isValid = testSubject.banksAreValid(state);
		assertTrue(isValid);
	}

	@Test
	public void testBankValid12()
	{
		State state = buildState(1, 2);
		boolean isValid = testSubject.banksAreValid(state);
		assertFalse(isValid);
	}

	@Test
	public void testBankValid13()
	{
		State state = buildState(1, 3);
		boolean isValid = testSubject.banksAreValid(state);
		assertFalse(isValid);
	}

	@Test
	public void testBankValid20()
	{
		State state = buildState(2, 0);
		boolean isValid = testSubject.banksAreValid(state);
		assertFalse(isValid);
	}

	@Test
	public void testBankValid21()
	{
		State state = buildState(2, 1);
		boolean isValid = testSubject.banksAreValid(state);
		assertFalse(isValid);
	}

	@Test
	public void testBankValid22()
	{
		State state = buildState(2, 2);
		boolean isValid = testSubject.banksAreValid(state);
		assertTrue(isValid);
	}

	@Test
	public void testBankValid23()
	{
		State state = buildState(2, 3);
		boolean isValid = testSubject.banksAreValid(state);
		assertFalse(isValid);
	}

	@Test
	public void testBankValid30()
	{
		State state = buildState(3, 0);
		boolean isValid = testSubject.banksAreValid(state);
		assertTrue(isValid);
	}

	@Test
	public void testBankValid31()
	{
		State state = buildState(3, 1);
		boolean isValid = testSubject.banksAreValid(state);
		assertTrue(isValid);
	}

	@Test
	public void testBankValid32()
	{
		State state = buildState(3, 2);
		boolean isValid = testSubject.banksAreValid(state);
		assertTrue(isValid);
	}

	@Test
	public void testBankValid33()
	{
		State state = buildState(3, 3);
		boolean isValid = testSubject.banksAreValid(state);
		assertTrue(isValid);
	}

	private State buildState(int wildebeestCrossed, int lionsCrossed)
	{
		State state = getInitialState();
		int lionsToCross = lionsCrossed;
		int wildebeestToCross = wildebeestCrossed;

		for (Passenger p : state.getPassengers()) {
			if (!p.isRaft()) {
				WildebeestOrLion wol = (WildebeestOrLion) p;
				if (wol.isLion() && lionsToCross > 0) {
					wol.cross();
					lionsToCross--;
				} else if (wol.isWildebeest() && wildebeestToCross > 0) {
					wol.cross();
					wildebeestToCross--;
				}
			}
		}

		return state;
	}

}
