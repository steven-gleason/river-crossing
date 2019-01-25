package rivercrossing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import rivercrossing.fourSoldiers.FourSoldiersRules;
import rivercrossing.weights.WeightsCrossing;
import rivercrossing.wolfGoatCabbage.WolfGoatCabbageCrossing;

public class RiverCrossingTest
{
	private RiverCrossing testSubject;

	@Test
	public void testClassicSolutionSize()
	{
		testSubject = new RiverCrossing(WolfGoatCabbageCrossing.class);
		testSubject.solve();
		assertEquals(7, testSubject.getPathLength());
	}

	@Test
	public void testFourSoldiersSolutionSize()
	{
		testSubject = new RiverCrossing(FourSoldiersRules.class);
		testSubject.solve();
		assertEquals(7, testSubject.getPathLength());
	}

	@Test
	public void testWeightsSolutionSize()
	{
		testSubject = new RiverCrossing(WeightsCrossing.class);
		testSubject.solve();
		assertEquals(15, testSubject.getPathLength());
	}
}
