package rivercrossing.wildebeestAndLions;

import java.util.ArrayList;
import java.util.List;
import rivercrossing.Passenger;
import rivercrossing.Raft;
import rivercrossing.Rules;
import rivercrossing.State;

public class WildebeestAndLionsRules extends Rules
{

	protected static final String WILDE_A = "Wildebeest-A";
	protected static final String WILDE_B = "Wildebeest-B";
	protected static final String WILDE_C = "Wildebeest-C";
	protected static final String LION_A = "Lion-A";
	protected static final String LION_B = "Lion-B";
	protected static final String LION_C = "Lion-C";

	@Override
	public State getInitialState() {
		List<Passenger> passengerList = new ArrayList<>(7);
		passengerList.add(new Raft());
		passengerList.add(new WildebeestOrLion(WILDE_A));
		passengerList.add(new WildebeestOrLion(WILDE_B));
		passengerList.add(new WildebeestOrLion(WILDE_C));

		WildebeestOrLion lionA = new WildebeestOrLion(LION_A);
		WildebeestOrLion lionB = new WildebeestOrLion(LION_B);
		WildebeestOrLion lionC = new WildebeestOrLion(LION_C);
		lionA.setIsLion(true);
		lionB.setIsLion(true);
		lionC.setIsLion(true);
		passengerList.add(lionA);
		passengerList.add(lionB);
		passengerList.add(lionC);

		State initialState = new State(WildebeestAndLionsRules.class);
		initialState.setPassengers(passengerList);
		return initialState;
	}

	@Override
	protected boolean isValidRaft(List<Passenger> loadedRaft) {
		boolean containsRaft = false;
		for (Passenger p : loadedRaft) {
			containsRaft = containsRaft || p.isRaft();
		}
		return 2 <= loadedRaft.size() && loadedRaft.size() <= 3 && containsRaft;
	}

	@Override
	protected boolean banksAreValid(State newState) {
		int wildebeestLeft = 0;
		int wildebeestRight = 0;
		int lionsLeft = 0;
		int lionsRight = 0;

		for (Passenger p : newState.getPassengers()) {
			if (!p.isRaft()) {
				WildebeestOrLion wol = (WildebeestOrLion) p;
				if (wol.isLion()) {
					if (wol.hasCrossed()) {
						++lionsRight;
					} else {
						++lionsLeft;
					}
				} else if (wol.isWildebeest()) {
					if (wol.hasCrossed()) {
						++wildebeestRight;
					} else {
						++wildebeestLeft;
					}
				}
			}
		}

		return (wildebeestRight == 0 || wildebeestRight >= lionsRight)
			&& (wildebeestLeft == 0 || wildebeestLeft >= lionsLeft);
	}
}
