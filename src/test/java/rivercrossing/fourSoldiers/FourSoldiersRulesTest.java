package rivercrossing.fourSoldiers;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

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

	@Test
	public void testBankValid0001()
	{
		State state = buildState(false, false, false, true);
		boolean isValid = testSubject.banksAreValid(state);
		assertTrue(isValid);
	}

	@Test
	public void testBankValid0010()
	{
		State state = buildState(false, false, true, false);
		boolean isValid = testSubject.banksAreValid(state);
		assertTrue(isValid);
	}

	@Test
	public void testBankValid0011()
	{
		State state = buildState(false, false, true, true);
		boolean isValid = testSubject.banksAreValid(state);
		assertTrue(isValid);
	}

	@Test
	public void testBankValid0100()
	{
		State state = buildState(false, true, false, false);
		boolean isValid = testSubject.banksAreValid(state);
		assertTrue(isValid);
	}

	@Test
	public void testBankValid0101()
	{
		State state = buildState(false, true, false, true);
		boolean isValid = testSubject.banksAreValid(state);
		assertTrue(isValid);
	}

	@Test
	public void testBankValid0110()
	{
		State state = buildState(false, true, true, false);
		boolean isValid = testSubject.banksAreValid(state);
		assertTrue(isValid);
	}

	@Test
	public void testBankValid0111()
	{
		State state = buildState(false, true, true, true);
		boolean isValid = testSubject.banksAreValid(state);
		assertFalse(isValid);
	}

	@Test
	public void testBankValid1000()
	{
		State state = buildState(true, false, false, false);
		boolean isValid = testSubject.banksAreValid(state);
		assertFalse(isValid);
	}

	@Test
	public void testBankValid1001()
	{
		State state = buildState(true, false, false, true);
		boolean isValid = testSubject.banksAreValid(state);
		assertTrue(isValid);
	}

	@Test
	public void testBankValid1010()
	{
		State state = buildState(true, false, true, false);
		boolean isValid = testSubject.banksAreValid(state);
		assertTrue(isValid);
	}

	@Test
	public void testBankValid1011()
	{
		State state = buildState(true, false, true, true);
		boolean isValid = testSubject.banksAreValid(state);
		assertTrue(isValid);
	}

	@Test
	public void testBankValid1100()
	{
		State state = buildState(true, true, false, false);
		boolean isValid = testSubject.banksAreValid(state);
		assertTrue(isValid);
	}

	@Test
	public void testBankValid1101()
	{
		State state = buildState(true, true, false, true);
		boolean isValid = testSubject.banksAreValid(state);
		assertTrue(isValid);
	}

	@Test
	public void testBankValid1110()
	{
		State state = buildState(true, true, true, false);
		boolean isValid = testSubject.banksAreValid(state);
		assertTrue(isValid);
	}

	@Test
	public void testBankValid1111()
	{
		State state = buildState(true, true, true, true);
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
