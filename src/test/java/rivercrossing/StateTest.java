package rivercrossing;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Test;
import rivercrossing.wolfGoatCabbage.WolfGoatCabbageCrossing;

public class StateTest
{
	@Test
	public void testDiff()
	{
		State initialState = new WolfGoatCabbageCrossing().getInitialState();
		State alteredState = initialState.clone();
		List<Passenger> passengers = alteredState.getPassengers();
		passengers.get(0).cross();
		passengers.get(1).cross();
		String expectedZero = passengers.get(0).getName();
		String expectedOne = passengers.get(1).getName();

		List<Passenger> diff = initialState.diffList(alteredState);

		assertEquals(expectedZero, diff.get(0).getName());
		assertEquals(expectedOne, diff.get(1).getName());
		assertEquals(2, diff.size());
	}

	@Test
	public void testDiffMore()
	{
		State initialState = new WolfGoatCabbageCrossing().getInitialState();
		State alteredState = initialState.clone();
		List<Passenger> passengers = alteredState.getPassengers();
		passengers.get(3).cross();
		String expected = passengers.get(3).getName();

		List<Passenger> diff = initialState.diffList(alteredState);

		assertEquals(expected, diff.get(0).getName());
		assertEquals(1, diff.size());
	}

	@Test
	public void testDiffSymetric()
	{
		State initialState = new WolfGoatCabbageCrossing().getInitialState();
		State alteredState = initialState.clone();
		List<Passenger> passengers = alteredState.getPassengers();
		passengers.get(1).cross();
		passengers.get(3).cross();

		List<Passenger> diff = initialState.diffList(alteredState);
		List<Passenger> ffid = alteredState.diffList(initialState);

		assertEquals(diff.size(), ffid.size());
		assertEquals(diff.get(0).getName(), ffid.get(0).getName());
		assertEquals(diff.get(1).getName(), ffid.get(1).getName());
	}
}
