package rivercrossing;

import rivercrossing.RiverCrossing;
import rivercrossing.weights.WeightsCrossing;
import rivercrossing.wolfGoatCabbage.WolfGoatCabbageCrossing;

public class Menu
{

	private static final int WOLF_GOAT_CABBAGE = 1;
	private static final int GENIUS_WEIGHTS = 2;

	public static void main(String[] args)
	{
		printMenu();
		RiverCrossing choosenRc = getUsersChoice();
		choosenRc.nextMove();
		System.out.println("no solution");
	}

	private static void printMenu()
	{
		StringBuffer menuText = new StringBuffer();
		menuText.append("** River Crossing **");
		menuText.append("\n");
		menuText.append(WOLF_GOAT_CABBAGE);
		menuText.append(" - Classic: Wolf, Goat Cabbage");
		menuText.append("\n");
		menuText.append(GENIUS_WEIGHTS);
		menuText.append(" - Genius Weights");
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
				choosenRc = new WolfGoatCabbageCrossing();
				break;

			case GENIUS_WEIGHTS:
				choosenRc = new WeightsCrossing();
				break;

			default:
				System.err.println("Invalid Selection: Choose a number from the menu.");
		}

		return choosenRc;
	}
}
