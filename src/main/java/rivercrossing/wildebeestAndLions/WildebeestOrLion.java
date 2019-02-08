package rivercrossing.wildebeestAndLions;

import java.lang.Cloneable;
import rivercrossing.Passenger;

public class WildebeestOrLion extends Passenger
{
	private boolean isLion = false;

	WildebeestOrLion(String name) {
		super(name);
	}

	void setIsLion(boolean isLion) {
		this.isLion = isLion;
	}

	void setIsWildebeest(boolean isWildebeest) {
		setIsLion(!isWildebeest);
	}

	public boolean isLion() {
		return this.isLion;
	}

	public boolean isWildebeest() {
		return !isLion();
	}

	@Override
	public Passenger clone()
	{
		WildebeestOrLion clone = new WildebeestOrLion(name);
		clone.crossed = this.crossed;
		clone.isLion = this.isLion;
		return clone;
	}

	@Override
	public int hashCode()
	{
		return name.hashCode() + (hasCrossed() ? 1 : 0) + (isLion() ? 2 : 4);
	}

	@Override
	public boolean matches(Passenger other)
	{
		return name.equals(other.getName())
			&& hasCrossed() == other.hasCrossed()
			&& hashCode() == other.hashCode();
	}
}
