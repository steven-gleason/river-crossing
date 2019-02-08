package rivercrossing;

import rivercrossing.fourSoldiers.FourSoldiersRules;
import rivercrossing.weights.WeightsCrossing;
import rivercrossing.wildebeestAndLions.WildebeestAndLionsRules;
import rivercrossing.wolfGoatCabbage.WolfGoatCabbageCrossing;

public class Menu
{

	private static final int WOLF_GOAT_CABBAGE = 1;
	private static final int FOUR_SOLDIERS = 2;
	private static final int GENIUS_WEIGHTS = 3;
	private static final int WILDEBEEST_AND_LIONS = 4;

	public static void main(String[] args)
	{
		RiverCrossing choosenRc = null;

		if (args.length > 0)
		{
			choosenRc = riverCrossingFactory(args[0]);
		}

		if (choosenRc == null)
		{
			printMenu();
			choosenRc = getUsersChoice();
		}

		choosenRc.solve();
		choosenRc.endGame();
	}

	private static void printMenu()
	{
		StringBuffer menuText = new StringBuffer();
		menuText.append("** River Crossing **");
		menuText.append("\n");

		menuText.append(WOLF_GOAT_CABBAGE);
		menuText.append(" - Classic: Wolf, Goat Cabbage");
		menuText.append("\n");

		menuText.append(FOUR_SOLDIERS);
		menuText.append(" - Four Soldiers");
		menuText.append("\n");

		menuText.append(GENIUS_WEIGHTS);
		menuText.append(" - Genius Weights");
		menuText.append("\n");

		menuText.append(WILDEBEEST_AND_LIONS);
		menuText.append(" - Wildebeest and Lions");
		menuText.append("\n");

		menuText.append("Q - Quit");
		System.out.println(menuText);
	}

	private static RiverCrossing getUsersChoice()
	{
		RiverCrossing choosenRc = null;

		while (choosenRc == null) {
			printPrompt();
			String input = System.console().readLine();

			if ("q".equalsIgnoreCase(input))
			{
				quit();
			}

		 	choosenRc = riverCrossingFactory(input);
		}

		return choosenRc;
	}

	private static void printPrompt()
	{
		System.out.print("Please choose a RiverCrossing number: ");
	}

	private static void quit()
	{
		System.out.println("Quit");
		System.exit(0);
	}

	private static RiverCrossing riverCrossingFactory(String riverCrossingNumber)
	{
		RiverCrossing choosenRc = null;
		try
		{
			choosenRc = riverCrossingFactory(Integer.valueOf(riverCrossingNumber));
		}
		catch (NumberFormatException e)
		{
			System.err.println("Invalid Selection: Please choose a number!");
		}

		return choosenRc;
	}

	private static RiverCrossing riverCrossingFactory(int riverCrossingNumber)
	{
		RiverCrossing choosenRc = null;
		switch (riverCrossingNumber)
		{
			case WOLF_GOAT_CABBAGE:
				choosenRc = new RiverCrossing(WolfGoatCabbageCrossing.class);
				break;

			case FOUR_SOLDIERS:
				choosenRc = new RiverCrossing(FourSoldiersRules.class);
				break;

			case GENIUS_WEIGHTS:
				choosenRc = new RiverCrossing(WeightsCrossing.class);
				break;

			case WILDEBEEST_AND_LIONS:
				choosenRc = new RiverCrossing(WildebeestAndLionsRules.class);
				break;

			default:
				System.err.println("Invalid Selection: Choose a number from the menu.");
		}

		return choosenRc;
	}
}
